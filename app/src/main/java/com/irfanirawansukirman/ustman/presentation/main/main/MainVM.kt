package com.irfanirawansukirman.ustman.presentation.main.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.irfanirawansukirman.abstraction.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface MainVMContract {
    interface View {
        fun getBearing()
    }
}

class MainVM : BaseViewModel(), MainVMContract.View {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _bearing = MutableLiveData<Boolean>()
    val bearing: LiveData<Boolean>
        get() = _bearing

    override fun getBearing() {
        viewModelScope.launch(Dispatchers.Main) {
            delay(1_000)
            _bearing.value = true
        }
    }
}