package io.vinter.trackmyanime.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.SearchView
import java.util.Random

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.entity.search.Result
import io.vinter.trackmyanime.ui.detail.DetailActivity
import io.vinter.trackmyanime.utils.AnimeNormalRecyclerAdapter

/**
 * Fragment for search anime by title
 */
class SearchFragment : Fragment() {

    private lateinit var mRootView: View
    internal lateinit var viewModel: SearchViewModel
    internal lateinit var adapter: AnimeNormalRecyclerAdapter

    @BindView(R.id.searchBar)
    internal lateinit var searchView: SearchView

    @BindView(R.id.searchRecycler)
    internal lateinit var recyclerView: RecyclerView

    @BindView(R.id.searchProgressBar)
    internal lateinit var progressBar: ProgressBar

    @OnClick(R.id.searchBar)
    internal fun openSearch() {
        searchView.onActionViewExpanded()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_search, container, false)
        ButterKnife.bind(this, mRootView)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        searchView.queryHint = resources.getStringArray(R.array.search_hints).random()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                searchView.clearFocus()
                viewModel.searchAnime(s, 1)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })

        viewModel.state.observe(this, Observer { state ->
            if (state != null) when (state) {
                0 -> {
                    progressBar.visibility = View.GONE
                }
                1 -> {
                    recyclerView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                2 -> setRecyclerView(viewModel.searchResults.value)
            }
        })
        return mRootView
    }

    private fun setRecyclerView(results: List<Result>?) {
        if (results != null) {
            recyclerView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE

            adapter = AnimeNormalRecyclerAdapter(context!!, results) { _, position ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("malId", position as Int)
                activity!!.startActivityForResult(intent, 22)
            }
            recyclerView.layoutManager = LinearLayoutManager(context)
            val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
            recyclerView.layoutAnimation = animation
            recyclerView.adapter = adapter
        }
    }

}
