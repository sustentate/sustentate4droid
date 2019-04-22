package ar.com.sustentate.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ar.com.sustentate.com.ui.FragmentEvento;
import ar.com.sustentate.com.ui.FragmentTips;
import ar.com.sustentate.com.ui.HomeFragment;

/**
 * Created by emzas on 25/3/2018.
 */
public class FragmentPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> listaFragments;

    public FragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);

        listaFragments = fragments;

        FragmentEvento fragmentEvento = new FragmentEvento();
        HomeFragment homeFragment = new HomeFragment();
        FragmentTips fragmentTips = new FragmentTips();

        listaFragments.add(fragmentEvento);
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
