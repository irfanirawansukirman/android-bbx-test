package com.irfanirawansukirman.featuresplashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irfanirawansukirman.abstraction.base.BaseViewModel

interface SplashscreenContract {
    suspend fun getAdzanSchedules(latitude: Double, longitude: Double)
}

class SplashscreenVM : BaseViewModel(), SplashscreenContract {

    private val _adzanSchedules = MutableLiveData<List<String>>()
    val adzanSchedules: LiveData<List<String>>
        get() = _adzanSchedules

    override suspend fun getAdzanSchedules(latitude: Double, longitude: Double) {
        _adzanSchedules.value = emptyList()
    }
}