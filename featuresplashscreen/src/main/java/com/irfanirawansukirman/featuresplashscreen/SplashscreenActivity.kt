package com.irfanirawansukirman.featuresplashscreen

import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.irfanirawansukirman.extensions.navigationModule
import com.irfanirawansukirman.extensions.util.Const.Navigation
import com.irfanirawansukirman.libraryanalytic.Sentry

class SplashscreenActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashscreenVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen_activity)

        Handler().postDelayed({
            Sentry.createBreadcrumb(SplashscreenActivity::class.java.simpleName + " is navigate")
            navigationModule(targetClass = Navigation.MainActivity, withFinish = true) {}
        }, 3_000)
    }
}