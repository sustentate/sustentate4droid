package com.sustentate.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sustentate.app.ui.FragmentTips;
import com.sustentate.app.ui.HomeFragment;

import java.util.List;

/**
 * Created by emzas on 25/3/2018.
 */
public class FragmentPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> listaFragments;

    public FragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);


        listaFragments = fragments;

        HomeFragment homeFragment = new HomeFragment();
        FragmentTips fragmentTips = new FragmentTips();

        listaFragments.add(homeFragment);
        listaFragments.add(fragmentTips);

        notifyDataSetChanged();

    }



    public List<Fragment> getListaFragments() {
        return listaFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.listaFragments.get(position);
    }
    @Override
    public int getCount() {
        return this.listaFragments.size();
    }

}
