package com.irfanirawansukirman.featuresplashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

interface SplashscreenContract {
    suspend fun getAdzanSchedules(latitude: Double, longitude: Double)
}

class SplashscreenVM : ViewModel(), SplashscreenContract {

    private val _adzanSchedules = MutableLiveData<List<String>>()
    val adzanSchedules: LiveData<List<String>>
        get() = _adzanSchedules

    override suspend fun getAdzanSchedules(latitude: Double, longitude: Double) {
        _adzanSchedules.value = emptyList()
    }
}