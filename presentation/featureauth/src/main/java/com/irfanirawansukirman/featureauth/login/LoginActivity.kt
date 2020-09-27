package com.irfanirawansukirman.featureauth.login

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.irfanirawansukirman.abstraction.base.BaseActivity
import com.irfanirawansukirman.extensions.createDialog
import com.irfanirawansukirman.extensions.navigationModule
import com.irfanirawansukirman.extensions.runCoroutine
import com.irfanirawansukirman.extensions.util.Const.Navigation.DashboardActivity
import com.irfanirawansukirman.extensions.util.Const.Time.DELAY_TIME_DEFAULT
import com.irfanirawansukirman.extensions.widget.setOnSingleClickListener
import com.irfanirawansukirman.featureauth.R
import com.irfanirawansukirman.featureauth.databinding.LoginActivityBinding

class LoginActivity : BaseActivity<LoginActivityBinding>(LoginActivityBinding::inflate) {

    override fun loadObservers() {

    }

    override fun onFirstLaunch(savedInstanceState: Bundle?) {

    }

    override fun continuousCall() {

    }

    override fun setupViewListener() {
        mViewBinding.btnLoginSubmit.setOnSingleClickListener {
            createDialog(R.layout.progress_dialog) {
                it.show()

                runCoroutine(delayTime = DELAY_TIME_DEFAULT) {
                    navigationModule(targetClass = DashboardActivity, withFinish = true)
                }
            }
        }
    }

    override fun enableBackButton(): Boolean = false

    override fun bindToolbar(): Toolbar? = null

    override fun onDestroyActivities() {}
}