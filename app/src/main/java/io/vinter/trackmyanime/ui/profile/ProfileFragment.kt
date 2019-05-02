package io.vinter.trackmyanime.ui.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.ogaclejapan.smarttablayout.SmartTabLayout

import java.util.ArrayList
import java.util.Objects

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.database.AppDatabase
import io.vinter.trackmyanime.ui.detail.DetailActivity
import io.vinter.trackmyanime.ui.dialog.DeleteAnimeFragment
import io.vinter.trackmyanime.ui.dialog.EditEpisodesFragment
import io.vinter.trackmyanime.utils.ProfileViewPagerAdapter

/**
 * Fragment for display user's anime lists
 */
class ProfileFragment : Fragment() {

    private lateinit var mRootView: View
    private var lists = arrayOf("all", "watching", "completed", "plan to watch")
    internal lateinit var adapter: ProfileViewPagerAdapter
    private lateinit var viewModel: ProfileViewModel
    private lateinit var preferences: SharedPreferences
    private var editEpisodes: EditEpisodesFragment? = null
    internal lateinit var db: AppDatabase

    @BindView(R.id.profile_viewpager)
    internal lateinit var viewPager: ViewPager

    @BindView(R.id.profile_tabs)
    internal lateinit var tabLayout: SmartTabLayout

    @OnClick(R.id.profile_open_stats)
    internal fun openStats() {
        Objects.requireNonNull<FragmentActivity>(activity).startActivityForResult(Intent(activity, ProfileStatsActivity::class.java), 24)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        preferences = activity!!.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        db = Room.databaseBuilder<AppDatabase>(context!!, AppDatabase::class.java, "anime")
                .allowMainThreadQueries()
                .build()
        ButterKnife.bind(this, mRootView)
        val fragments = ArrayList<ProfileListFragment>()

        for (list in lists) {
            val bundle = Bundle()
            bundle.putString("filter", list)
            val fragment = ProfileListFragment()
            fragment.arguments = bundle
            fragments.add(fragment)
        }

        adapter = ProfileViewPagerAdapter(childFragmentManager, fragments, lists)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 4
        tabLayout.setViewPager(viewPager)

        if ((viewModel.animes.value == null) and !viewModel.loading) viewModel.getAnimeList(preferences.getString("token", "")!!, db)

        viewModel.animes.observe(this, Observer{ animeListItems ->
            if (animeListItems != null) {
                val fragmentList = childFragmentManager.fragments
                for (f in fragmentList) {
                    if (f is ProfileListFragment)
                        f.setupRecycler(db.animeListDAO().animeList) { malId, clickMode ->
                            when (clickMode) {
                                0 -> {
                                    val intent = Intent(activity, DetailActivity::class.java)
                                    intent.putExtra("malId", malId as Int)
                                    activity!!.startActivityForResult(intent, 22)
                                }
                                1 -> addEpisodes(malId as Int, 1)
                                2 -> {
                                    val bundle = Bundle()
                                    editEpisodes = EditEpisodesFragment()
                                    editEpisodes!!.setUpdateListener(this::addEpisodes)
                                    bundle.putSerializable("anime", db.animeListDAO().getAnimeByMalID(malId as Int))
                                    editEpisodes!!.arguments = bundle
                                    editEpisodes!!.show(childFragmentManager, "edit_dialog")
                                }
                                4 -> {
                                    val bundleForDelete = Bundle()
                                    bundleForDelete.putInt("malId", malId as Int)
                                    val deleteAnimeFragment = DeleteAnimeFragment()
                                    deleteAnimeFragment.setTargetFragment(this, 0)
                                    deleteAnimeFragment.arguments = bundleForDelete
                                    deleteAnimeFragment.show(fragmentManager!!, "delete_dialog")
                                }
                            }
                        }
                }
            }
        })

        viewModel.update.observe(this, Observer{ message ->
            if (message != null) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                update(false)
            }
        })

        viewModel.error.observe(this, Observer { error ->
            if (error != null) {
                viewModel.error.postValue(null)
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        })

        if (savedInstanceState != null) {
            editEpisodes = childFragmentManager.findFragmentByTag("edit_dialog") as EditEpisodesFragment?
            if (editEpisodes != null) editEpisodes!!.setUpdateListener(this::addEpisodes)
        }

        return mRootView
    }

    private fun addEpisodes(malId: Int, episodes: Int) {
        viewModel.updateAnime(preferences.getString("token", "")!!, malId, episodes, db)
    }

    fun delete(malId: Int) {
        viewModel.deleteAnime(preferences.getString("token", "")!!, malId, db)
    }

    fun update(hardReset: Boolean) {
        if (hardReset) {
            viewModel.animes.postValue(db.animeListDAO().animeList)
        } else {
            val fragmentList = childFragmentManager.fragments
            for (f in fragmentList) {
                if (f is ProfileListFragment) f.updateRecycler(db.animeListDAO().animeList)
            }
        }
        viewModel.update.postValue(null)
    }

}