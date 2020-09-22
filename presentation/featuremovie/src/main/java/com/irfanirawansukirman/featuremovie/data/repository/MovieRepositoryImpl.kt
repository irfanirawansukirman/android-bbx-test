package com.irfanirawansukirman.featuremovie.data.repository

import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.extensions.widget.orDefault
import com.irfanirawansukirman.featuremovie.data.model.Result
import com.irfanirawansukirman.featuremovie.data.service.MovieService
import com.irfanirawansukirman.featuremovie.domain.repository.MovieRepository
import com.irfanirawansukirman.libraryutil.CoroutineContextProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider,
    private val movieService: MovieService
) : MovieRepository {
    override suspend fun getMovies(): Flow<UIState<List<Result>>> {
        return flow<UIState<List<Result>>> {
            emit(UIState.loading())
            try {
                val response = movieService.getMovies().results.orDefault(null)
                emit(UIState.success(response))
            } catch (e: Exception) {
                emit(UIState.error(e.message ?: "Something Went Wrong"))
            }
            emit(UIState.finish())
        }.flowOn(coroutineContextProvider.io)
    }
}