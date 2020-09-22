package com.irfanirawansukirman.librarycache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.irfanirawansukirman.librarycache.dao.MovieDao
import com.irfanirawansukirman.librarycache.model.MovieEnt

@Database(entities = [MovieEnt::class], version = 1, exportSchema = false)
abstract class DbConfig : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}