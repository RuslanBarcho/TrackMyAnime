package io.vinter.trackmyanime.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.entity.AnimeTop;

public class TopMainRecyclerAdapter extends RecyclerView.Adapter<TopMainRecyclerAdapter.TopMainRecyclerViewHolder> {

    private Context context = null;
    private List<List<AnimeTop>>  animeTopList;
    private String[] names;
    ItemClickListener animeClickListener;

    public TopMainRecyclerAdapter(Context context, List<List<AnimeTop>> list, String[] names,
                                  ItemClickListener animeClickListener){
        this.context = context;
        this.animeTopList = list;
        this.names = names;
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
                animeTopList.get(i).subList(0, 10), animeClickListener);
        holder.horizontalRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.horizontalRecycler.setAdapter(adapter);
        holder.topTypeButton.setText(names[i]);
    }

    @Override
    public int getItemCount() {
        return animeTopList.size();
    }

    public static class TopMainRecyclerViewHolder extends RecyclerView.ViewHolder {

        RecyclerView horizontalRecycler;
        Button topTypeButton;

        public TopMainRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalRecycler = itemView.findViewById(R.id.horizontalRecycler);
            topTypeButton = itemView.findViewById(R.id.topTypeButton);
        }
    }
}
