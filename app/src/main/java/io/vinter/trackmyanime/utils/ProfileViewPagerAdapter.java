package io.vinter.trackmyanime.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import io.vinter.trackmyanime.ui.profile.ProfileListFragment;

public class ProfileViewPagerAdapter extends FragmentStatePagerAdapter{
    private ArrayList<ProfileListFragment> fragments;
    private Context context;
    private String [] titles;

    public ProfileViewPagerAdapter(FragmentManager fm, ArrayList<ProfileListFragment> fragments,
                                   Context context, String[] titles){
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position < titles.length) return titles[position];
        else return "null";
    }
}
