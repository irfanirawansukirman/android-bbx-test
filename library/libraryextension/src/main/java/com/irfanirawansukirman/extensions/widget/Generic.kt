package com.irfanirawansukirman.extensions.widget

import android.content.res.Resources

fun Any?.isNull() = this == null

fun Any?.isNotNull() = !isNull()

fun Int?.orZero(): Int = this ?: 0

fun Long?.orZero(): Long = this ?: 0L

fun <T> T?.orDefault(default: T): T {
    return this ?: default
}

fun Int?.toDp() = (this?.div(Resources.getSystem().displayMetrics.density))?.toInt() ?: 0

fun Int?.toPx() = (this?.times((Resources.getSystem().displayMetrics.density)))?.toInt() ?: 0
