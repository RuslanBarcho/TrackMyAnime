package io.vinter.trackmyanime.ui.top;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.vinter.trackmyanime.entity.AnimeTop;
import io.vinter.trackmyanime.entity.Top;
import io.vinter.trackmyanime.network.NetModule;
import io.vinter.trackmyanime.network.service.AnimeService;

public class TopViewModel extends ViewModel {

    public MutableLiveData<List<AnimeTop>> topUpcoming = new MutableLiveData<>();
    public MutableLiveData<List<Pair<List<AnimeTop>, String>>> tops = new MutableLiveData<>();
    List<Pair<List<AnimeTop>, String>> animeTopList = new ArrayList<>();

    @SuppressLint("CheckResult")
    public void getAnimeTop(String type, int page){
        NetModule.getRetrofit().create(AnimeService.class)
                .getAnimeTop(page, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Top::getTop)
                .subscribe(animeTopList -> {
                    topUpcoming.postValue(animeTopList);
                }, e -> {
                    Log.e("Network", e.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void getAnimeTops(String[] types){
        animeTopList.clear();
        for (String type: types){
            NetModule.getRetrofit().create(AnimeService.class)
                    .getAnimeTop(1, type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(Top::getTop)
                    .subscribe(list -> {
                        animeTopList.add(new Pair<>(list, type));
                        if (animeTopList.size() == types.length) tops.postValue(animeTopList);
                    }, e -> {
                        Log.e("Network", e.getMessage());
                    });
        }

    }
}
