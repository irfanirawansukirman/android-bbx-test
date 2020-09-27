package com.irfanirawansukirman.featuredashboard.domain.usecase

import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.featuredashboard.data.model.PodsData
import com.irfanirawansukirman.featuredashboard.data.model.PodsStatus
import com.irfanirawansukirman.featuredashboard.domain.repository.PodsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PodsUseCaseImpl @Inject constructor(private val podsRepository: PodsRepository) :
    PodsUseCase {

    override suspend fun getPods(): Flow<UIState<List<PodsData>>> = podsRepository.getPods()
    override suspend fun getPodsByStatus(status: PodsStatus): Flow<UIState<List<PodsData>>> =
        podsRepository.getPodsByStatus(status)
}