package com.irfanirawansukirman.ustman.presentation.main.main.qibla

import android.content.Context.LOCATION_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.fragment.app.activityViewModels
import com.irfanirawansukirman.abstraction.base.BaseFragment
import com.irfanirawansukirman.abstraction.util.compass.Compass
import com.irfanirawansukirman.abstraction.util.compass.GPSTracker
import com.irfanirawansukirman.extensions.*
import com.irfanirawansukirman.extensions.util.Const.Navigation
import com.irfanirawansukirman.extensions.util.Const.Permission
import com.irfanirawansukirman.extensions.widget.notVisible
import com.irfanirawansukirman.extensions.widget.show
import com.irfanirawansukirman.ustman.R
import com.irfanirawansukirman.ustman.databinding.QiblaFragmentBinding
import com.irfanirawansukirman.ustman.presentation.main.main.MainActivity
import com.irfanirawansukirman.ustman.presentation.main.main.MainVM
import kotlinx.android.synthetic.main.qibla_fragment.*
import java.io.IOException
import java.util.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


class QiblaFragment : BaseFragment<QiblaFragmentBinding>(
    QiblaFragmentBinding::inflate) {

    private lateinit var gpsTracker: GPSTracker
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var locationManager: LocationManager

    private val mainVM by activityViewModels<MainVM>()

    private var compass: Compass? = null
    private var currentAzimuth = 0.0F
    private var hasGpsEnabled = false

    override fun onGetArguments(arguments: Bundle?) {

    }

    override fun loadObservers() {
        mainVM.apply {
            title.subscribe(viewLifecycleOwner) {
                showToast<MainActivity>(it)
            }
            bearing.subscribe(viewLifecycleOwner) {
                if (it) {
                    getParentActivity<MainActivity>().apply {
                        finish()
                        overridePendingTransitionExit()
                        startActivity(intent)
                        overridePendingTransitionEnter()
                    }
                }
            }
        }
    }

    override fun onFirstLaunch(savedInstanceState: Bundle?) {
        setupLocationManager()
        setupPreferences()
        setupGpsTracker()
        setupCompass()
    }

    override fun continuousCall() {
        compass?.start()
    }

    override fun onDestroyActivities() {
        compass?.stop()
    }

    override fun setupViewListener() {

    }

    private fun setupLocationManager() {
        if (!::locationManager.isInitialized) {
            locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        }

        hasGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
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
        val parentAct = getParentActivity<MainActivity>()
        parentAct.apply {
            if (hasLocationPermission()) {
                if (hasGpsEnabled) {
                    this@QiblaFragment.getBearing()
                } else {
                    showToast("GPS is not available. Please enable it.")
                    Handler().postDelayed({
                        navigateToSetting(Navigation.LOCATION_SETTINGS)
                    }, 3_000)
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val permissions = listOf(Permission.FINE_LOCATION, Permission.COARSE_LOCATION)
                    parentAct.requestMultiplePermission(permissions, this, this)
                }
            }
        }

        compass = Compass(parentAct)
        compass?.setListener(object : Compass.CompassListener {
            override fun onNewAzimuth(azimuth: Float) {
                navigateArrowQibla(azimuth)
            }
        })
    }

    private fun navigateArrowQibla(azimuth: Float) {
        val qiblaDegree = getFloat("qiblaDegree")
        val animation = RotateAnimation(
            -(currentAzimuth) + qiblaDegree, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )

        currentAzimuth = azimuth

        animation.apply {
            duration = 500
            repeatCount = 0
            fillAfter = true
        }
        imgQibla.startAnimation(animation)
    }

    private fun getBearing() {
        val qiblaDegree = getFloat("qiblaDegree")
//        if (qiblaDegree > 0.0001) {
//
//        } else {
        fetchGps()
//        }
    }

    private fun fetchGps() {
        val result: Double
        if (gpsTracker.canGetLocation()) {
            val myLatitude: Double = gpsTracker.latitude
            val myLongitude: Double = gpsTracker.longitude

            if (myLatitude < 0.001 && myLongitude < 0.001) {
                imgQibla.notVisible()
            } else {
                val longitude2 =
                    39.826206 // ka'bah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                val longitude1: Double = myLongitude
                val latitude2 =
                    Math.toRadians(21.422487) // ka'bah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                val latitude1 = Math.toRadians(myLatitude)
                val longDiff = Math.toRadians(longitude2 - longitude1)
                val y = sin(longDiff) * cos(latitude2)
                val x =
                    cos(latitude1) * sin(latitude2) - sin(latitude1) * cos(latitude2) * cos(longDiff)
                result = (Math.toDegrees(atan2(y, x)) + 360) % 360
                val result2 = result.toFloat()
                saveFloat("qiblaDegree", result2)
                imgQibla.show()
            }
        }
    }

    private fun showMyAddress(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addressList: List<Address>? =
                geocoder.getFromLocation(latitude, longitude, 1)
            if (addressList != null && addressList.isNotEmpty()) {
                val address: Address = addressList[0]
                val addressBuilder = StringBuilder().apply {
                    append(
                        address.getAddressLine(0)
                            .substring(0, address.getAddressLine(0).indexOfFirst { it == ',' })
                    )

                    if (address.subAdminArea != null) {
                        append(", ${address.subAdminArea}")
                    }

                    if (address.adminArea != null) {
                        append(", ${address.adminArea}")
                    }

                    if (address.postalCode != null) {
                        append(", ${address.postalCode}")
                    }
                }
                logD(addressBuilder.toString())
            }
        } catch (e: IOException) {
            logE("Location Address Loader. Unable connect to Geocoder : ${e.message}")
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