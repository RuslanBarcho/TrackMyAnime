package io.vinter.trackmyanime.ui.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.util.Pair;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.vinter.trackmyanime.database.AppDatabase;
import io.vinter.trackmyanime.entity.animelist.AnimeListItem;
import io.vinter.trackmyanime.entity.detail.Anime;
import io.vinter.trackmyanime.network.NetModule;
import io.vinter.trackmyanime.network.service.AnimeService;

public class DetailViewModel extends ViewModel {

    public MutableLiveData<Anime> animeDetail = new MutableLiveData<>();
    public MutableLiveData<AnimeListItem> insertedAnime = new MutableLiveData<>();
    public MutableLiveData<Pair<String, AnimeListItem>> update = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public void getAnimeDetail(int malId){
        NetModule.getRetrofit().create(AnimeService.class)
                .getAnimeDetail(malId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(anime -> {
                    animeDetail.postValue(anime);
                }, e -> {
                    Log.e("Network", e.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void addToMyList(String token, AnimeListItem item, AppDatabase db){
        NetModule.getAnimeListModule().create(AnimeService.class)
                .addAnimeToList("Bearer " + token, item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    item.setId(message.getMessage());
                    db.animeListDAO().insertAll(item);
                    insertedAnime.postValue(item);
                }, e -> {
                    Log.e("Network", e.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void updateAnime(String token, AnimeListItem newAnime, AppDatabase db){
        NetModule.getAnimeListModule().create(AnimeService.class)
                .updateAnime("Bearer " + token, newAnime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    db.animeListDAO().update(newAnime);
                    update.postValue(new Pair<>(message.getMessage(), newAnime));
                }, e -> {
                    Log.e("Network", e.getMessage());
                });
    }

}
