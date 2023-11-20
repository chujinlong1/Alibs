package io.cjl.app.ui

import android.annotation.SuppressLint
import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import android.provider.Settings
import io.cjl.app.utils.ToastUtils

@HiltAndroidApp
class AppContext : Application(){

    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }
}