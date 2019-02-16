package io.vinter.trackmyanime.ui.top;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.utils.TopMainRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    TopViewModel viewModel;
    View mRootView;
    TopMainRecyclerAdapter adapter;
    final String[] types = {"upcoming", "airing", "ova", "special", "movie"};

    @BindView(R.id.recyclerUpcoming)
    RecyclerView recyclerView;

    @BindView(R.id.topProgressBar)
    ProgressBar progressBar;

    public TopFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_top, container, false);
        viewModel = ViewModelProviders.of(this).get(TopViewModel.class);
        ButterKnife.bind(this, mRootView);

        if (viewModel.tops.getValue() == null) viewModel.getAnimeTops(types);

        viewModel.tops.observe(this, tops -> {
            if (tops != null){
                progressBar.setVisibility(View.GONE);
                adapter = new TopMainRecyclerAdapter(getContext(), tops, (v, position) -> {
                    Toast.makeText(getContext(), "Anime with ID: " + position, Toast.LENGTH_SHORT).show();
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
                recyclerView.setLayoutAnimation(animation);
                recyclerView.setAdapter(adapter);
            }
        });

        return mRootView;
    }

}
