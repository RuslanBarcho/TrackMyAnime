package io.vinter.trackmyanime.ui.profile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.utils.ProfileViewPagerAdapter;

/**
 * Fragment for display user's anime lists
 */
public class ProfileFragment extends Fragment {

    View mRootView;
    String[] lists = {"all","watching", "completed", "plan to watch"};
    ProfileViewPagerAdapter adapter;

    @BindView(R.id.profile_viewpager)
    ViewPager viewPager;

    @BindView(R.id.profile_tabs)
    SmartTabLayout tabLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, mRootView);
        ArrayList<ProfileListFragment> fragments = new ArrayList<>();
        for (String list: lists){
            fragments.add(new ProfileListFragment());
        }

        adapter = new ProfileViewPagerAdapter(getChildFragmentManager(), fragments, getContext(), lists);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);

        return mRootView;
    }

}
