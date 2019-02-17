package io.vinter.trackmyanime.ui.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.vinter.trackmyanime.entity.detail.Anime;
import io.vinter.trackmyanime.network.NetModule;
import io.vinter.trackmyanime.network.service.AnimeService;

public class DetailViewModel extends ViewModel {

    public MutableLiveData<Anime> animeDetail = new MutableLiveData<>();

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

}
