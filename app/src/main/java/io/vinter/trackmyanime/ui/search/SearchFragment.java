package io.vinter.trackmyanime.ui.search;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.entity.search.Result;
import io.vinter.trackmyanime.ui.detail.DetailActivity;
import io.vinter.trackmyanime.utils.AnimeNormalRecyclerAdapter;

/**
 * Fragment for search anime by title
 */
public class SearchFragment extends Fragment {

    View mRootView;
    SearchViewModel viewModel;
    AnimeNormalRecyclerAdapter adapter;

    @BindView(R.id.searchBar)
    SearchView searchView;

    @BindView(R.id.searchRecycler)
    RecyclerView recyclerView;

    @BindView(R.id.searchProgressBar)
    ProgressBar progressBar;

    @OnClick(R.id.searchBar)
    void openSearch(){
        searchView.onActionViewExpanded();
    }

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, mRootView);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        searchView.setQueryHint(getResources().getStringArray(R.array.search_hints)[new Random().nextInt(7)]);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                viewModel.searchAnime(s, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        viewModel.state.observe(this, state -> {
            if (state != null){
                switch (state){
                    case 0:
                        break;
                    case 1:
                        recyclerView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setRecyclerView(viewModel.searchResults.getValue());
                        break;
                }
            }
        });

        return mRootView;
    }

    private void setRecyclerView(List<Result> results){
        if (results != null){
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            adapter = new AnimeNormalRecyclerAdapter(getContext(), results, (v, position) -> {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("malId", position);
                getActivity().startActivityForResult(intent, 22);
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
            recyclerView.setLayoutAnimation(animation);
            recyclerView.setAdapter(adapter);
        }
    }

}
