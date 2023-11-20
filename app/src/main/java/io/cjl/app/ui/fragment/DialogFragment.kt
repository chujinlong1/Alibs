package io.cjl.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.cjl.adialog.*
import io.cjl.app.databinding.FragmentDialogBinding
import io.cjl.app.ui.base.BaseFragment
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DialogFragment: BaseFragment() {

    private val logger: Logger = LoggerFactory.getLogger(DialogFragment::class.java)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDialogBinding.inflate(inflater)

        binding.btnAlter.setOnClickListener {
            AlertDialog.show(requireContext() as AppCompatActivity, "提示", "我是演示", "测试")
        }
        binding.btnAlter1.setOnClickListener {
            AlertDialog.show(requireContext() as AppCompatActivity, "提示", "我是演示", "按钮","按钮")
        }
        binding.btnAlterVertical.setOnClickListener {
            AlertVerticalDialog.show(requireContext() as AppCompatActivity,arrayListOf("选项1","选项2","选项3"), object : OnBottomItemClick{
                override fun onItemClick(dialog: BaseDialog, position: Int): Boolean {
                    return true
                }
            })
        }
        binding.btnAlterVertical1.setOnClickListener {
            AlertVerticalDialog.show(requireContext() as AppCompatActivity,"提示", "我是内容", arrayListOf("选项1","选项2","选项3"), object : OnBottomItemClick{
                override fun onItemClick(dialog: BaseDialog, position: Int): Boolean {
                    return true
                }
            },false)
        }
        binding.btnBottom.setOnClickListener {
            BottomDialog.show(requireContext() as AppCompatActivity,arrayListOf("选项1","选项2","选项3"), object : OnBottomItemClick{
                override fun onItemClick(dialog: BaseDialog, position: Int): Boolean {
                    return true
                }
            })
        }
        binding.btnBottom1.setOnClickListener {
            BottomDialog.show(requireContext() as AppCompatActivity,"请选择",arrayListOf("选项1","选项2","选项3"), object : OnBottomItemClick{
                override fun onItemClick(dialog: BaseDialog, position: Int): Boolean {
                    return true
                }
            },false)
        }


        binding.btnCustom.setOnClickListener {
            CustomDialog.show(requireContext() as AppCompatActivity, io.cjl.app.R.layout.custom_dialog,
                object:OnBindView{
                    override fun onBind(dialog: BaseDialog?, view: View) {
                        logger.info("view:{}",view)
                        view.findViewById<TextView>(io.cjl.app.R.id.title_name).text = "我是标题"
                        view.findViewById<TextView>(io.cjl.app.R.id.btn_ok).setOnClickListener {
                            dialog?.dismiss()
                        }
                    }
                },false)
        }
        return binding.root
    }

}