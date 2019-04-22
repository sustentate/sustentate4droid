package ar.com.sustentate.com.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.sustentate.com.R;
import ar.com.sustentate.com.utils.KeySaver;

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
                startActivity(new Intent(fragmentView.getContext(), ChatBotActivity.class));
            }
        });

        FloatingActionButton how = (FloatingActionButton) fragmentView.findViewById(R.id.how);
        how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeySaver.removeKey((Activity) fragmentView.getContext(), "sliding");
                startActivity(new Intent(fragmentView.getContext(), SlideActivity.class));
            }
        });


        return fragmentView;
        }
    }


