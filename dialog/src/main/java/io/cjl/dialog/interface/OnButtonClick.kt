package io.cjl.dialog.`interface`

import BaseDialog
import android.view.View

interface OnButtonClick {
    fun onClickLift(dialog: BaseDialog , v : View) : Boolean
    fun onClickRight(dialog: BaseDialog ,v : View) : Boolean
}