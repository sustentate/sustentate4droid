package com.sustentate.app.ui;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.sustentate.app.R;
import com.sustentate.app.api.SustentateAPI;
import com.sustentate.app.models.ClassificationRequest;
import com.sustentate.app.models.ClassificationResponse;
import com.sustentate.app.utils.Constants;
import com.sustentate.app.utils.KeySaver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int PERMISSION_CAMERA_SD = 3030;

    private TextView cameraHint;
    private ViewGroup cameraRoot;
    private ImageView cameraRetake;
    private ImageView cameraPreview;
    private ImageView cameraTrigger;
    private ProgressBar cameraLoading;
    private LottieAnimationView cameraAnim;

    private String fileName;

    protected CameraDevice cameraDevice;
    private TextureView cameraView;
    private Size imageDimension;
    protected CaptureRequest.Builder captureRequestBuilder;
    private ImageReader imageReader;
    protected CameraCaptureSession cameraCaptureSessions;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;

    // Recycle Finish Views
    private View recycleRoot;
    private TextView recycleTitle;
    private View recycleBackground;
    private TextView recycleContinue;
    private TextView recycleSubtitle;
    private ImageView recycleImage;

    private double noRec;
    private double rec;

    private Drawable[] triggerDrawable = new Drawable[2];
    private TransitionDrawable triggerTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

        cameraRoot = findViewById(R.id.camera_root);

        cameraView = findViewById(R.id.camera_view);
        cameraTrigger = findViewById(R.id.camera_trigger);
        cameraPreview = findViewById(R.id.camera_preview);
        cameraLoading = findViewById(R.id.camera_loading);
        cameraRetake = findViewById(R.id.camera_retake);
        cameraHint = findViewById(R.id.camera_hint);

        cameraAnim = findViewById(R.id.camera_anim);
        cameraAnim.setAnimation("check.json");

        cameraHint.setText("CAPTURA UN ELEMENTO");

        requestPermissionMarshmallow();

        cameraView.setSurfaceTextureListener(textureListener);

        cameraTrigger.setOnClickListener(cameraListener);
        cameraRetake.setOnClickListener(retakeListener);
        cameraAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WatsonTask().execute();
            }
        });

        // Recycle Finish
        recycleRoot = findViewById(R.id.view_recycle);
        recycleTitle = recycleRoot.findViewById(R.id.recycle_text);
        recycleBackground = recycleRoot.findViewById(R.id.recycle_bg);
        recycleContinue = recycleRoot.findViewById(R.id.button_continue);
        recycleSubtitle = recycleRoot.findViewById(R.id.recycle_subtitle);
        recycleImage = recycleRoot.findViewById(R.id.recycle_icon);

        recycleContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleRoot.animate().alpha(0).setDuration(500).setInterpolator(new DecelerateInterpolator())
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
            }
        });

        triggerDrawable[0] = ContextCompat.getDrawable(this, R.drawable.circle_drawable);
        triggerDrawable[1] = ContextCompat.getDrawable(this, R.drawable.circle_drawable_enable);

        triggerTransition = new TransitionDrawable(triggerDrawable);
        cameraTrigger.setImageDrawable(triggerTransition);
    }

    private class WatsonTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cameraAnim.setEnabled(false);
            cameraRetake.setEnabled(false);
            cameraLoading.setVisibility(View.VISIBLE);
            cameraHint.setText("IDENTIFICANDO");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return isRecyclable();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            cameraAnim.setEnabled(true);
            cameraRetake.setEnabled(true);
            animRecycleState(result);
            cameraLoading.setVisibility(View.GONE);
            retakePicture();
        }
    }

    private void animRecycleState(Boolean result) {
        recycleRoot.setAlpha(0);
        recycleRoot.setVisibility(View.VISIBLE);
        if (result) {
            recycleTitle.setText("RECICLABLE");
            recycleSubtitle.setText("Por favor, asegurate que esté limpio y seco antes de depositarlo en el cesto de color verde.");
            recycleBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            recycleContinue.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            recycleImage.setImageResource(R.drawable.recycle);
        } else {
            recycleTitle.setText("NO RECICLABLE");
            recycleSubtitle.setText("Por favor, depositalo en el cesto de color negro.");
            recycleBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey));
            recycleContinue.setTextColor(ContextCompat.getColor(this, R.color.colorGrey));
            recycleImage.setImageResource(R.drawable.tacho);
        }
        recycleRoot.animate().alpha(1).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
    }

    public String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile= "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[20240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output.flush();
            output64.flush();
            output64.close();
            encodedFile =  output.toString();
        }
        catch (FileNotFoundException e1 ) {
            e1.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }

    private String getUserIdOrSetDefault() {
        // Revisar la mejor manera de hacer esto
        SharedPreferences prefs = getSharedPreferences(getString(R.string.userpref), Context.MODE_PRIVATE);
        String strUserId = prefs.getString(getString(R.string.userid), java.util.UUID.randomUUID().toString());

        SharedPreferences.Editor editor =getSharedPreferences(getString(R.string.userpref), Context.MODE_PRIVATE).edit();
        editor.putString(getString(R.string.userid), strUserId);
        editor.apply();

        return strUserId;
    }

    private boolean isRecyclable() throws IOException {
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        Double ratio = 1.0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            ratio = bitmap.getWidth() / 600.0;
        } else {
            ratio = bitmap.getHeight() / 600.0;
        }

        storeImage(bitmapResize(bitmap, (int) Math.round(bitmap.getWidth() / ratio) , (int) Math.round(bitmap.getHeight() / ratio)));
        String encoded =  getStringFile(new File(fileName));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sustentatemiddleware-generous-bonobo.mybluemix.net/")
                //.baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SustentateAPI api = retrofit.create(SustentateAPI.class);
        ClassificationRequest request = new ClassificationRequest();
        request.setEncodedImage(encoded);
        request.setUserId(getUserIdOrSetDefault());
        Call<ClassificationResponse> responseCall = api.classify(request);

        Response<ClassificationResponse> result = responseCall.execute();
        return result.body().getRecognitionResult() == 1;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startBackgroundThread();
        openCameraWhenPossible();
    }

    private void openCameraWhenPossible() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (KeySaver.getPermission(this, Constants.CAMERA_SD_PERMISSION)) {
                if (cameraView.isAvailable()) {
                    openCamera();
                } else {
                    cameraView.setSurfaceTextureListener(textureListener);
                }
            }
        } else {
            if (cameraView.isAvailable()) {
                openCamera();
            } else {
                cameraView.setSurfaceTextureListener(textureListener);
            }
        }
    }

    @Override
    protected void onStop() {
        closeCamera();
        stopBackgroundThread();
        super.onStop();
    }

    private View.OnClickListener cameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            fileName = rootFolder() + File.separator + "sus_" + System.currentTimeMillis() + ".jpg";
            cameraLoading.setVisibility(View.VISIBLE);
            cameraTrigger.setEnabled(false);
            takePicture();
        }
    };

    public Bitmap bitmapResize(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    private File rootFolder() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Sustentate");
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                return null;
            }
        }
        return folder;
    }

    private View.OnClickListener retakeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            retakePicture();
        }
    };

    private void showResult() {
        cameraRetake.setVisibility(View.VISIBLE);
        cameraHint.setText("TOCA NUEVAMENTE PARA IDENTIFICAR");
        cameraLoading.setVisibility(View.GONE);
        cameraPreview.setVisibility(View.VISIBLE);
        Glide.with(this).load(new File(fileName)).into(cameraPreview);
        triggerTransition.startTransition(300);
        cameraAnim.setVisibility(View.VISIBLE);
        cameraAnim.playAnimation();
    }

    private void storeImage(Bitmap image) {
        File pictureFile = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getScreenWidth() {
        WindowManager window = getWindowManager();
        Point size = new Point();
        window.getDefaultDisplay().getSize(size);
        return size.x;
    }

    private int getScreenHeight() {
        WindowManager window = getWindowManager();
        Point size = new Point();
        window.getDefaultDisplay().getSize(size);
        return size.y;
    }

    private void retakePicture() {
        cameraRetake.setVisibility(View.GONE);
        cameraPreview.setVisibility(View.GONE);
        cameraPreview.setImageBitmap(null);
        cameraAnim.setVisibility(View.GONE);
        cameraAnim.setProgress(0);
        cameraHint.setText("CAPTURA UN ELEMENTO");
        cameraTrigger.setEnabled(true);
        triggerTransition.reverseTransition(300);

    }

    @AfterPermissionGranted(PERMISSION_CAMERA_SD)
    private void requestCameraAndSDPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_sd_permission), PERMISSION_CAMERA_SD, perms);
        } else {
            KeySaver.savePermission(this, Constants.CAMERA_SD_PERMISSION, true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        if (requestCode == PERMISSION_CAMERA_SD) {
            if (list.size() > 1) {
                if (cameraView.isAvailable()) {
                    openCamera();
                } else {
                    cameraView.setSurfaceTextureListener(textureListener);
                }
            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PERMISSION_CAMERA_SD) {
            cameraRetake.setEnabled(false);
            cameraTrigger.setEnabled(false);
            Snackbar.make(cameraRoot, "Necesitamos que apruebes los permisos", Snackbar.LENGTH_SHORT).setAction("ACEPTAR", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestCameraAndSDPermission();
                }
            }).show();
        }
    }

    private void requestPermissionMarshmallow() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (!KeySaver.getPermission(this, Constants.CAMERA_SD_PERMISSION))
                requestCameraAndSDPermission();
        }
    }


    // CAMERA
    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCameraWhenPossible();
        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    protected void takePicture() {
        if(cameraDevice == null) {
            return;
        }
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSizes = null;
            if (characteristics != null) {
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
            }
            int width = 960;
            int height = 720;
            if (jpegSizes != null && 0 < jpegSizes.length) {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }
            imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
            List<Surface> outputSurfaces = new ArrayList<>(2);
            outputSurfaces.add(imageReader.getSurface());
            outputSurfaces.add(new Surface(cameraView.getSurfaceTexture()));
            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(imageReader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
            final File file = new File(fileName);
            ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = null;
                    try {
                        image = reader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.capacity()];
                        buffer.get(bytes);
                        save(bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (image != null) {
                            image.close();
                        }
                    }
                }
                private void save(byte[] bytes) throws IOException {
                    OutputStream output = null;
                    try {
                        output = new FileOutputStream(file);
                        output.write(bytes);
                    } finally {
                        if (null != output) {
                            output.close();
                        }
                    }
                }
            };
            imageReader.setOnImageAvailableListener(readerListener, mBackgroundHandler);
            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    createCameraPreview();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showResult();
                        }
                    });
                }
            };
            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        session.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onConfigureFailed(CameraCaptureSession session) {

                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    protected void createCameraPreview() {
        try {
            SurfaceTexture texture = cameraView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    //The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }
                    // When the session is ready, we start displaying the preview.
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (null != imageReader) {
            imageReader.close();
            imageReader = null;
        }
    }

    protected void updatePreview() {
        if(cameraDevice == null) {
            return;
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }
        @Override
        public void onDisconnected(CameraDevice camera) {
            cameraDevice.close();
        }
        @Override
        public void onError(CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    protected void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }
    protected void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
