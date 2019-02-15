package io.vinter.trackmyanime.ui.top;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.ui.main.MainViewModel;
import io.vinter.trackmyanime.utils.TopHorizontalRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    TopViewModel viewModel;
    View mRootView;
    TopHorizontalRecyclerAdapter adapter;

    @BindView(R.id.recyclerUpcoming)
    RecyclerView recyclerUpcoming;

    public TopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_top, container, false);
        viewModel = ViewModelProviders.of(this).get(TopViewModel.class);
        ButterKnife.bind(this, mRootView);

        if (viewModel.topUpcoming.getValue() == null) viewModel.getAnimeTop("upcoming", 1);
        viewModel.topUpcoming.observe(this, animeTops -> {
            if (animeTops != null){
                adapter = new TopHorizontalRecyclerAdapter(getContext(), animeTops.subList(0, 10));
                recyclerUpcoming.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerUpcoming.setAdapter(adapter);
            }
        });

        return mRootView;
    }

}
