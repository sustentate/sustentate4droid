package com.sustentate.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sustentate.app.ui.FragmentEventos;
import com.sustentate.app.ui.FragmentTips;
import com.sustentate.app.ui.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emzas on 25/3/2018.
 */
public class FragmentPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> listaFragments;
    public FragmentPageAdapter(FragmentManager fm, List<Fragment> listaFragments) {
        super(fm);
        this.listaFragments = listaFragments;

        HomeFragment homeFragment = new HomeFragment();
        FragmentEventos fragmentEventos = new FragmentEventos();
        FragmentTips fragmentTips = new FragmentTips();

        listaFragments.add(fragmentEventos);
        listaFragments.add(homeFragment);
        listaFragments.add(fragmentTips);

        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        return this.listaFragments.get(position);
    }
    @Override
    public int getCount() {
        return this.listaFragments.size();
    }
   /* @Override
    public CharSequence getPageTitle(int position) {
        return this.listaFragments.get(position).getTitulo();
    }*/
}
