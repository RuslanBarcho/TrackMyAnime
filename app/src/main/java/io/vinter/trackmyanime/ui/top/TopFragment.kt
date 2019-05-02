package io.vinter.trackmyanime.ui.top

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar

import butterknife.BindView
import butterknife.ButterKnife
import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.ui.detail.DetailActivity
import io.vinter.trackmyanime.utils.recycler.MainVerticalRecyclerView
import io.vinter.trackmyanime.utils.TopMainRecyclerAdapter

/**
 * Fragment for display anime tops
 */
class TopFragment : Fragment() {

    private lateinit var viewModel: TopViewModel
    private lateinit var mRootView: View
    internal lateinit var adapter: TopMainRecyclerAdapter
    private val types = arrayOf("upcoming", "airing", "ova", "special", "movie")

    @BindView(R.id.recyclerUpcoming)
    internal lateinit var recyclerView: MainVerticalRecyclerView

    @BindView(R.id.topProgressBar)
    internal lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_top, container, false)
        viewModel = ViewModelProviders.of(this).get(TopViewModel::class.java)
        ButterKnife.bind(this, mRootView)

        if ((viewModel.tops.value == null) and !viewModel.loading) viewModel.getAnimeTops(types)

        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent): Boolean {
                if (motionEvent.action == MotionEvent.ACTION_DOWN && recyclerView.scrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                    recyclerView.stopScroll()
                }
                return false
            }

            override fun onTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent) {}

            override fun onRequestDisallowInterceptTouchEvent(b: Boolean) {}
        })

        viewModel.tops.observe(this, Observer{
            if (it != null) {
                progressBar.visibility = View.GONE
                adapter = TopMainRecyclerAdapter(context!!, it)  { _, position ->
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("malId", position as Int)
                    activity!!.startActivityForResult(intent, 22)
                }
                recyclerView.layoutManager = LinearLayoutManager(context)
                val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
                recyclerView.layoutAnimation = animation
                recyclerView.adapter = adapter
            }
        })

        viewModel.error.observe(this, Observer {
            if (it != null) {
                progressBar.visibility = View.GONE
                val snackBar = Snackbar.make(mRootView.findViewById<View>(R.id.topSnackbar), it, Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(resources.getColor(R.color.colorAccent))
                snackBar.setAction("Retry") {
                    snackBar.dismiss()
                    progressBar.visibility = View.VISIBLE
                    viewModel.getAnimeTops(types)
                }
                snackBar.show()
                viewModel.error.postValue(null)
            }
        })
        return mRootView
    }

}
