package io.vinter.trackmyanime.ui.search;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.vinter.trackmyanime.entity.search.Result;
import io.vinter.trackmyanime.entity.search.Search;
import io.vinter.trackmyanime.network.NetModule;
import io.vinter.trackmyanime.network.service.AnimeService;

public class SearchViewModel extends ViewModel {

    public static int STATE_EMPTY_RESULTS = 0;
    public static int LOADING = 1;
    public static int LOADED = 2;

    public MutableLiveData<List<Result>> searchResults = new MutableLiveData<>();
    public MutableLiveData<Integer> state = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public void searchAnime(String q, int page){
        state.postValue(LOADING);
        NetModule.getRetrofit().create(AnimeService.class)
                .searchAnime(q, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Search::getResults)
                .subscribe(searchResults -> {
                    this.searchResults.postValue(searchResults);
                    state.postValue(LOADED);
                }, e -> {
                    Log.e("Network", e.getMessage());
                });
    }

}
