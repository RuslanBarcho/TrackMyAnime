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
import io.vinter.trackmyanime.entity.top.AnimeTop;
import io.vinter.trackmyanime.entity.top.Top;
import io.vinter.trackmyanime.network.NetModule;
import io.vinter.trackmyanime.network.service.AnimeService;

public class TopViewModel extends ViewModel {

    public MutableLiveData<List<Pair<List<AnimeTop>, String>>> tops = new MutableLiveData<>();
    public boolean loading = false;
    List<Pair<List<AnimeTop>, String>> animeTopList = new ArrayList<>();

    @SuppressLint("CheckResult")
    public void getAnimeTops(String[] types){
        animeTopList.clear();
        loadTops(types, 0);
    }

    @SuppressLint("CheckResult")
    private void loadTops(String[] types, int i){
        loading = true;
        NetModule.getRetrofit().create(AnimeService.class)
                .getAnimeTop(1, types[i])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Top::getTop)
                .subscribe(list -> {
                    animeTopList.add(new Pair<>(list, types[i]));
                    if (animeTopList.size() == types.length) {
                        tops.postValue(animeTopList);
                        loading = false;
                    } else {
                        loadTops(types, i + 1);
                    }
                }, e -> {
                    Log.e("Network", e.getMessage());
                    loading = false;
                });
    }

}
