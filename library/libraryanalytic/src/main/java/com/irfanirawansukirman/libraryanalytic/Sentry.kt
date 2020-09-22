package com.irfanirawansukirman.libraryanalytic

import io.sentry.core.Breadcrumb
import io.sentry.core.Sentry
import io.sentry.core.SentryLevel
import io.sentry.core.protocol.User

// source: https://blog.sentry.io/2019/12/10/new-android-sdk-how-to
object Sentry {
    fun setUserTrack(user: User) {
        Sentry.setUser(user)
    }

    fun createForceClose() {
        throw RuntimeException("Error test!")
    }

    fun createBreadcrumb(message: String) {
        Sentry.addBreadcrumb(message)
    }

    fun createBreadcrumb(breadcrumb: Breadcrumb) {
        Sentry.addBreadcrumb(breadcrumb)
    }

    fun captureMessageDebug(message: String) {
        Sentry.captureMessage(message, SentryLevel.DEBUG)
    }

    fun captureMessageError(message: String) {
        Sentry.captureMessage(message, SentryLevel.ERROR)
    }

    fun sendErrorMessage(e: Exception) {
        Sentry.captureException(e)
    }
}