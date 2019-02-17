package io.vinter.trackmyanime.ui.top;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import io.vinter.trackmyanime.ui.detail.DetailActivity;
import io.vinter.trackmyanime.utils.recycler.MainVerticalRecyclerView;
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
    MainVerticalRecyclerView recyclerView;

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

        if (viewModel.tops.getValue() == null & savedInstanceState == null) viewModel.getAnimeTops(types);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {
                    recyclerView.stopScroll();
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) { }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) { }
        });

        viewModel.tops.observe(this, tops -> {
            if (tops != null){
                progressBar.setVisibility(View.GONE);
                adapter = new TopMainRecyclerAdapter(getContext(), tops, (v, position) -> {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("malId", position);
                    getActivity().startActivityForResult(intent, 22);
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
