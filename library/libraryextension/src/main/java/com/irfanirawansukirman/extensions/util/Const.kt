package com.irfanirawansukirman.extensions.util

import android.Manifest
import android.provider.Settings

object Const {
    object Code {
        const val REQUEST_CODE = 1001
    }

    object View {
        const val MESSAGE_TOAST_TYPE = 0
        const val MESSAGE_SNACK_TYPE = 1

        const val EMPTY_TOOLBAR = 0
    }

    object Navigation {
        const val LoginActivity = "featureauth.login.LoginActivity"
        const val DashboardActivity = "featuredashboard.presentation.dashboard.DashboardActivity"

        const val LOCATION_SETTINGS = Settings.ACTION_LOCATION_SOURCE_SETTINGS
    }

    object DateTime {
        const val DATE_TIME_24H_DEFAULT = "yyyy-MM-dd HH:mm:ss" // HH (24) hh (12)
        const val TIME_24H_DEFAULT = "HH:mm"
        const val TIME_24H_WITH_SECOND = "HH:mm:ss"

        const val DATE_TIME_12H_DEFAULT = "yyyy-MM-dd hh:mm:ss" // HH (24) hh (12)
        const val TIME_12H_DEFAULT = "hh:mm"
        const val TIME_12H_WITH_SECOND = "hh:mm:ss"
    }

    object KeyParam {
        const val TEST = "TEST"
    }

    object Permission {
        const val CAMERA = Manifest.permission.CAMERA
        const val CAMERA_NAME = "android.permission.CAMERA"

        const val WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        const val READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    }

    object Time {
        const val DELAY_TIME_DEFAULT = 3_000L
    }
}