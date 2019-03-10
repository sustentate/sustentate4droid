package ar.com.sustentate.com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("ECOTIPS");
        textView.setTextSize(20);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        //Picasso.with(this).load(imageURL).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageDetalle);
        textDetalle.setText(text);
        titleDetalle.setText(title);
        imageDetalle.setImageResource(R.drawable.ecotips2);
    }

}
