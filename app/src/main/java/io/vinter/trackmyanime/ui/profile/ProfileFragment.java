package io.vinter.trackmyanime.ui.profile;


import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.database.AppDatabase;
import io.vinter.trackmyanime.entity.animelist.AnimeListItem;
import io.vinter.trackmyanime.entity.detail.To;
import io.vinter.trackmyanime.ui.search.SearchViewModel;
import io.vinter.trackmyanime.utils.ItemClickListener;
import io.vinter.trackmyanime.utils.ProfileViewPagerAdapter;

/**
 * Fragment for display user's anime lists
 */
public class ProfileFragment extends Fragment {

    View mRootView;
    String[] lists = {"all", "watching", "completed", "plan to watch"};
    ProfileViewPagerAdapter adapter;
    ProfileViewModel viewModel;
    SharedPreferences preferences;
    AppDatabase db;

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
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        preferences = getActivity().getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "anime")
                .allowMainThreadQueries()
                .build();
        ButterKnife.bind(this, mRootView);
        ArrayList<ProfileListFragment> fragments = new ArrayList<>();

        for (String list: lists){
            Bundle bundle = new Bundle();
            bundle.putString("filter", list);
            ProfileListFragment fragment = new ProfileListFragment();
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }

        adapter = new ProfileViewPagerAdapter(getChildFragmentManager(), fragments, getContext(), lists);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setViewPager(viewPager);

        if (viewModel.animes.getValue() == null & savedInstanceState == null)
            viewModel.getAnimeList(preferences.getString("token", ""), db);

        viewModel.animes.observe(this, animeListItems -> {
            if (animeListItems != null){
                List<Fragment> fragmentList = getChildFragmentManager().getFragments();
                for(Fragment f: fragmentList){
                    if (f instanceof ProfileListFragment) ((ProfileListFragment) f).setupRecycler(animeListItems, (v, position) -> {
                        addEpisode(animeListItems, position);
                    });
                }
            }
        });

        viewModel.update.observe(this, message -> {
            if (message != null){
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                update(false);
            }
        });

        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

    }

    private void addEpisode(List<AnimeListItem> animeListItems, int position){
        for(AnimeListItem item: animeListItems){
            if (item.getMalId() == position){
                item.setWatchedEps(item.getWatchedEps() + 1);
                viewModel.updateAnime(preferences.getString("token", ""), item, db);
            }
        }
    }

    public void update(boolean hardReset){
        if (hardReset){
            viewModel.animes.postValue(db.animeListDAO().getAnimeList());
        } else {
            List<Fragment> fragmentList = getChildFragmentManager().getFragments();
            for(Fragment f: fragmentList){
                if (f instanceof ProfileListFragment) ((ProfileListFragment) f).updateRecycler(db.animeListDAO().getAnimeList());
            }
        }
        viewModel.update.postValue(null);
    }

}
