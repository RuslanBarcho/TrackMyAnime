package io.vinter.trackmyanime.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.entity.search.Result;

public class AnimeNormalRecyclerAdapter extends RecyclerView.Adapter<AnimeNormalRecyclerAdapter.AnimeNormalRecyclerViewHolder> {

    private Context context = null;
    private List<Result> animeList;
    private ItemClickListener animeClickListener;

    public AnimeNormalRecyclerAdapter(Context context, List<Result> list, ItemClickListener animeClickListener){
        this.context = context;
        this.animeList = list;
        this.animeClickListener = animeClickListener;
    }

    @NonNull
    @Override
    public AnimeNormalRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_anime_normal, viewGroup, false);
        return new AnimeNormalRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeNormalRecyclerViewHolder holder, int i) {
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

        if (animeList.get(i).getEpisodes() != 0) holder.eps.setText(String.valueOf(animeList.get(i).getEpisodes()));
        else  if (animeList.get(i).getAiring()) holder.eps.setText(String.valueOf("Ongoing"));
        else holder.eps.setVisibility(View.GONE);
        if (animeList.get(i).getScore() != null) holder.score.setText(String.valueOf(animeList.get(i).getScore()));
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public static class AnimeNormalRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView art;
        TextView title;
        TextView eps;
        TextView score;

        public AnimeNormalRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            art = itemView.findViewById(R.id.itemAnimeNormalPicture);
            title = itemView.findViewById(R.id.itemAnimeNormalTitle);
            eps = itemView.findViewById(R.id.itemAnimeNormalEps);
            score = itemView.findViewById(R.id.itemAnimeNormalScore);
        }
    }
}
