package io.cjl.adialog

import android.view.View

interface OnDialogButtonClickListener {
    fun onClick(dialog: BaseDialog , v : View) : Boolean
}