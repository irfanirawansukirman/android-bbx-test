package com.irfanirawansukirman.ustman

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.time4j.android.ApplicationStarter

@HiltAndroidApp
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // time4J config
        ApplicationStarter.initialize(this, true) // with prefetch on background thread
    }
}