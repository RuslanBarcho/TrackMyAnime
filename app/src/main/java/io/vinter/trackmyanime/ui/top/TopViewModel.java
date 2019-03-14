package io.vinter.trackmyanime.ui.top;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;

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
    public MutableLiveData<String> error = new MutableLiveData<>();
    private List<Pair<List<AnimeTop>, String>> animeTopList = new ArrayList<>();
    public boolean loading = false;

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
                    error.postValue("Unable to load tops");
                    loading = false;
                });
    }

}
