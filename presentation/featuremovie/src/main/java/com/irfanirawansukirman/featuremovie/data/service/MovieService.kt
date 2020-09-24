package com.irfanirawansukirman.featuremovie.data.service

import com.irfanirawansukirman.featuremovie.data.model.Movie
import com.irfanirawansukirman.librarynetwork.BuildConfig
import retrofit2.http.GET

interface MovieService {

    @GET("movie/popular?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getMovies(): Movie
}