package com.irfanirawansukirman.librarycache.dao

import androidx.room.Query
import com.irfanirawansukirman.librarycache.BaseDao
import com.irfanirawansukirman.librarycache.model.MovieEnt

interface MovieDao: BaseDao<MovieEnt> {

    @Query("SELECT * FROM tb_movie WHERE movie_id = :movieId")
    suspend fun getObject(movieId: Long): MovieEnt

    @Query("SELECT * FROM tb_movie ")
    suspend fun getAllObject(): List<MovieEnt>
}