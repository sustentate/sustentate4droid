package ar.com.sustentate.com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ar.com.sustentate.com.R;

public class DetalleActivity extends AppCompatActivity {


    public static final String CLAVE_TIPID = "id";
    public static final String CLAVE_TIPDATE = "date";
    public static final String CLAVE_TIPTITLE = "title";
    public static final String CLAVE_TIPTEXT = "text";
    public static final String CLAVE_TIPIMAGE = "image";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String title = bundle.getString(CLAVE_TIPTITLE);
        String text = bundle.getString(CLAVE_TIPTEXT);
        String imageURL = bundle.getString(CLAVE_TIPIMAGE);
        Long id = bundle.getLong(CLAVE_TIPID);
        Long date = bundle.getLong(CLAVE_TIPDATE);

        TextView textDetalle = findViewById(R.id.textView_textDetalle);
        TextView titleDetalle = findViewById(R.id.textView_titleDetalle);
        ImageView imageDetalle = findViewById(R.id.imageView_detalle);

        Picasso.with(this).load(imageURL).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageDetalle);
        textDetalle.setText(text);
        titleDetalle.setText(title);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
