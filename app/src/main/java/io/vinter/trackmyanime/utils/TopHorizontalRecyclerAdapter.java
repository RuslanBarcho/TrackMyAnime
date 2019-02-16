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
import io.vinter.trackmyanime.entity.top.AnimeTop;

public class TopHorizontalRecyclerAdapter extends RecyclerView.Adapter<TopHorizontalRecyclerAdapter.TopHorizontalRecyclerViewHolder> {

    private Context context = null;
    private List<AnimeTop> animeTopList;
    ItemClickListener listener;

    public TopHorizontalRecyclerAdapter(Context context, List<AnimeTop> list, ItemClickListener listener){
        this.context = context;
        this.animeTopList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopHorizontalRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_anime_light, viewGroup, false);
        return new TopHorizontalRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TopHorizontalRecyclerViewHolder holder, int i) {
        holder.title.setText(animeTopList.get(i).getTitle());
        GlideApp.with(context)
                .load(animeTopList.get(i).getImageUrl())
                .override(200, 270)
                .placeholder(R.color.colorInactive)
                .error(R.color.colorInactive)
                .transforms(new CenterCrop(), new RoundedCorners(15))
                .into(holder.art);
        holder.art.setOnClickListener(view -> {
            listener.onItemClick(view, animeTopList.get(i).getMalId());
        });
    }

    @Override
    public int getItemCount() {
        return animeTopList.size();
    }

    public static class TopHorizontalRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView art;
        TextView title;

        public TopHorizontalRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            art = itemView.findViewById(R.id.itemAnimeLightPicture);
            title = itemView.findViewById(R.id.itemAnimeLightTitle);
        }
    }
}
