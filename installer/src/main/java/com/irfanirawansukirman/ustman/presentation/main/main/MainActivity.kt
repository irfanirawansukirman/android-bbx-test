package com.irfanirawansukirman.ustman.presentation.main.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.irfanirawansukirman.abstraction.base.BaseActivity
import com.irfanirawansukirman.extensions.makeStatusBarTransparent
import com.irfanirawansukirman.extensions.navigation
import com.irfanirawansukirman.ustman.R
import com.irfanirawansukirman.ustman.databinding.MainActivityBinding
import com.irfanirawansukirman.ustman.presentation.main.main.daily.DailyFragment
import com.irfanirawansukirman.ustman.presentation.main.quran.QuranActivity
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : BaseActivity<MainActivityBinding>(MainActivityBinding::inflate),
    MultiplePermissionsListener, MainVMContract.View {

    private val mainVM by viewModels<MainVM>()

    override fun loadObservers() {

    }

    override fun onFirstLaunch(savedInstanceState: Bundle?) {
        makeStatusBarTransparent()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, DailyFragment.newInstance())
            .commit()
    }

    override fun continuousCall() {

    }

    override fun setupViewListener() {
        mViewBinding.apply {
            fabMainQuran.setOnClickListener { navigation<QuranActivity>() }
        }
    }

    override fun enableBackButton(): Boolean = false

    override fun bindToolbar(): Toolbar? = null

    override fun onDestroyActivities() {

    }

    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        report?.let {
            if (it.areAllPermissionsGranted()) {
                getBearing()
            } else {
                // showToast("All permissions is denied")
            }
        }
    }

    override fun onPermissionRationaleShouldBeShown(
        permissions: MutableList<PermissionRequest>?,
        token: PermissionToken?
    ) {
        token?.continuePermissionRequest()
    }

    override fun getBearing() {
        mainVM.getBearing()
    }
}