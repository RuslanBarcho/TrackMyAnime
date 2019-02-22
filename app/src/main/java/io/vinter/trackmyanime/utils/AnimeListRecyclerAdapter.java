package io.vinter.trackmyanime.utils;

import android.content.Context;
import android.support.annotation.NonNull;
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
import io.vinter.trackmyanime.entity.animelist.AnimeListItem;

public class AnimeListRecyclerAdapter extends RecyclerView.Adapter<AnimeListRecyclerAdapter.AnimeListRecyclerViewHolder>{

    private Context context;
    private java.util.List<AnimeListItem> animeList;
    private ItemClickListener animeClickListener;
    private ItemClickListener addClickListener;

    public AnimeListRecyclerAdapter(Context context, List<AnimeListItem> list, ItemClickListener animeClickListener, ItemClickListener addButtonListener){
        this.context = context;
        this.animeList = list;
        this.animeClickListener = animeClickListener;
        this.addClickListener = addButtonListener;
    }

    @NonNull
    @Override
    public AnimeListRecyclerAdapter.AnimeListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_anime_list, viewGroup, false);
        return new AnimeListRecyclerAdapter.AnimeListRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeListRecyclerAdapter.AnimeListRecyclerViewHolder holder, int i) {
        GlideApp.with(context)
                .load(animeList.get(i).getImageUrl())
                .override(200, 270)
                .placeholder(R.color.colorInactive)
                .error(R.color.colorInactive)
                .transforms(new CenterCrop(), new RoundedCorners(15))
                .into(holder.art);
        holder.art.setOnClickListener(view -> {
            animeClickListener.onItemClick(view, animeList.get(i).getMalId());
        });
        holder.title.setText(animeList.get(i).getTitle());
        holder.eps.setText(String.valueOf(animeList.get(i).getWatchedEps() + "/" + animeList.get(i).getEps()));
        holder.status.setText(animeList.get(i).getStatus());
        //if (animeList.get(i).getEps() <= animeList.get(i).getWatchedEps()) holder.add.setVisibility(View.GONE);
        holder.add.setOnClickListener(view -> {
            addClickListener.onItemClick(view, animeList.get(i).getMalId());
        });
    }

    public void update(List<AnimeListItem> newItems){
        this.animeList = newItems;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    static class AnimeListRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView art;
        TextView title;
        TextView status;
        TextView eps;
        Button add;

        AnimeListRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            art = itemView.findViewById(R.id.item_anime_list_pic);
            title = itemView.findViewById(R.id.item_anime_list_title);
            status = itemView.findViewById(R.id.item_anime_list_status);
            eps = itemView.findViewById(R.id.item_anime_list_episodes);
            add = itemView.findViewById(R.id.item_anime_list_add);
        }
    }
}
