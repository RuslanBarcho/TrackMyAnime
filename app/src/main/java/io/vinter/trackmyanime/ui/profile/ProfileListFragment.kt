package io.vinter.trackmyanime.ui.profile

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils

import java.util.ArrayList

import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.entity.animelist.AnimeListItem
import io.vinter.trackmyanime.utils.AnimeListRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_profile_list.*

/**
 * dynamic page which show one of user's lists
 */
class ProfileListFragment : Fragment() {

    internal var adapter: AnimeListRecyclerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_list, container, false)
    }

    fun setupRecycler(items: List<AnimeListItem>, animeListener: (Int, Int) -> Unit) {
        var recyclerViewState: Parcelable? = null
        val animeListRecycler = anime_list_recycler

        if (adapter != null) recyclerViewState = animeListRecycler.layoutManager!!.onSaveInstanceState()
        val list = ArrayList<AnimeListItem>()
        for (item in items)
            if ((item.status == arguments!!.getString("filter", "all")) or (arguments!!.getString("filter", "all") == "all"))
                list.add(item)
        list.reverse()
        adapter = AnimeListRecyclerAdapter(context!!, list, animeListener)
        animeListRecycler.layoutManager = LinearLayoutManager(context)
        val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        animeListRecycler.layoutAnimation = animation
        animeListRecycler.adapter = adapter
        if (recyclerViewState != null) animeListRecycler.layoutManager!!.onRestoreInstanceState(recyclerViewState)
    }

    fun updateRecycler(items: List<AnimeListItem>) {
        val list = ArrayList<AnimeListItem>()
        for (item in items)
            if ((item.status == arguments!!.getString("filter", "all")) or (arguments!!.getString("filter", "all") == "all"))
                list.add(item)
        list.reverse()
        if (adapter != null) adapter!!.update(list)
    }

}
