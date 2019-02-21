package io.vinter.trackmyanime.entity.animelist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.vinter.trackmyanime.entity.detail.Anime;

@Entity(tableName = "animelist")
public class AnimeListItem {
    @NonNull
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    private String id;
    @ColumnInfo(name = "malId")
    @SerializedName("malId")
    @Expose
    private Integer malId;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;
    @ColumnInfo(name = "recOwner")
    @SerializedName("recOwner")
    @Expose
    private String recOwner;
    @ColumnInfo(name = "eps")
    @SerializedName("eps")
    @Expose
    private Integer eps;
    @ColumnInfo(name = "imageUrl")
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @ColumnInfo(name = "watchedEps")
    @SerializedName("watchedEps")
    @Expose
    private Integer watchedEps;
    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    private String status;

    public AnimeListItem(){}

    public AnimeListItem(Anime anime, String status){
        this.malId = anime.getMalId();
        this.title = anime.getTitle();
        if (anime.getEpisodes() == null) {
            this.eps = 0;
        } else {
            this.eps = anime.getEpisodes();
        }
        this.imageUrl = anime.getImageUrl();
        this.watchedEps = 0;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMalId() {
        return malId;
    }

    public void setMalId(Integer malId) {
        this.malId = malId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecOwner() {
        return recOwner;
    }

    public void setRecOwner(String recOwner) {
        this.recOwner = recOwner;
    }

    public Integer getEps() {
        return eps;
    }

    public void setEps(Integer eps) {
        this.eps = eps;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getWatchedEps() {
        return watchedEps;
    }

    public void setWatchedEps(Integer watchedEps) {
        this.watchedEps = watchedEps;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}