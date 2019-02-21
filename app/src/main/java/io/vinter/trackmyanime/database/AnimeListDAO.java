package io.vinter.trackmyanime.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.vinter.trackmyanime.entity.animelist.AnimeListItem;

@Dao
public interface AnimeListDAO {

    @Query("SELECT * FROM animelist")
    List<AnimeListItem> getAnimeList();

    @Query("SELECT * FROM animelist WHERE malId = :malId")
    AnimeListItem getAnimeByMalID(int malId);

    @Query("SELECT * FROM animelist WHERE id = :id")
    AnimeListItem getAnimeById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AnimeListItem... animeListItems);

    @Delete
    void delete(AnimeListItem animeListItem);

    @Update
    void update(AnimeListItem animeListItem);

    @Query("DELETE FROM animelist")
    public void nukeTable();

}
