package com.sustentate.app.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sustentate.app.R;
import com.sustentate.app.utils.KeySaver;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final java.lang.String TITULO = "hola" ;

    public static HomeFragment createFragment(int numberfragment, String title, int color) {
        HomeFragment fragmentFirst = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(TITULO, title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        Bundle unBundle = getArguments();

        fragmentView.findViewById(R.id.button_cam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragmentView.getContext(), MainActivity.class));
            }
        });

        fragmentView.findViewById(R.id.button_how).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        KeySaver.removeKey((Activity) fragmentView.getContext(), "sliding");
                startActivity(new Intent(fragmentView.getContext(), SlideActivity.class));
            }
        });

        ImageView homeBg = fragmentView.findViewById(R.id.home_bg);
        Glide.with(this).load(R.drawable.bg_main).apply(RequestOptions.bitmapTransform(new BlurTransformation(130))).into(homeBg);
        return fragmentView;
        }
    }


