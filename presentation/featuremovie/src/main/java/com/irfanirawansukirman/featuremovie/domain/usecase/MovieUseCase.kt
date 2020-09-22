package com.irfanirawansukirman.featuremovie.domain.usecase

import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.featuremovie.data.model.Result
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    suspend operator fun invoke(): Flow<UIState<List<Result>>>
}