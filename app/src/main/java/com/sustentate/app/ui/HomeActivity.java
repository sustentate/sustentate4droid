package com.sustentate.app.ui;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sustentate.app.R;
import com.sustentate.app.adapter.FragmentPageAdapter;
import com.sustentate.app.utils.KeySaver;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/*
 * Created by mzorilla on 11/4/17.
 */

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Fragment> fragments = new ArrayList<>();
        setContentView(R.layout.activity_home);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_Pager);
        FragmentPageAdapter adapterViewPager = new FragmentPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(1);
        CustomTabLayout tabLayout = (CustomTabLayout) findViewById(R.id.tabs);
        List<Drawable> listaIconos = new ArrayList<>();
        listaIconos.add(getDrawable(R.drawable.logo_ecoeventos));
        listaIconos.add(getDrawable(R.drawable.logo_recicla));
        listaIconos.add(getDrawable(R.drawable.logo_tips));
        for(int i=0;i<adapterViewPager.getCount();i++) {
            tabLayout.addTab(tabLayout.newTab().setIcon(listaIconos.get(i)));
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

        Drawable logoEvento = getDrawable(R.drawable.logo_ecoeventos);
        Drawable logoTip = getDrawable(R.drawable.logo_tips);
        Drawable logoRecicla = getDrawable(R.drawable.logo_recicla);





    }
}
