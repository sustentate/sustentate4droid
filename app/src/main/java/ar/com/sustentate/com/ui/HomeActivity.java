package ar.com.sustentate.com.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import ar.com.sustentate.com.R;
import ar.com.sustentate.com.models.Evento;
import ar.com.sustentate.com.models.Tip;

/*
 * Created by mzorilla on 11/4/17.
 */

public class HomeActivity extends AppCompatActivity implements FragmentTips.Comunicador, FragmentEvento.Comunicador {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));
        //Instancio un fragment donde está el listview
        FragmentViewPager fragmentRecycleView = new FragmentViewPager();
        //Pido el fragment manager
        FragmentManager manager = getSupportFragmentManager();
        //Creo una transaccion
        FragmentTransaction transaction = manager.beginTransaction();
        //Le digo a la transaccion que fragment poner y donde
        transaction.replace(R.id.home_activity, fragmentRecycleView);
        //Ejecuto la transaccion
        transaction.commit();

    }

    @Override
    public void enviarInfo(Tip tip) {
        Intent intent  = new Intent(this, DetalleActivity.class);
        Bundle bundle = new Bundle();
        //Pongo en el bundle la informacion
        bundle.putString(DetalleActivity.CLAVE_TIPIMAGE, tip.getImageUrl());
        bundle.putString(DetalleActivity.CLAVE_TIPTEXT, tip.getText());
        bundle.putLong(DetalleActivity.CLAVE_TIPDATE, tip.getDate());
        bundle.putLong(DetalleActivity.CLAVE_TIPID, tip.getId());
        bundle.putString(DetalleActivity.CLAVE_TIPTITLE, tip.getTitle());

        intent.putExtras(bundle);

        startActivity(intent);

    }

    @Override
    public void enviarInfo(Evento evento) {
        Intent intent = new Intent(this, ActivityEvento.class);
        Bundle bundle = new Bundle();

        bundle.putLong(ActivityEvento.DATE, evento.getDate());
        bundle.putLong(ActivityEvento.DATE_END, evento.getDateEnd());
        bundle.putString(ActivityEvento.PLACE, evento.getPlace());
        bundle.putString(ActivityEvento.TEXT, evento.getText());
        bundle.putString(ActivityEvento.TITLE, evento.getTitle());
        bundle.putString(ActivityEvento.URL_EVENTO, evento.getImageUrl());
        bundle.putLong(ActivityEvento.ID, evento.getId());

        intent.putExtras(bundle);
        startActivity(intent);
    }
}