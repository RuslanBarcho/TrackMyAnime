package io.vinter.trackmyanime.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    AnimeListItem animeListItem;

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

    @BindView(R.id.animeDetailUserEps)
    Button userEpisodes;

    @OnClick(R.id.animeDetailAddToList)
    void addToList(){
        Anime anime = viewModel.animeDetail.getValue();
        if (!inList & anime != null) {
            AddDialogFragment dialogFragment = new AddDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "add_dialog");
        } else if (animeListItem != null) {
            animeListItem.setWatchedEps(animeListItem.getWatchedEps() + 1);
            if (animeListItem.getEps() != 0 & animeListItem.getEps() <= animeListItem.getWatchedEps()) animeListItem.setStatus("completed");
            else animeListItem.setStatus("watching");
            viewModel.updateAnime(preferences.getString("token", ""), animeListItem, db);
        }
    }

    @BindView(R.id.animeDetailAddToList)
    Button add;

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
            animeListItem = db.animeListDAO().getAnimeByMalID(malId);
            configUserEpisodes(animeListItem);
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

        viewModel.insertedAnime.observe(this, insertedAnime -> {
            if (insertedAnime != null){
                this.setResult(22);
                inList = true;
                animeListItem = insertedAnime;
                Toast.makeText(this, "Added to your list", Toast.LENGTH_SHORT).show();
                configUserEpisodes(insertedAnime);
            }
        });

        viewModel.update.observe(this, update -> {
            if (update != null){
                this.setResult(22);
                Toast.makeText(this, update.first, Toast.LENGTH_SHORT).show();
                configUserEpisodes(update.second);
                viewModel.update.postValue(null);
            }
        });

    }

    public void addToAnimeList(String status){
        Anime anime = viewModel.animeDetail.getValue();
        if (anime != null){
            viewModel.addToMyList(preferences.getString("token", ""), new AnimeListItem(anime, status), db);
        }
    }

    private void configUserEpisodes(AnimeListItem item){
        if (item.getStatus().equals("completed")){
           add.setVisibility(View.INVISIBLE);
        }
        userEpisodes.setVisibility(View.VISIBLE);
        userEpisodes.setText(String.valueOf(item.getWatchedEps() + "/" + item.getEps()));
    }
}
