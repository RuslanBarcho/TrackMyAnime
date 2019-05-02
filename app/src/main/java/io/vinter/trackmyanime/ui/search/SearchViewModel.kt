package io.vinter.trackmyanime.ui.search

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.trackmyanime.entity.search.Result
import io.vinter.trackmyanime.entity.search.Search
import io.vinter.trackmyanime.network.NetModule
import io.vinter.trackmyanime.network.service.AnimeService

class SearchViewModel : ViewModel() {

    var searchResults = MutableLiveData<List<Result>>()
    var state = MutableLiveData<Int>()

    @SuppressLint("CheckResult")
    fun searchAnime(q: String, page: Int) {
        state.postValue(LOADING)
        NetModule.retrofit.create(AnimeService::class.java)
                .searchAnime(q, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Search::results)
                .subscribe({ searchResults ->
                    this.searchResults.postValue(searchResults)
                    state.postValue(LOADED)
                }, { e: Throwable -> Log.e("Network", e.message) })
    }

    companion object {
        var STATE_EMPTY_RESULTS = 0
        var LOADING = 1
        var LOADED = 2
    }

}
