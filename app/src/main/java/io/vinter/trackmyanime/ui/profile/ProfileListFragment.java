package io.vinter.trackmyanime.ui.profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.entity.animelist.AnimeListItem;
import io.vinter.trackmyanime.ui.detail.DetailActivity;
import io.vinter.trackmyanime.utils.AnimeListRecyclerAdapter;
import io.vinter.trackmyanime.utils.ItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileListFragment extends Fragment {

    View mRootView;
    AnimeListRecyclerAdapter adapter;

    @BindView(R.id.anime_list_recycler)
    RecyclerView animeListRecycler;

    public ProfileListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_profile_list, container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    public void setupRecycler(List<AnimeListItem> items, ItemClickListener addButtonListener){
        List<AnimeListItem> list = new ArrayList<>();

        for (AnimeListItem item: items) if (item.getStatus().equals(getArguments().getString("filter", "all")) |
                getArguments().getString("filter", "all").equals("all")) list.add(item);

        adapter = new AnimeListRecyclerAdapter(getContext(),list, (v, position) -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("malId", position);
            getActivity().startActivityForResult(intent, 22);
        }, addButtonListener);

        animeListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
        animeListRecycler.setLayoutAnimation(animation);
        animeListRecycler.setAdapter(adapter);
    }

    public void updateRecycler(List<AnimeListItem> items){
        List<AnimeListItem> list = new ArrayList<>();
        for (AnimeListItem item: items) if (item.getStatus().equals(getArguments().getString("filter", "all")) |
            getArguments().getString("filter", "all").equals("all")) list.add(item);
        if (adapter != null) adapter.update(list);
    }

}
