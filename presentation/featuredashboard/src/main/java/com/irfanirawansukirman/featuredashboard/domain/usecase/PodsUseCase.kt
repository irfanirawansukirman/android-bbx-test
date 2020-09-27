package com.irfanirawansukirman.featuredashboard.domain.usecase

import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.featuredashboard.data.model.PodsData
import com.irfanirawansukirman.featuredashboard.data.model.PodsStatus
import kotlinx.coroutines.flow.Flow

interface PodsUseCase {
    suspend fun getPods(): Flow<UIState<List<PodsData>>>
    suspend fun getPodsByStatus(status: PodsStatus): Flow<UIState<List<PodsData>>>
}