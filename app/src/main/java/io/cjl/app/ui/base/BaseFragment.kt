package io.cjl.app.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.cjl.app.core.entity.Status
import io.cjl.app.utils.LoadingUtil

open class BaseFragment : Fragment(){

    fun showLoading(status: Status) {
        if (status == Status.LOADING) {
            LoadingUtil.showLoading(activity as AppCompatActivity)
        } else {
            LoadingUtil.dismiss()
        }
    }
}