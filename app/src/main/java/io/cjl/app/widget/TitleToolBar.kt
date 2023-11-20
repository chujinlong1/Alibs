package io.cjl.app.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.navigation.findNavController
import io.cjl.app.R
import io.cjl.app.databinding.ViewTitleToolBarBinding

class TitleToolBar@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: ViewTitleToolBarBinding = ViewTitleToolBarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.title_tool_bar, defStyleAttr, 0)
        val title = arr.getString(R.styleable.title_tool_bar_title)
        //默认是显示左边的退出箭头
        val showLeft = arr.getBoolean(R.styleable.title_tool_bar_showLeft, true)
        val showRight = arr.getBoolean(R.styleable.title_tool_bar_showRight, true)
        val leftIcon = arr.getDrawable(R.styleable.title_tool_bar_leftIcon)
        val rightIcon = arr.getDrawable(R.styleable.title_tool_bar_rightIcon)
        val leftText = arr.getString(R.styleable.title_tool_bar_leftText)
        val rightText = arr.getString(R.styleable.title_tool_bar_rightText)

        if (!showLeft) {
            binding.ivLeft.visibility = GONE
        } else {
            if (leftIcon != null) {
                binding.ivLeft.setImageDrawable(leftIcon)
            } else {
                if (TextUtils.isEmpty(leftText)) {
                } else {
                    binding.ivLeft.visibility = GONE
                    binding.tvLeft.visibility = VISIBLE
                    binding.tvLeft.text = leftText
                }
            }
            binding.ivLeft.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        if (!showRight) {
            binding.linRight.visibility = GONE
            binding.tvRight.visibility = GONE
        } else {
            if (rightIcon != null) {
                binding.ivRight.setImageDrawable(rightIcon)
            } else {
                if (!TextUtils.isEmpty(rightText)) {
                    binding.tvRight.visibility = View.VISIBLE
                    binding.linRight.visibility = View.GONE
                    binding.tvRight.text = rightText
                }
            }
        }
        binding.tvTitle.text = title
        arr.recycle()
    }
    fun showLeftButton(visibility: Int) {
        binding.ivLeft.visibility = visibility
    }

    fun setLeftClickListener(listener: OnClickListener) {
        binding.ivLeft.setOnClickListener(listener)
        if (binding.tvLeft.visibility == VISIBLE) {
            binding.tvLeft.setOnClickListener(listener)
        }
    }

    fun setRightClickListener(listener: OnClickListener) {
        binding.ivRight.setOnClickListener(listener)
        if (binding.tvRight.visibility == View.VISIBLE) {
            binding.tvRight.setOnClickListener(listener)
        }
    }

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    fun setRightLayoutView(view: View) {
        binding.tvRight.visibility = View.GONE
        binding.linRight.visibility = View.VISIBLE
        binding.linRight.addView(view)
    }

    fun setTranslate() {
        binding.linTitleBg.setBackgroundColor(0x00000000)
        binding.ivLeft.visibility = View.GONE
    }
}