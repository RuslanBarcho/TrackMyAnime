package io.vinter.trackmyanime.ui.profile

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.trackmyanime.database.AppDatabase
import io.vinter.trackmyanime.entity.animelist.AnimeListItem
import io.vinter.trackmyanime.entity.animelist.ListResponse
import io.vinter.trackmyanime.entity.user.Message
import io.vinter.trackmyanime.network.NetModule
import io.vinter.trackmyanime.network.service.AnimeService
import retrofit2.HttpException

class ProfileViewModel : ViewModel() {

    var animes = MutableLiveData<List<AnimeListItem>>()
    var update = MutableLiveData<String>()
    var error = MutableLiveData<String>()
    var loading = false

    fun getAnimeList(token: String, db: AppDatabase) {
        loading = true
        NetModule.animeListModule.create(AnimeService::class.java)
                .getAnimeList("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ListResponse::animes)
                .subscribe({ animes ->
                    updateDatabase(db, animes!!)
                    this.animes.postValue(animes)
                    loading = false
                }, { e ->
                    animes.postValue(db.animeListDAO().animeList)
                    Log.e("Network", e.message)
                    loading = false
                })
    }

    fun updateAnime(token: String, malId: Int, episodes: Int, db: AppDatabase) {
        val newAnime = db.animeListDAO().getAnimeByMalID(malId)
        newAnime!!.watchedEps = newAnime.watchedEps!! + episodes
        if ((newAnime.eps != 0) and (newAnime.eps!! <= newAnime.watchedEps!!))
            newAnime.status = "completed"
        else
            newAnime.status = "watching"
        NetModule.animeListModule.create(AnimeService::class.java)
                .updateAnime("Bearer $token", newAnime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message: Message ->
                    db.animeListDAO().update(newAnime)
                    update.postValue(message.message)
                }, { e -> if (e is HttpException) error.postValue(e.localizedMessage)
                else error.postValue("No internet connection")})
    }

    fun deleteAnime(token: String, malId: Int, db: AppDatabase) {
        val animeToDelete = db.animeListDAO().getAnimeByMalID(malId)
        NetModule.animeListModule.create(AnimeService::class.java)
                .deleteAnime("Bearer $token", animeToDelete!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message: Message ->
                    db.animeListDAO().delete(animeToDelete)
                    update.postValue(message.message)
                }, { e -> if (e is HttpException) error.postValue(e.localizedMessage)
                else error.postValue("No internet connection")})
    }

    private fun updateDatabase(db: AppDatabase, toUpdate: List<AnimeListItem>) {
        for (item in toUpdate) db.animeListDAO().insertAll(item)
    }
}
