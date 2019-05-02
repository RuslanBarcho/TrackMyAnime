package io.vinter.trackmyanime.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import io.vinter.trackmyanime.entity.animelist.AnimeListItem

@Dao
interface AnimeListDAO {

    @get:Query("SELECT * FROM animelist")
    val animeList: List<AnimeListItem>

    @Query("SELECT * FROM animelist WHERE malId = :malId")
    fun getAnimeByMalID(malId: Int): AnimeListItem?

    @Query("SELECT * FROM animelist WHERE id = :id")
    fun getAnimeById(id: String): AnimeListItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg animeListItems: AnimeListItem)

    @Delete
    fun delete(animeListItem: AnimeListItem)

    @Update
    fun update(animeListItem: AnimeListItem)

    @Query("DELETE FROM animelist")
    fun nukeTable()

}
