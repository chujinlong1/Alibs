package io.cjl.adialog

import android.view.View

interface OnBindView {
    fun onBind(dialog: BaseDialog? ,view : View)
}