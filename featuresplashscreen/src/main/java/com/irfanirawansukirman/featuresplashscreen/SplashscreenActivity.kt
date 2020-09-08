package com.irfanirawansukirman.featuresplashscreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.irfanirawansukirman.abstraction.base.BaseActivity
import com.irfanirawansukirman.extensions.navigationModule
import com.irfanirawansukirman.extensions.runCoroutine
import com.irfanirawansukirman.extensions.util.Const.Navigation
import com.irfanirawansukirman.featuresplashscreen.databinding.SplashscreenActivityBinding
import com.irfanirawansukirman.libraryanalytic.Sentry

class SplashscreenActivity :
    BaseActivity<SplashscreenActivityBinding>(SplashscreenActivityBinding::inflate) {

    private val viewModel by viewModels<SplashscreenVM>()

    override fun loadObservers() {

    }

    override fun onFirstLaunch(savedInstanceState: Bundle?) {
        runCoroutine(3_000) {
            Sentry.createBreadcrumb(SplashscreenActivity::class.java.simpleName + " is navigate")
            navigationModule(targetClass = Navigation.MainActivity, withFinish = true)
        }
    }

    override fun continuousCall() {

    }

    override fun setupViewListener() {

    }

    override fun enableBackButton(): Boolean = false

    override fun bindToolbar(): Toolbar? = null

    override fun onDestroyActivities() {}
}