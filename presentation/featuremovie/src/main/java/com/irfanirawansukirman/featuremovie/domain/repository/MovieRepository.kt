package com.irfanirawansukirman.featuremovie.domain.repository

import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.featuremovie.data.model.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(): Flow<UIState<List<Result>>>
}