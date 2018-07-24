package ar.com.sustentate.com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import ar.com.sustentate.com.R;

public class ActivityEvento extends AppCompatActivity {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String TEXT = "text";
    public static final String URL_EVENTO= "url";
    public static final String DATE = "date";
    public static final String DATE_END = "date_end";
    public static final String PLACE = "place";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Long dateInit = bundle.getLong(DATE);
        Long dateEnd = bundle.getLong(DATE_END);
        String title = bundle.getString(TITLE);
        String text = bundle.getString(TEXT);
        String urlImage = bundle.getString(URL_EVENTO);
        String place = bundle.getString(PLACE);

        TextView titulo = findViewById(R.id.textView_titleDetalleEvento);
        TextView texto = findViewById(R.id.textView_textDetalleEvento);
        ImageView imageDetalle = findViewById(R.id.imageView_detalleEvento);

        titulo.setText(title);
        texto.setText(text);

    }

}
