package com.irfanirawansukirman.featuredashboard.presentation.dashboard

import android.app.Application
import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.abstraction.base.BaseViewModel
import com.irfanirawansukirman.featuredashboard.data.model.PodsData
import com.irfanirawansukirman.featuredashboard.data.model.PodsStatus
import com.irfanirawansukirman.featuredashboard.domain.usecase.PodsUseCaseImpl
import com.irfanirawansukirman.libraryutil.CoroutineContextProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

interface DashboardContract {
    interface ToDashboard {
        fun getPods()
    }

    interface PodsFilter {
        fun getPodsByStatus(status: PodsStatus)
    }
}

class DashboardVM @ViewModelInject constructor(
    @ApplicationContext val context: Context,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val podsRepositoryImpl: PodsUseCaseImpl
) : BaseViewModel(context as Application, coroutineContextProvider), DashboardContract.ToDashboard,
    DashboardContract.PodsFilter {

    private val _pods = MutableLiveData<UIState<List<PodsData>>>()
    val pods: LiveData<UIState<List<PodsData>>>
        get() = _pods

    private val _status = MutableLiveData<PodsStatus>()
    val status: LiveData<PodsStatus>
        get() = _status

    override fun getPods() {
        executeJob {
            podsRepositoryImpl
                .getPods()
                .collect {
                    withContext(coroutineContextProvider.main) {
                        _pods.value = it
                    }
                }
        }
    }

    override fun getPodsByStatus(status: PodsStatus) {
        _status.value = status

        executeJob {
            podsRepositoryImpl
                .getPodsByStatus(status)
                .collect {
                    withContext(coroutineContextProvider.main) {
                        _pods.value = it
                    }
                }
        }
    }
}