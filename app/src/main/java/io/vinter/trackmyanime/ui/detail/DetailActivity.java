package io.vinter.trackmyanime.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.elyeproj.loaderviewlibrary.LoaderTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.ui.main.MainViewModel;
import io.vinter.trackmyanime.utils.GlideApp;

public class DetailActivity extends AppCompatActivity {

    DetailViewModel viewModel;

    @BindView(R.id.animeDetailPicture)
    ImageView art;

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

    @OnClick(R.id.detailBack)
    void goBack(){
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        viewModel.getAnimeDetail(getIntent().getIntExtra("malId", 0));

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

    }
}
