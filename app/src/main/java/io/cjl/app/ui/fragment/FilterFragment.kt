package io.cjl.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.cjl.app.core.data.FilterAreaOneEntity
import io.cjl.app.core.data.FilterAreaTwoEntity
import io.cjl.app.core.data.FilterSelectedEntity
import io.cjl.app.databinding.FragmentFilterBinding
import io.cjl.app.ui.base.BaseFragment
import io.cjl.app.utils.ToastUtils
import io.cjl.filtertab.FilterTabConfig
import io.cjl.filtertab.base.BaseFilterBean
import io.cjl.filtertab.bean.FilterInfoBean
import io.cjl.filtertab.bean.FilterResultBean
import io.cjl.filtertab.listener.OnSelectResultListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class FilterFragment: BaseFragment(), OnSelectResultListener {

    private val logger: Logger = LoggerFactory.getLogger(FilterFragment::class.java)

    private var mAreaList = mutableListOf<BaseFilterBean<BaseFilterBean<*>>>()

    private var mAllPriceList = mutableListOf<BaseFilterBean<BaseFilterBean<*>>>()

    private var mSortList = mutableListOf<BaseFilterBean<BaseFilterBean<*>>>()

    private var mStatusList = mutableListOf<BaseFilterBean<BaseFilterBean<*>>>()

    private var mMoreList = mutableListOf<BaseFilterBean<BaseFilterBean<*>>>()

    private var mSinglePriceList = mutableListOf<BaseFilterBean<BaseFilterBean<*>>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentFilterBinding.inflate(inflater)
        binding.ftbFilter.removeViews()

        initData()

        val state = FilterInfoBean("状态", FilterTabConfig.FILTER_TYPE_SINGLE_GIRD, mStatusList)
        val test = FilterInfoBean("测试", FilterTabConfig.FILTER_TYPE_SINGLE_GIRD, mStatusList)
        val aaa = FilterInfoBean("测试1", FilterTabConfig.FILTER_TYPE_SINGLE_GIRD, mStatusList)

        binding.ftbFilter.addFilterItem(state.tabName, mStatusList, state.popupType, 0,false)
        binding.ftbFilter.addFilterItem(test.tabName, mStatusList, test.popupType, 1,false)
        binding.ftbFilter.addFilterItem(aaa.tabName, mStatusList, aaa.popupType, 2,false)

        binding.ftbFilter.setOnSelectResultListener(this)
        return binding.root
    }

    private fun initData() {
        setState()
    }

    private fun setState(){
        val a = FilterSelectedEntity()
        a.tid = -1
        a.name = "全部"
        a.selected = 1

        val b = FilterSelectedEntity()
        b.tid = 1
        b.name = "测试"
        b.selected = 0

        val c = FilterSelectedEntity()
        c.tid = 2
        c.name = "测试1"
        c.selected = 0

        mStatusList.add(a)
        mStatusList.add(b)
        mStatusList.add(c)
    }

    override fun onSelectResult(resultBean: FilterResultBean?, postion: Int) {
        logger.info("{}",resultBean)
        logger.info("{}",postion)
    }

    override fun onSelectResultList(resultBeans: MutableList<FilterResultBean>?, postion: Int) {
        logger.info("{}",resultBeans)
        logger.info("{}",postion)
        var message = ""
        val list: List<FilterResultBean> = resultBeans!!
        for (i in list.indices) {
            val bean = list[i]
            message = if (i == list.size - 1) {
                message + bean.name
            } else {
                message + bean.name + ","
            }
        }
        ToastUtils.show(message)
    }
}