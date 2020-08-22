package com.irfanirawansukirman.ustman.presentation.main.qibla

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import com.irfanirawansukirman.abstraction.base.BaseFragment
import com.irfanirawansukirman.abstraction.util.compass.Compass
import com.irfanirawansukirman.abstraction.util.compass.GPSTracker
import com.irfanirawansukirman.extensions.putArgs
import com.irfanirawansukirman.extensions.widget.notVisible
import com.irfanirawansukirman.extensions.widget.show
import com.irfanirawansukirman.ustman.R
import com.irfanirawansukirman.ustman.databinding.QiblaFragmentBinding
import kotlinx.android.synthetic.main.qibla_fragment.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class QiblaFragment :
    BaseFragment<QiblaFragmentBinding>(QiblaFragmentBinding::inflate, R.layout.qibla_fragment) {

    private lateinit var gpsTracker: GPSTracker
    private lateinit var sharedPreferences: SharedPreferences

    private var compass: Compass? = null
    private var currentAzimuth = 0.0F

    override fun loadObservers() {

    }

    override fun onFirstLaunch(savedInstanceState: Bundle?) {
        setupPreferences()
        setupGpsTracker()
        setupCompass()
    }

    override fun continuousCall() {
        compass?.start()
    }

    override fun setupViewListener() {

    }

    override fun onPause() {
        super.onPause()
        compass?.stop()
    }

    override fun onResume() {
        super.onResume()
        compass?.start()
    }

    override fun onStop() {
        super.onStop()
        compass?.stop()
    }

    private fun setupPreferences() {
        if (!::sharedPreferences.isInitialized) {
            sharedPreferences = requireContext().getSharedPreferences("", MODE_PRIVATE)
        }
    }

    private fun setupGpsTracker() {
        if (!::gpsTracker.isInitialized) {
            gpsTracker = GPSTracker(requireContext())
        }
    }

    private fun setupCompass() {
        getBearing()

        compass = Compass(requireContext())
        compass?.setListener(object : Compass.CompassListener {
            override fun onNewAzimuth(azimuth: Float) {
                navigateArrowQibla(azimuth)
            }
        })
    }

    private fun navigateArrowQibla(azimuth: Float) {
        val animation = RotateAnimation(-currentAzimuth, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )

        currentAzimuth = azimuth

        animation.apply {
            duration = 500
            repeatCount = 0
            fillAfter = true
        }
        imgQibla.animation = animation
    }

    private fun getBearing() {
        val qiblaDegree = getFloat("qiblaDegree")
        if (qiblaDegree > 0.0001) {

        } else {
            fetchGps()
        }
    }

    private fun fetchGps() {
        val result: Double
        if (gpsTracker.canGetLocation()) {
            val myLatitude: Double = gpsTracker.latitude
            val myLongitude: Double = gpsTracker.longitude

            if (myLatitude < 0.001 && myLongitude < 0.001) {
                imgQibla.notVisible()
            } else {
                val longitude2 = 39.826206 // ka'bah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                val longitude1: Double = myLongitude
                val latitude2 =
                    Math.toRadians(21.422487) // ka'bah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                val latitude1 = Math.toRadians(myLatitude)
                val longDiff = Math.toRadians(longitude2 - longitude1)
                val y = sin(longDiff) * cos(latitude2)
                val x = cos(latitude1) * sin(latitude2) - sin(latitude1) * cos(latitude2) * cos(longDiff)
                result = (Math.toDegrees(atan2(y, x)) + 360) % 360
                val result2 = result.toFloat()
                saveFloat("qiblaDegree", result2)
                imgQibla.show()
            }
        }
    }

    private fun getFloat(key: String): Float = sharedPreferences.getFloat(key, 0.0F)

    private fun saveFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    companion object {
        fun newInstance() = QiblaFragment().putArgs { }
    }
}