package com.irfanirawansukirman.featuremovie.data.service

import com.irfanirawansukirman.featuremovie.data.model.Movie
import retrofit2.http.GET

interface MovieService {

    @GET("movie/popular?api_key=1b2f29d43bf2e4f3142530bc6929d341")
    suspend fun getMovies(): Movie
}