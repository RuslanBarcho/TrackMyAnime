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
import io.vinter.trackmyanime.entity.search.Result

class AnimeNormalRecyclerAdapter(var context: Context, private val animeList: List<Result>,
                                 private val animeClickListener: (Any, Any) -> Unit) : RecyclerView.Adapter<AnimeNormalRecyclerAdapter.AnimeNormalRecyclerViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AnimeNormalRecyclerViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemView = inflater.inflate(R.layout.item_anime_normal, viewGroup, false)
        return AnimeNormalRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnimeNormalRecyclerViewHolder, i: Int) {
        GlideApp.with(context)
                .load(animeList[i].imageUrl)
                .override(200, 270)
                .placeholder(R.color.colorInactive)
                .error(R.color.colorInactive)
                .transforms(CenterCrop(), RoundedCorners(15))
                .into(holder.art)
        holder.art.setOnClickListener { view -> animeClickListener(view, animeList[i].malId!!) }
        holder.title.text = animeList[i].title

        if (animeList[i].episodes != 0)
            holder.eps.text = animeList[i].episodes.toString()
        else if (animeList[i].airing!!)
            holder.eps.text = "Ongoing"
        else
            holder.eps.visibility = View.GONE
        if (animeList[i].score != null) holder.score.text = animeList[i].score.toString()
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    class AnimeNormalRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var art: ImageView
        internal var title: TextView
        internal var eps: TextView
        internal var score: TextView

        init {
            art = itemView.findViewById(R.id.itemAnimeNormalPicture)
            title = itemView.findViewById(R.id.itemAnimeNormalTitle)
            eps = itemView.findViewById(R.id.itemAnimeNormalEps)
            score = itemView.findViewById(R.id.itemAnimeNormalScore)
        }
    }
}
