package io.vinter.trackmyanime.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.database.AppDatabase
import io.vinter.trackmyanime.entity.animelist.AnimeListItem
import io.vinter.trackmyanime.ui.dialog.EditEpisodesFragment
import io.vinter.trackmyanime.utils.GlideApp
import io.vinter.trackmyanime.utils.SongRecyclerAdapter
import io.vinter.trackmyanime.utils.decorator.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    internal lateinit var db: AppDatabase
    private var inList = false
    private lateinit var preferences: SharedPreferences
    private var animeListItem: AnimeListItem? = null
    private var editEpisodes: EditEpisodesFragment? = null
    private lateinit var userEpisodes: Button
    private lateinit var add: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(22)
        this.window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        setContentView(R.layout.activity_detail)

        val art = animeDetailPicture
        val title = animeDetailTitle
        val titleJapanese = animeDetailJapanTitle
        val episodes = animeDetailEpisodes
        val score = animeDetailScore
        val description = animeDetailDescription
        userEpisodes = animeDetailUserEps
        add = animeDetailAddToList

        add.setOnClickListener {
            val anime = viewModel.animeDetail.value
            if (!inList and (anime != null)) {
                val dialogFragment = AddDialogFragment()
                dialogFragment.show(supportFragmentManager, "add_dialog")
            } else if (animeListItem != null) {
                addEpisodes(0,1)
            }
        }

        userEpisodes.setOnClickListener {
            if (animeListItem != null) {
                val bundle = Bundle()
                editEpisodes = EditEpisodesFragment()
                editEpisodes!!.setUpdateListener(this::addEpisodes)
                bundle.putSerializable("anime", animeListItem)
                editEpisodes!!.arguments = bundle
                editEpisodes!!.show(supportFragmentManager, "edit_dialog")
            }
        }

        detailBack.setOnClickListener {
            this.finish()
        }

        val malId = intent.getIntExtra("malId", 0)
        db = Room.databaseBuilder<AppDatabase>(this, AppDatabase::class.java, "anime")
                .allowMainThreadQueries()
                .build()
        viewModel.getAnimeDetail(malId)
        if (db.animeListDAO().getAnimeByMalID(malId) != null) {
            inList = true
            animeListItem = db.animeListDAO().getAnimeByMalID(malId)
            configUserEpisodes(animeListItem!!)
        }

        viewModel.animeDetail.observe(this, Observer{
            if (it != null) {
                title.text = it.title
                titleJapanese.text = it.titleJapanese
                score.text = it.score.toString()
                episodes.text ="Episodes " + it.episodes.toString()
                description.text = it.synopsis
                GlideApp.with(this)
                        .load(it.imageUrl)
                        .override(200, 270)
                        .placeholder(R.color.colorInactive)
                        .error(R.color.colorInactive)
                        .transforms(CenterCrop(), RoundedCorners(15))
                        .into(art)
                if (it.openingThemes != null){
                    if (it.openingThemes!!.isNotEmpty()) detail_songs_title.visibility = View.VISIBLE
                    val adapter = SongRecyclerAdapter(it.openingThemes!!)
                    if (songs_recycler.itemDecorationCount == 0) songs_recycler.addItemDecoration(LinearItemDecoration(this, R.dimen.item_offset))
                    songs_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    songs_recycler.adapter = adapter
                }
                if (inList) viewModel.updateImageUrl(preferences.getString("token", "")!!, animeListItem!!, it.imageUrl!!, db)
            }
        })

        viewModel.insertedAnime.observe(this, Observer{
            if (it != null) {
                setResult(22)
                inList = true
                animeListItem = it
                Toast.makeText(this, "Added to your list", Toast.LENGTH_SHORT).show()
                configUserEpisodes(it)
            }
        })

        viewModel.update.observe(this, Observer{
            if (it != null) {
                setResult(22)
                Toast.makeText(this, it.first, Toast.LENGTH_SHORT).show()
                configUserEpisodes(it.second)
                viewModel.update.postValue(null)
            }
        })

        if (savedInstanceState != null) {
            editEpisodes = supportFragmentManager.findFragmentByTag("edit_dialog") as EditEpisodesFragment?
            if (editEpisodes != null) editEpisodes!!.setUpdateListener(this::addEpisodes)
        }

    }

    fun addToAnimeList(status: String) {
        val anime = viewModel.animeDetail.value
        if (anime != null) when {
            (status == "completed") and (anime.episodes != null) -> {
                val toAdd = AnimeListItem(anime, status)
                toAdd.watchedEps = anime.episodes
                viewModel.addToMyList(preferences.getString("token", "")!!, toAdd, db)
            }
            status != "completed" -> viewModel.addToMyList(preferences.getString("token", "")!!, AnimeListItem(anime, status), db)
            else -> Toast.makeText(this, "Cannot set as completed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addEpisodes(malId: Int, episodes: Int) {
        animeListItem!!.watchedEps = animeListItem!!.watchedEps!! + episodes
        if ((animeListItem!!.eps != 0) and (animeListItem!!.eps!! <= animeListItem!!.watchedEps!!))
            animeListItem!!.status = "completed"
        else
            animeListItem!!.status = "watching"
        viewModel.updateAnime(preferences.getString("token", "")!!, animeListItem!!, db)
    }

    private fun configUserEpisodes(item: AnimeListItem) {
        if (item.status == "completed") {
            setDimensions(add, 0, 36)
        } else {
            setDimensions(add, 36, 36)
        }
        userEpisodes.visibility = View.VISIBLE
        userEpisodes.text = item.watchedEps.toString() + "/" + item.eps
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources
                .displayMetrics
                .density
        return Math.round(dp.toFloat() * density)
    }

    private fun setDimensions(view: View, width: Int, height: Int) {
        val params = view.layoutParams
        params.width = dpToPx(width)
        params.height = dpToPx(height)
        view.layoutParams = params
    }

}
