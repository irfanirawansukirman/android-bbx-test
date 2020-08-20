package com.irfanirawansukirman.ustman

import android.app.Application
import net.time4j.android.ApplicationStarter

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationStarter.initialize(this, true) // with prefetch on background thread
    }
}