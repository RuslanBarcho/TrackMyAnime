package io.vinter.trackmyanime.utils

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.vinter.trackmyanime.R

class SongRecyclerAdapter(var list: List<String>) : RecyclerView.Adapter<SongRecyclerAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SongViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemView = inflater.inflate(R.layout.item_song, viewGroup, false)
        return SongViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongViewHolder, i: Int) {
        Log.i("Song", list[i])
        val songInfo = list[i].split(" by | - ".toRegex())
        if (songInfo.size >= 2){
            holder.artist.text = songInfo[0]
            holder.name.text = songInfo[1]
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.song_name)
        var artist: TextView = itemView.findViewById(R.id.song_artist)
    }
}
