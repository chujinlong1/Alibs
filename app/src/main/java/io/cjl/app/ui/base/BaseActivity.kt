package io.cjl.app.ui.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        translucent()

        initView()

        initData()
    }
    private fun translucent() {
        val decorView = window.decorView
        window.statusBarColor = Color.TRANSPARENT
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        decorView.setOnApplyWindowInsetsListener { v, insets ->
            val defaultInsets = v.onApplyWindowInsets(insets)
            defaultInsets.replaceSystemWindowInsets(
                defaultInsets.systemWindowInsetLeft,
                0,
                defaultInsets.systemWindowInsetRight,
                defaultInsets.systemWindowInsetBottom
            )
        }
        ViewCompat.requestApplyInsets(decorView)
    }

    private var pendingCollapseKeyword = false
    private var focusedView: View? = null

    // 点击非输入框位置优先隐藏软键盘
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            pendingCollapseKeyword = isShouldHideInput(ev)
            if (pendingCollapseKeyword) focusedView = currentFocus
        } else if (ev.action == MotionEvent.ACTION_UP) {
            if (pendingCollapseKeyword) {
                hideInputMethod(this)
                focusedView!!.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideInput(event: MotionEvent): Boolean {
        val v = currentFocus
        if (v is EditText) {
            val location = intArrayOf(0, 0)
            v.getLocationInWindow(location)
            return event.x < location[0] || event.x > location[0] + v.getWidth() || event.y < location[1] || event.y > location[1] + v.getHeight()
        }
        return false
    }

    // 抬起手指时如果焦点还在原来的EditText则收起键盘
    private fun hideInputMethod(context: Context) {
        val v = currentFocus
        if (v === focusedView) {
            focusedView?.clearFocus()
            val imm = context
                .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(focusedView?.windowToken, 0)
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initData()

}