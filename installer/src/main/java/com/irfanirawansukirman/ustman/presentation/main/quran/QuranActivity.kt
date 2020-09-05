package com.irfanirawansukirman.ustman.presentation.main.quran

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.irfanirawansukirman.abstraction.base.BaseActivity
import com.irfanirawansukirman.ustman.databinding.QuranActivityBinding

class QuranActivity : BaseActivity<QuranActivityBinding>(QuranActivityBinding::inflate) {

    override fun loadObservers() {

    }

    override fun onFirstLaunch(savedInstanceState: Bundle?) {

    }

    override fun continuousCall() {

    }

    override fun setupViewListener() {

    }

    override fun enableBackButton(): Boolean = false

    override fun bindToolbar(): Toolbar? = null

    override fun onDestroyActivities() {

    }
}