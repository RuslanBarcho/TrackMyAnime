package io.vinter.trackmyanime.ui.profile

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.database.AppDatabase
import kotlinx.android.synthetic.main.activity_profile_stats.*

class ProfileStatsActivity : AppCompatActivity() {

    internal lateinit var db: AppDatabase
    private var counter = IntArray(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_stats)
        val animes = profile_animes
        val episodes = profile_episodes
        val watching = profile_watching
        val completed = profile_completed
        profile_stats_back.setOnClickListener {
            this.setResult(24)
            this.finish()
        }
        db = Room.databaseBuilder<AppDatabase>(this, AppDatabase::class.java, "anime")
                .allowMainThreadQueries()
                .build()
        for (anime in db.animeListDAO().animeList) {
            counter[0]++
            counter[1] += anime.watchedEps!!
            if (anime.status == "watching") counter[2]++
            else if (anime.status == "completed") counter[3]++
        }
        animes.text = counter[0].toString()
        episodes.text = counter[1].toString()
        watching.text = counter[2].toString()
        completed.text = counter[3].toString()
        db.close()
    }
}
