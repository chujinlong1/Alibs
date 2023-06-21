package io.cjl.dialog.`interface`

import BaseDialog
import android.view.View

interface OnDialogButtonClickListener {
    fun onClick(dialog: BaseDialog , v : View) : Boolean
}