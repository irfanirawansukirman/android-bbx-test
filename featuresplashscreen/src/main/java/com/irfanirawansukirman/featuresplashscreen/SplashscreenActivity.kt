package com.irfanirawansukirman.featuresplashscreen

import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.irfanirawansukirman.extensions.navigationModule
import com.irfanirawansukirman.extensions.util.Const.Navigation

class SplashscreenActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashscreenVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen_activity)

        Handler().postDelayed({
            navigationModule(targetClass = Navigation.MainActivity, withFinish = true) {}
        }, 3_000)
    }
}