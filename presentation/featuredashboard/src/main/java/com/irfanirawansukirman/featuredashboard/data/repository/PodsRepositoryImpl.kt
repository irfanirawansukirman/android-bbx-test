package com.irfanirawansukirman.featuredashboard.data.repository

import android.content.Context
import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.extensions.widget.orDefault
import com.irfanirawansukirman.featuredashboard.data.DataFactory.getEmptyPods
import com.irfanirawansukirman.featuredashboard.data.DataFactory.getJsonDataFromAsset
import com.irfanirawansukirman.featuredashboard.data.model.Pods
import com.irfanirawansukirman.featuredashboard.data.model.PodsData
import com.irfanirawansukirman.featuredashboard.data.model.PodsStatus
import com.irfanirawansukirman.featuredashboard.domain.repository.PodsRepository
import com.irfanirawansukirman.libraryutil.CoroutineContextProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PodsRepositoryImpl @Inject constructor(
    private val context: Context,
    private val coroutineContextProvider: CoroutineContextProvider,
) : PodsRepository {

    override suspend fun getPods(): Flow<UIState<List<PodsData>>> {
        return flow<UIState<List<PodsData>>> {
            emit(UIState.loading())
            try {
                val response = getJsonDataFromAsset(context).orDefault(getEmptyPods())
                val dataMap = Json.decodeFromString<Pods>(response)
                val pods = dataMap.data.orDefault(emptyList())
                emit(UIState.success(pods))
            } catch (e: Exception) {
                emit(UIState.error(e.message.orDefault("Something Went Wrong")))
            }
            emit(UIState.finish())
        }.flowOn(coroutineContextProvider.io)
    }

    override suspend fun getPodsByStatus(pods: PodsStatus): Flow<UIState<List<PodsData>>> {
        return flow<UIState<List<PodsData>>> {
            emit(UIState.loading())
            try {
                val response = getJsonDataFromAsset(context).orDefault(getEmptyPods())
                val dataMap = Json.decodeFromString<Pods>(response)
                val data = dataMap.data.orDefault(emptyList())
                val podsByStatus = if (pods.status == "AS") {
                    data
                } else {
                    data.filter { it.roomStatusCode == pods.status }
                }
                emit(UIState.success(podsByStatus))
            } catch (e: Exception) {
                emit(UIState.error(e.message.orDefault("Something Went Wrong")))
            }
            emit(UIState.finish())
        }.flowOn(coroutineContextProvider.io)
    }
}
