package com.irfanirawansukirman.abstraction.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.irfanirawansukirman.extensions.overridePendingTransitionExit

abstract class BaseActivity<VB : ViewBinding>(private val viewBinder: (LayoutInflater) -> ViewBinding) :
    AppCompatActivity() {

    val mViewBinding by lazy { viewBinder.invoke(layoutInflater) as VB }

    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        setupToolbar()
        onFirstLaunch(savedInstanceState)
        setupViewListener()
        loadObservers()
    }

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    /**
     * Function for load livedata observer from viewmodel
     */
    abstract fun loadObservers()

    /**
     * Function for first launching like as onCreate
     */
    abstract fun onFirstLaunch(savedInstanceState: Bundle?)

    /**
     * Function for continously call after onCreate and onResume
     */
    abstract fun continuousCall()

    /**
     * Function for setup view listener
     */
    abstract fun setupViewListener()

    /**
     * Function for enable back button toolbar.
     * @return Boolean
     */
    abstract fun enableBackButton(): Boolean

    /**
     * Function for get toolbarId
     * @param void
     * @return Toolbar
     */
    abstract fun bindToolbar(): Toolbar?

    override fun onStart() {
        super.onStart()
        continuousCall()
    }

    /**
     * Method listener for menu toolbar
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }

    /**
     * Function for setup toolbar [inactive|active]
     * @param void
     * @return void
     */
    private fun setupToolbar() {
        bindToolbar()?.let {
            mToolbar = it
            setSupportActionBar(mToolbar)
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(enableBackButton())
            }
        }
    }

    fun getParentToolbar(): Toolbar? = mToolbar
}