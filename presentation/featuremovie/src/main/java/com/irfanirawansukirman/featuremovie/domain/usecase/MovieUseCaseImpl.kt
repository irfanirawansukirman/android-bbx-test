package com.irfanirawansukirman.featuremovie.domain.usecase

import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.featuremovie.data.model.Result
import com.irfanirawansukirman.featuremovie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(private val movieRepository: MovieRepository) :
    MovieUseCase {

    override suspend fun invoke(): Flow<UIState<List<Result>>> = movieRepository.getMovies()
}