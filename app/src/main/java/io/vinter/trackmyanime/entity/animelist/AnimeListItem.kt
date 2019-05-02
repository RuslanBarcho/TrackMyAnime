package io.vinter.trackmyanime.entity.animelist

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

import io.vinter.trackmyanime.entity.detail.Anime

@Entity(tableName = "animelist")
class AnimeListItem : Serializable {
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    lateinit var id: String
    @ColumnInfo(name = "malId")
    @SerializedName("malId")
    @Expose
    var malId: Int? = null
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    var title: String? = null
    @ColumnInfo(name = "recOwner")
    @SerializedName("recOwner")
    @Expose
    var recOwner: String? = null
    @ColumnInfo(name = "eps")
    @SerializedName("eps")
    @Expose
    var eps: Int? = null
    @ColumnInfo(name = "imageUrl")
    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String? = null
    @ColumnInfo(name = "watchedEps")
    @SerializedName("watchedEps")
    @Expose
    var watchedEps: Int? = null
    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    var status: String? = null

    constructor()

    constructor(anime: Anime, status: String) {
        this.malId = anime.malId
        this.title = anime.title
        if (anime.episodes == null) {
            this.eps = 0
        } else {
            this.eps = anime.episodes
        }
        this.imageUrl = anime.imageUrl
        this.watchedEps = 0
        this.status = status
    }

}