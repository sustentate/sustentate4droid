package ar.com.sustentate.ia.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.Date;

import ar.com.sustentate.ia.R;

public class ActivityEvento extends AppCompatActivity {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String LINK= "link";
    public static final String IMAGEURL= "url";
    public static final String PUBLISHED= "published";
    public static final String PROMOTED= "promoted";
    public static final String DATE = "date";
    public static final String ADDRESS = "place";
    public static final String PRICE= "price";
    public static final String TYPE= "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("ECOEVENTOS");
        textView.setTextSize(20);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String id = bundle.getString(ID);
        String title = bundle.getString(TITLE);
        String description = bundle.getString(DESCRIPTION);
        String urlImage = bundle.getString(IMAGEURL);
        String place = bundle.getString(ADDRESS);
        String price = bundle.getString(PRICE);
        String type = bundle.getString(TYPE);
        Boolean promoted = bundle.getBoolean(PROMOTED);
        Boolean published = bundle.getBoolean(PUBLISHED);
        Date time = (Date) bundle.getSerializable(DATE);

        TextView timev= findViewById(R.id.textView_timeDetalleEvento);
        TextView titulo = findViewById(R.id.textView_titleDetalleEvento);
        TextView descripcion = findViewById(R.id.textView_textDetalleEvento);
        TextView placev = findViewById(R.id.textView_placeDetalleEvento);
        ImageView imageDetalle = findViewById(R.id.imageView_detalleEvento);

        timev.setText(dateToString(time));
        titulo.setText(title);
        descripcion.setText(description);
        placev.setText(place);
        new DownloadImageTask(imageDetalle)
                .execute(urlImage);

    }

    private String dateToString (Date date){
        if(date == null){
            return null;
        }

        String monthString;
        switch (date.getMonth()){
                case 0:  monthString = "Enero";
                    break;
                case 1:  monthString = "Febrero";
                    break;
                case 2:  monthString = "Marzo";
                    break;
                case 3:  monthString = "Abril";
                    break;
                case 4:  monthString = "Mayo";
                    break;
                case 5:  monthString = "Junio";
                    break;
                case 6:  monthString = "Julio";
                    break;
                case 7:  monthString = "Agosto";
                    break;
                case 8:  monthString = "Septiembre";
                    break;
                case 9: monthString = "Octubre";
                    break;
                case 10: monthString = "Noviembre";
                    break;
                case 11: monthString = "Diciembre";
                    break;
                default: monthString = "Invalid month";
                    break;
            }
        String dia;
            switch (date.getDay()){
                case 0:  dia = "Domingo";
                    break;
                case 1:  dia = "Lunes";
                    break;
                case 2:  dia = "Martes";
                    break;
                case 3:  dia = "Miercoles";
                    break;
                case 4:  dia = "Jueves";
                    break;
                case 5:  dia = "Viernes";
                    break;
                case 6:  dia = "Sabado";
                    break;
                default: dia = "Invalid day";
                    break;
            }
        String fecha = dia + " " + date.getDate() + " de " + monthString;
        return fecha;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
