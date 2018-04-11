package com.sustentate.app.ui;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sustentate.app.R;
import com.sustentate.app.adapter.FragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentViewPager extends Fragment {

    List<Fragment> fragments ;

    FragmentPageAdapter adapterViewPager;

    public FragmentViewPager() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_view_pager,container,false);

        fragments = new ArrayList<>();

        adapterViewPager = new FragmentPageAdapter(getFragmentManager(), fragments);


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_Pager);


        viewPager.setAdapter(adapterViewPager);

        if (adapterViewPager.getListaFragments().isEmpty()) {

            List<Fragment> list = new ArrayList<>();
            HomeFragment homeFragment = new HomeFragment();
            FragmentTips fragmentTips = new FragmentTips();

            list.add(homeFragment);
            list.add(fragmentTips);
            FragmentPageAdapter adapterViewPager1 = new FragmentPageAdapter(getFragmentManager(), list);
            adapterViewPager1.notifyDataSetChanged();
            viewPager.setAdapter(adapterViewPager1);

        }

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        List<Drawable> listaIconos = new ArrayList<>();
        listaIconos.add(getActivity().getDrawable(R.drawable.logo_recicla));
        listaIconos.add(getActivity().getDrawable(R.drawable.logo_tips));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.slide_three));

        tabLayout.getTabAt(0).setIcon(listaIconos.get(0));
        tabLayout.getTabAt(1).setIcon(listaIconos.get(1));

        return view;
    }

}
