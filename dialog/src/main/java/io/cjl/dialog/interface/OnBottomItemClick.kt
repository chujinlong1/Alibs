package io.cjl.dialog.`interface`

import BaseDialog

interface OnBottomItemClick {
    fun onItemClick(dialog: BaseDialog , position : Int) : Boolean
}