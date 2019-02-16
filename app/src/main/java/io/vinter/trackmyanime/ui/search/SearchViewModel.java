package io.vinter.trackmyanime.ui.search;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.vinter.trackmyanime.entity.search.Result;
import io.vinter.trackmyanime.entity.search.Search;
import io.vinter.trackmyanime.network.NetModule;
import io.vinter.trackmyanime.network.service.AnimeService;

public class SearchViewModel extends ViewModel {

    public MutableLiveData<List<Result>> searchResults = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public void searchAnime(String q, int page){
        NetModule.getRetrofit().create(AnimeService.class)
                .searchAnime(q, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Search::getResults)
                .subscribe(searchResults::postValue, e -> {
                    Log.e("Network", e.getMessage());
                });
    }

}
