package com.irfanirawansukirman.featuredashboard.domain.repository

import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.featuredashboard.data.model.PodsData
import com.irfanirawansukirman.featuredashboard.data.model.PodsStatus
import kotlinx.coroutines.flow.Flow

interface PodsRepository {
    suspend fun getPods(): Flow<UIState<List<PodsData>>>
    suspend fun getPodsByStatus(pods: PodsStatus): Flow<UIState<List<PodsData>>>
}