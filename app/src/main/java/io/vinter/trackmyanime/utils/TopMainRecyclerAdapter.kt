package io.vinter.trackmyanime.utils

import android.content.Context
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import java.util.Collections

import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.entity.top.AnimeTop
import io.vinter.trackmyanime.utils.recycler.ChildHorizontalRecyclerView

class TopMainRecyclerAdapter(private var context: Context, private var animeTopList: List<Pair<List<AnimeTop>, String>>,
                             private var animeClickListener: (Any, Any) -> Unit) : RecyclerView.Adapter<TopMainRecyclerAdapter.TopMainRecyclerViewHolder>() {

    init {
        Collections.sort(animeTopList) { listStringPair, t1 -> listStringPair.second!!.compareTo(t1.second!!) }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TopMainRecyclerViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemView = inflater.inflate(R.layout.item_top_main, viewGroup, false)
        return TopMainRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TopMainRecyclerViewHolder, i: Int) {
        val adapter = TopHorizontalRecyclerAdapter(context,
                animeTopList[i].first!!.subList(0, 10), animeClickListener)
        val mLayoutManager = object : LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        holder.horizontalRecycler.layoutManager = mLayoutManager
        holder.horizontalRecycler.adapter = adapter
        holder.horizontalRecycler.isNestedScrollingEnabled = false
        holder.topTypeButton.text = animeTopList[i].second
    }

    override fun getItemCount(): Int {
        return animeTopList.size
    }

    class TopMainRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var horizontalRecycler: ChildHorizontalRecyclerView = itemView.findViewById(R.id.horizontalRecycler)
        internal var topTypeButton: Button = itemView.findViewById(R.id.topTypeButton)

    }
}
