package io.vinter.trackmyanime.ui.top

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.util.Pair

import java.util.ArrayList

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.trackmyanime.entity.top.AnimeTop
import io.vinter.trackmyanime.entity.top.Top
import io.vinter.trackmyanime.network.NetModule
import io.vinter.trackmyanime.network.service.AnimeService

class TopViewModel : ViewModel() {

    var tops = MutableLiveData<List<Pair<List<AnimeTop>, String>>>()
    var error = MutableLiveData<String>()
    private val animeTopList = ArrayList<Pair<List<AnimeTop>, String>>()
    var loading = false

    fun getAnimeTops(types: Array<String>) {
        animeTopList.clear()
        loadTops(types, 0)
    }

    @SuppressLint("CheckResult")
    private fun loadTops(types: Array<String>, i: Int) {
        loading = true
        NetModule.retrofit.create(AnimeService::class.java)
                .getAnimeTop(1, types[i])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Top::top)
                .subscribe({ list ->
                    animeTopList.add(Pair(list, types[i]))
                    if (animeTopList.size == types.size) {
                        tops.postValue(animeTopList)
                        loading = false
                    } else {
                        loadTops(types, i + 1)
                    }
                }) {
                    error.postValue("Unable to load tops")
                    loading = false
                }
    }

}
