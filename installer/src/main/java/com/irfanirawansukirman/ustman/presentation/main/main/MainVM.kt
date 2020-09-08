package com.irfanirawansukirman.ustman.presentation.main.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.abstraction.base.BaseViewModel
import com.irfanirawansukirman.libraryutil.CoroutineContextProvider
import kotlinx.coroutines.withContext

interface MainVMContract {
    interface View {
        fun getBearing()
    }
}

class MainVM(
    context: Context,
    private val coroutineContextProvider: CoroutineContextProvider
) : BaseViewModel(
    context as Application,
    coroutineContextProvider
), MainVMContract.View {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _bearing = MutableLiveData<Boolean>()
    val bearing: LiveData<Boolean>
        get() = _bearing

    private val _test = MutableLiveData<String>()
    val test: LiveData<String>
        get() = _test

    private val _state = MutableLiveData<UIState<String?>>()
    val state: LiveData<UIState<String?>>
        get() = _state

    override fun getBearing() {
        executeJob {
            // requesting from network or cache
            val result = ""

            // switching process from io to main thread
            withContext(coroutineContextProvider.main) {

            }
        }
    }
}