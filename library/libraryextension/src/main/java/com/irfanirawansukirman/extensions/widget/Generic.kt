package com.irfanirawansukirman.extensions.widget

fun Any?.isNull() = this == null

fun Any?.isNotNull() = !isNull()

fun Int?.orZero(): Int = this ?: 0

fun Long?.orZero(): Long = this ?: 0L

fun <T> T?.orDefault(default: T): T {
    return this ?: default
}
