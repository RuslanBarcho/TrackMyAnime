package io.vinter.trackmyanime.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.database.AppDatabase;
import io.vinter.trackmyanime.entity.animelist.AnimeListItem;
import io.vinter.trackmyanime.entity.detail.Anime;
import io.vinter.trackmyanime.utils.GlideApp;

public class DetailActivity extends AppCompatActivity {

    DetailViewModel viewModel;
    AppDatabase db;
    boolean inList = false;
    SharedPreferences preferences;

    @BindView(R.id.animeDetailPicture)
    LoaderImageView art;

    @BindView(R.id.animeDetailTitle)
    LoaderTextView title;

    @BindView(R.id.animeDetailJapanTitle)
    LoaderTextView  titleJapanese;

    @BindView(R.id.animeDetailEpisodes)
    LoaderTextView  episodes;

    @BindView(R.id.animeDetailScore)
    LoaderTextView  score;

    @BindView(R.id.animeDetailDescription)
    LoaderTextView  description;

    @OnClick(R.id.animeDetailAddToList)
    void addToList(){
        Anime anime = viewModel.animeDetail.getValue();
        if (!inList & anime != null){
            viewModel.addToMyList(preferences.getString("token", ""), new AnimeListItem(anime, "watching"), db);
        }
    }

    @OnClick(R.id.detailBack)
    void goBack(){
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        int malId = getIntent().getIntExtra("malId", 0);
        db = Room.databaseBuilder(this, AppDatabase.class, "anime")
                .allowMainThreadQueries()
                .build();

        viewModel.getAnimeDetail(malId);

        if (db.animeListDAO().getAnimeByMalID(malId) != null){
            inList = true;
        }

        viewModel.animeDetail.observe(this, anime -> {
            if (anime != null){
                title.setText(anime.getTitle());
                titleJapanese.setText(anime.getTitleJapanese());
                score.setText(String.valueOf(anime.getScore()));
                episodes.setText(String.valueOf("Episodes " + anime.getEpisodes()));
                description.setText(anime.getSynopsis());
                GlideApp.with(this)
                        .load(anime.getImageUrl())
                        .override(200, 270)
                        .placeholder(R.color.colorInactive)
                        .error(R.color.colorInactive)
                        .transforms(new CenterCrop(), new RoundedCorners(15))
                        .into(art);
            }
        });

        viewModel.objectId.observe(this, id -> {
            if (id != null){
                Toast.makeText(this, "Added to your list", Toast.LENGTH_SHORT).show();
                this.setResult(22);
            }
        });

    }
}
