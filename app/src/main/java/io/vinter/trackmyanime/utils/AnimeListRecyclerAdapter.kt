package io.vinter.trackmyanime.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.entity.animelist.AnimeListItem

class AnimeListRecyclerAdapter(private val context: Context, private var animeList: List<AnimeListItem>, private val listener: (Int, Int) -> Unit) : RecyclerView.Adapter<AnimeListRecyclerAdapter.AnimeListRecyclerViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AnimeListRecyclerAdapter.AnimeListRecyclerViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemView = inflater.inflate(R.layout.item_anime_list, viewGroup, false)
        return AnimeListRecyclerAdapter.AnimeListRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnimeListRecyclerAdapter.AnimeListRecyclerViewHolder, i: Int) {
        loadImage(i, holder.art)
        holder.art.setOnClickListener { listener(animeList[i].malId!!, 0) }
        holder.art.setOnLongClickListener {
            listener(animeList[i].malId!!, 4)
            true
        }
        holder.title.text = animeList[i].title
        holder.eps.text = animeList[i].watchedEps.toString() + "/" + animeList[i].eps
        holder.status.text = animeList[i].status
        if (animeList[i].status == "completed") {
            holder.add.visibility = View.GONE
        } else {
            holder.add.visibility = View.VISIBLE
        }
        holder.add.setOnClickListener { listener(animeList[i].malId!!, 1) }
        holder.eps.setOnClickListener { listener(animeList[i].malId!!, 2) }
    }

    fun update(newItems: List<AnimeListItem>) {
        this.animeList = newItems
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    class AnimeListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var art: ImageView = itemView.findViewById(R.id.item_anime_list_pic)
        var title: TextView = itemView.findViewById(R.id.item_anime_list_title)
        var status: TextView = itemView.findViewById(R.id.item_anime_list_status)
        var eps: TextView = itemView.findViewById(R.id.item_anime_list_episodes)
        var add: Button = itemView.findViewById(R.id.item_anime_list_add)
    }

    private fun loadImage(i: Int, art: ImageView){
        GlideApp.with(context)
                .load(animeList[i].imageUrl)
                .override(200, 270)
                .placeholder(R.color.colorInactive)
                .error(R.color.colorInactive)
                .transforms(CenterCrop(), RoundedCorners(15))
                .into(art)
    }

}
