package io.cjl.app.utils

import androidx.appcompat.app.AppCompatActivity
import io.cjl.adialog.LoadDialog

object LoadingUtil {

    fun showLoading(context: AppCompatActivity, message: String = "正在加载数据...") {
        LoadDialog.show(context, message)
    }

    fun dismiss() {
        LoadDialog.dismiss()
    }
}