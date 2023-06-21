package io.cjl.dialog.`interface`

import BaseDialog
import android.view.View

interface OnBindView {
    fun onBind(dialog: BaseDialog? ,view : View)
}