package io.vinter.trackmyanime.ui.profile;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.vinter.trackmyanime.database.AppDatabase;
import io.vinter.trackmyanime.entity.animelist.AnimeListItem;
import io.vinter.trackmyanime.entity.animelist.ListResponse;
import io.vinter.trackmyanime.network.NetModule;
import io.vinter.trackmyanime.network.service.AnimeService;

public class ProfileViewModel extends ViewModel {

    public MutableLiveData<List<AnimeListItem>> animes = new MutableLiveData<>();
    public MutableLiveData<String> update = new MutableLiveData<>();
    public boolean loading = false;

    @SuppressLint("CheckResult")
    public void getAnimeList(String token, AppDatabase db){
        loading = true;
        NetModule.getAnimeListModule().create(AnimeService.class)
                .getAnimeList("Bearer " + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ListResponse::getAnimes)
                .subscribe(animes -> {
                    updateDatabase(db, animes);
                    this.animes.postValue(animes);
                    loading = false;
                }, e -> {
                    animes.postValue(db.animeListDAO().getAnimeList());
                    Log.e("Network", e.getMessage());
                    loading = false;
                });
    }

    @SuppressLint("CheckResult")
    public void updateAnime(String token, int malId, int episodes, AppDatabase db){
        AnimeListItem newAnime = db.animeListDAO().getAnimeByMalID(malId);
        newAnime.setWatchedEps(newAnime.getWatchedEps() + episodes);
        if (newAnime.getEps() != 0 & newAnime.getEps() <= newAnime.getWatchedEps()) newAnime.setStatus("completed");
        else newAnime.setStatus("watching");

        NetModule.getAnimeListModule().create(AnimeService.class)
                .updateAnime("Bearer " + token, newAnime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    db.animeListDAO().update(newAnime);
                    update.postValue(message.getMessage());
                }, e -> {
                    Log.e("Network", e.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void deleteAnime(String token, int malId, AppDatabase db){
        AnimeListItem animeToDelete = db.animeListDAO().getAnimeByMalID(malId);
        NetModule.getAnimeListModule().create(AnimeService.class)
                .deleteAnime("Bearer " + token, animeToDelete)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    db.animeListDAO().delete(animeToDelete);
                    update.postValue(message.getMessage());
                }, e -> {
                    Log.e("Network", e.getMessage());
                });
    }

    private void updateDatabase(AppDatabase db, List<AnimeListItem> toUpdate){
        for (AnimeListItem item: toUpdate) db.animeListDAO().insertAll(item);
    }
}
