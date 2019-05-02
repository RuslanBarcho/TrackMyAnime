package io.vinter.trackmyanime.ui.detail

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.util.Pair

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.trackmyanime.database.AppDatabase
import io.vinter.trackmyanime.entity.animelist.AnimeListItem
import io.vinter.trackmyanime.entity.detail.Anime
import io.vinter.trackmyanime.network.NetModule
import io.vinter.trackmyanime.network.service.AnimeService

class DetailViewModel : ViewModel() {

    var animeDetail = MutableLiveData<Anime>()
    var insertedAnime = MutableLiveData<AnimeListItem>()
    var update = MutableLiveData<Pair<String, AnimeListItem>>()

    @SuppressLint("CheckResult")
    fun getAnimeDetail(malId: Int) {
        NetModule.retrofit.create(AnimeService::class.java)
                .getAnimeDetail(malId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ anime -> animeDetail.postValue(anime) }, { e  -> Log.e("Network", e.message) })
    }

    @SuppressLint("CheckResult")
    fun addToMyList(token: String, item: AnimeListItem, db: AppDatabase) {
        NetModule.animeListModule.create(AnimeService::class.java)
                .addAnimeToList("Bearer $token", item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message ->
                    item.id = message.message!!
                    db.animeListDAO().insertAll(item)
                    insertedAnime.postValue(item)
                }, { e  -> Log.e("Network", e.message) })
    }

    @SuppressLint("CheckResult")
    fun updateAnime(token: String, newAnime: AnimeListItem, db: AppDatabase) {
        NetModule.animeListModule.create(AnimeService::class.java)
                .updateAnime("Bearer $token", newAnime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message ->
                    db.animeListDAO().update(newAnime)
                    update.postValue(Pair(message.message!!, newAnime))
                }, { e -> Log.e("Network", e.message) })
    }

    @SuppressLint("CheckResult")
    fun updateImageUrl(token: String, toUpdate: AnimeListItem, newURL: String, db: AppDatabase) {
        toUpdate.imageUrl = newURL
        NetModule.animeListModule.create(AnimeService::class.java)
                .updateAnime("Bearer $token", toUpdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ db.animeListDAO().update(toUpdate) }, { e -> Log.e("Network", e.message) })
    }

}
