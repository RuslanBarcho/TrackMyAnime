package io.vinter.trackmyanime.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Collections;
import java.util.List;

import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.entity.top.AnimeTop;
import io.vinter.trackmyanime.utils.recycler.ChildHorizontalRecyclerView;

public class TopMainRecyclerAdapter extends RecyclerView.Adapter<TopMainRecyclerAdapter.TopMainRecyclerViewHolder> {

    private Context context = null;
    private List<Pair<List<AnimeTop>, String>> animeTopList;
    ItemClickListener animeClickListener;

    public TopMainRecyclerAdapter(Context context, List<Pair<List<AnimeTop>, String>> list,
                                  ItemClickListener animeClickListener){
        Collections.sort(list, (listStringPair, t1) -> listStringPair.second.compareTo(t1.second));
        this.context = context;
        this.animeTopList = list;
        this.animeClickListener = animeClickListener;
    }

    @NonNull
    @Override
    public TopMainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_top_main, viewGroup, false);
        return new TopMainRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TopMainRecyclerViewHolder holder, int i) {
        TopHorizontalRecyclerAdapter adapter = new TopHorizontalRecyclerAdapter(context,
                animeTopList.get(i).first.subList(0, 10), animeClickListener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.horizontalRecycler.setLayoutManager(mLayoutManager);
        holder.horizontalRecycler.setAdapter(adapter);
        holder.horizontalRecycler.setNestedScrollingEnabled(false);
        holder.topTypeButton.setText(animeTopList.get(i).second);
    }

    @Override
    public int getItemCount() {
        return animeTopList.size();
    }

    public static class TopMainRecyclerViewHolder extends RecyclerView.ViewHolder {

        ChildHorizontalRecyclerView horizontalRecycler;
        Button topTypeButton;

        public TopMainRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalRecycler = itemView.findViewById(R.id.horizontalRecycler);
            topTypeButton = itemView.findViewById(R.id.topTypeButton);
        }
    }
}
