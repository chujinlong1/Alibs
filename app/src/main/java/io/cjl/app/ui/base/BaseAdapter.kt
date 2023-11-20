package io.cjl.app.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class VBViewHolder<VB : ViewBinding>(val vb: VB, view: View) : BaseViewHolder(view)
abstract class BaseAdapter<T, VB : ViewBinding>(data: MutableList<T>? = null) : BaseQuickAdapter<T, VBViewHolder<VB>>(0, data) {

    abstract fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): VB

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): VBViewHolder<VB> {
        val viewBinding = createViewBinding(LayoutInflater.from(parent.context), parent)
        return VBViewHolder(viewBinding, viewBinding.root)
    }
}