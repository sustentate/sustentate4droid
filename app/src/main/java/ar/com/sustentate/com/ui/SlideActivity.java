package ar.com.sustentate.com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ar.com.sustentate.com.R;
import ar.com.sustentate.com.adapter.SlideAdapter;
import ar.com.sustentate.com.utils.KeySaver;
import ar.com.sustentate.com.utils.Utils;

/*
 * Created by mzorilla on 11/5/17.
 */

public class SlideActivity extends AppCompatActivity implements View.OnClickListener {

    private Button finish;
    private Button skip;

    private ViewPager viewPager;
    private LinearLayout slideIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

        viewPager = findViewById(R.id.view_slide);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(SlideFragment.newInstance(R.drawable.slide_title, "Para saber si un residuo es reciclable o no, solo tómale una foto o pregúntale a Carla, nuestra asistente virtual.", R.color.colorLigthGrey, R.drawable.slide_one));
        fragmentList.add(SlideFragment.newInstance(R.drawable.slide_title, "Carla también podrá ayudarte con cualquier duda que tengas sobre el ambiente y su cuidado.", R.color.colorLigthGrey, R.drawable.slide_two));
        fragmentList.add(SlideFragment.newInstance(R.drawable.slide_title, "Además podrás encontrar consejos, eventos y talleres relacionados con el cuidado del medio ambiente en las secciones de Ecotips y Ecoeventos.", R.color.colorLigthGrey, R.drawable.slide_three));

        SlideAdapter adapter = new SlideAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);

        finish = findViewById(R.id.slide_finish);
        finish.setVisibility(View.GONE);

        skip = findViewById(R.id.slide_skip);

        slideIndicator = findViewById(R.id.slide_count);

        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            slideIndicator.addView(Utils.getViewIndicator(this));
            slideIndicator.getChildAt(i).setTag(i);
            slideIndicator.getChildAt(i).setOnClickListener(this);
        }

        if (slideIndicator.getChildCount() > 0) {
            slideIndicator.getChildAt(0).setBackground(getDrawable(R.drawable.circle_indicator));
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    finish.setVisibility(View.VISIBLE);
                    skip.setVisibility(View.GONE);
                } else {
                    finish.setVisibility(View.GONE);
                    skip.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < slideIndicator.getChildCount(); i++) {
                    slideIndicator.getChildAt(i).setBackground(getDrawable(R.drawable.circle_indicator_empty));
                }
                slideIndicator.getChildAt(position).setBackground(getDrawable(R.drawable.circle_indicator));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (KeySaver.getBoolSavedShare(this, "sliding")) {
            startActivity(new Intent(SlideActivity.this, HomeActivity.class));
            finish();
        }

        finish.setOnClickListener(onFinishClickListener);
        skip.setOnClickListener(onFinishClickListener);
    }

    View.OnClickListener onFinishClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            KeySaver.saveShare(SlideActivity.this, "sliding", true);
            startActivity(new Intent(SlideActivity.this, HomeActivity.class));
            finish();
        }
    };

    @Override
    public void onClick(View v) {
        if (viewPager != null) {
            viewPager.setCurrentItem((int) v.getTag());
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager != null) {
            if (viewPager.getCurrentItem() > 0) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}
