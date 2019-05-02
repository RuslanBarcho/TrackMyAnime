package io.vinter.trackmyanime.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import io.vinter.trackmyanime.entity.animelist.AnimeListItem

@Database(entities = [AnimeListItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animeListDAO(): AnimeListDAO
}
