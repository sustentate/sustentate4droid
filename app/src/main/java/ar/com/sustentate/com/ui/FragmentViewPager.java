package ar.com.sustentate.com.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ar.com.sustentate.com.R;
import ar.com.sustentate.com.adapter.FragmentPageAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentViewPager extends Fragment {

    List<Fragment> fragments ;

    FragmentPageAdapter adapterViewPager;

    BottomNavigationView bottomNavigationView;

    MenuItem prevMenuItem;

    public FragmentViewPager() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_view_pager,container,false);

        fragments = new ArrayList<>();

        adapterViewPager = new FragmentPageAdapter(getFragmentManager(), fragments);


        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_Pager);


        viewPager.setAdapter(adapterViewPager);

        if (adapterViewPager.getListaFragments().isEmpty()) {

            List<Fragment> list = new ArrayList<>();
            FragmentEvento fragmentEvento = new FragmentEvento();
            HomeFragment homeFragment = new HomeFragment();
            FragmentTips fragmentTips = new FragmentTips();

            list.add(fragmentEvento);
            list.add(homeFragment);
            list.add(fragmentTips);
            FragmentPageAdapter adapterViewPager1 = new FragmentPageAdapter(getFragmentManager(), list);
            adapterViewPager1.notifyDataSetChanged();
            viewPager.setAdapter(adapterViewPager1);

        }


        bottomNavigationView = (BottomNavigationView)view.findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_call:

                                viewPager.setCurrentItem(0);

                                break;

                            case R.id.action_chat:

                                viewPager.setCurrentItem(1);

                                break;

                            case R.id.action_contact:

                                viewPager.setCurrentItem(2);

                                break;

                        }

                        return false;

                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(1);

        return view;
    }

}
