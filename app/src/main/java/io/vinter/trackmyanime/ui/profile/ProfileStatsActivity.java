package io.vinter.trackmyanime.ui.profile;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.database.AppDatabase;
import io.vinter.trackmyanime.entity.animelist.AnimeListItem;

public class ProfileStatsActivity extends AppCompatActivity {

    AppDatabase db;
    int[] counter = new int[4];

    @BindView(R.id.profile_animes)
    TextView animes;

    @BindView(R.id.profile_episodes)
    TextView episodes;

    @BindView(R.id.profile_watching)
    TextView watching;

    @BindView(R.id.profile_completed)
    TextView completed;

    @OnClick(R.id.profile_stats_back)
    void back(){
        this.setResult(24);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_stats);
        ButterKnife.bind(this);
        db = Room.databaseBuilder(this, AppDatabase.class, "anime")
                .allowMainThreadQueries()
                .build();
        for (AnimeListItem anime: db.animeListDAO().getAnimeList()){
            counter[0] ++;
            counter[1] += anime.getWatchedEps();
            if (anime.getStatus().equals("watching")) counter[2] ++;
            else if (anime.getStatus().equals("completed")) counter[3] ++;
        }
        animes.setText(String.valueOf(counter[0]));
        episodes.setText(String.valueOf(counter[1]));
        watching.setText(String.valueOf(counter[2]));
        completed.setText(String.valueOf(counter[3]));
        db.close();
    }
}
