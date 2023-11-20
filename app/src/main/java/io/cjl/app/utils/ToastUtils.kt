package io.cjl.app.utils

import android.app.Application
import android.view.Gravity
import com.hjq.toast.Toaster

object ToastUtils {

    fun init(application: Application) {
        Toaster.init(application)
        Toaster.setGravity(Gravity.CENTER)
    }

    fun show(message: String?) {
        message?.let {
            Toaster.show(it)
        }
    }
}