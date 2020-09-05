package com.irfanirawansukirman.ustman.presentation.main.main.daily

import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.irfanirawansukirman.abstraction.base.BaseFragment
import com.irfanirawansukirman.abstraction.util.components.AppBarStateChangeListener
import com.irfanirawansukirman.extensions.putArgs
import com.irfanirawansukirman.extensions.widget.setLinearList
import com.irfanirawansukirman.ustman.databinding.DailyFragmentBinding

class DailyFragment : BaseFragment<DailyFragmentBinding>(DailyFragmentBinding::inflate) {

    override fun onGetArguments(arguments: Bundle?) {

    }

    override fun loadObservers() {

    }

    override fun onFirstLaunch(savedInstanceState: Bundle?) {
        mViewBinding.apply {
            recyclerDaily.apply {
                setLinearList()
                adapter = DailyAdapter()
            }
            appbarDaily.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
                override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                    when (state?.name) {
                        "EXPANDED" -> {
                        }
                        "COLLAPSED" -> {
                        }
                        else -> {
                        } // do nothing
                    }
                }
            })
            //            source: https://stackoverflow.com/a/49683649
//            appbarDaily.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
//                var fraction = abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange
//                val peopleRange: Int = imgDailyBackdrop.bottom
//                val delay = 0.25f
//                val speed = 2f
//                fraction = max(fraction - delay, 0f) * speed
//                imgDailyBackdrop.translationY = fraction * peopleRange * -1
//            })
        }
    }

    override fun continuousCall() {

    }

    override fun setupViewListener() {

    }

    override fun onDestroyActivities() {

    }

    companion object {
        fun newInstance() = DailyFragment().putArgs { }
    }
}