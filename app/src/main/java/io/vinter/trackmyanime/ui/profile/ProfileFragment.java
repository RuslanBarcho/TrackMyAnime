package io.vinter.trackmyanime.ui.profile;


import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.database.AppDatabase;
import io.vinter.trackmyanime.ui.detail.DetailActivity;
import io.vinter.trackmyanime.ui.dialog.DeleteAnimeFragment;
import io.vinter.trackmyanime.ui.dialog.EditEpisodesFragment;
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
    EditEpisodesFragment editEpisodes;
    AppDatabase db;

    @BindView(R.id.profile_viewpager)
    ViewPager viewPager;

    @BindView(R.id.profile_tabs)
    SmartTabLayout tabLayout;

    @OnClick(R.id.profile_open_stats)
    void openStats(){
        Objects.requireNonNull(getActivity()).startActivityForResult(new Intent(getActivity(), ProfileStatsActivity.class), 24);
    }

    public ProfileFragment() { }

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

        if (viewModel.animes.getValue() == null & !viewModel.loading)
            viewModel.getAnimeList(preferences.getString("token", ""), db);

        viewModel.animes.observe(this, animeListItems -> {
            if (animeListItems != null) {
                List<Fragment> fragmentList = getChildFragmentManager().getFragments();
                for(Fragment f: fragmentList){
                    if (f instanceof ProfileListFragment)
                        ((ProfileListFragment) f).setupRecycler(db.animeListDAO().getAnimeList(), (malId, clickMode) -> {
                        switch (clickMode){
                            case 0:
                                Intent intent = new Intent(getActivity(), DetailActivity.class);
                                intent.putExtra("malId", malId);
                                getActivity().startActivityForResult(intent, 22);
                                break;
                            case 1:
                                addEpisodes(malId, 1);
                                break;
                            case 2:
                                Bundle bundle = new Bundle();
                                editEpisodes = new EditEpisodesFragment();
                                editEpisodes.setUpdateListener(this::addEpisodes);
                                bundle.putSerializable("anime", db.animeListDAO().getAnimeByMalID(malId));
                                editEpisodes.setArguments(bundle);
                                editEpisodes.show(getChildFragmentManager(), "edit_dialog");
                                break;
                            case 4:
                                Bundle bundleForDelete = new Bundle();
                                bundleForDelete.putInt("malId", malId);
                                DeleteAnimeFragment deleteAnimeFragment = new DeleteAnimeFragment();
                                deleteAnimeFragment.setTargetFragment(this, 0);
                                deleteAnimeFragment.setArguments(bundleForDelete);
                                deleteAnimeFragment.show(getFragmentManager(), "delete_dialog");
                                break;
                        }
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

        if (savedInstanceState != null){
            editEpisodes = (EditEpisodesFragment) getChildFragmentManager().findFragmentByTag("edit_dialog");
            if (editEpisodes != null) editEpisodes.setUpdateListener(this::addEpisodes);
        }

        return mRootView;
    }

    private void addEpisodes(int malId, int episodes){
        viewModel.updateAnime(preferences.getString("token", ""), malId, episodes , db);
    }

    public void delete(int malId){
        viewModel.deleteAnime(preferences.getString("token", ""), malId, db);
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