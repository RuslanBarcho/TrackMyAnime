package io.vinter.trackmyanime.utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.entity.top.AnimeTop

class TopHorizontalRecyclerAdapter(private val context: Context, private val animeTopList: List<AnimeTop>, internal var listener: (Any, Any) -> Unit) : RecyclerView.Adapter<TopHorizontalRecyclerAdapter.TopHorizontalRecyclerViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TopHorizontalRecyclerViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemView = inflater.inflate(R.layout.item_anime_light, viewGroup, false)
        return TopHorizontalRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TopHorizontalRecyclerViewHolder, i: Int) {
        holder.title.text = animeTopList[i].title
        GlideApp.with(context)
                .load(animeTopList[i].imageUrl)
                .override(200, 270)
                .placeholder(R.color.colorInactive)
                .error(R.color.colorInactive)
                .transforms(CenterCrop(), RoundedCorners(15))
                .into(holder.art)
        holder.art.setOnClickListener { view -> listener(view, animeTopList[i].malId!!) }
    }

    override fun getItemCount(): Int {
        return animeTopList.size
    }

    class TopHorizontalRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var art: ImageView = itemView.findViewById(R.id.itemAnimeLightPicture)
        internal var title: TextView = itemView.findViewById(R.id.itemAnimeLightTitle)
    }
}
