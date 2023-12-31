package io.cjl.filtertab.listener;

import io.cjl.filtertab.bean.FilterResultBean;

import java.util.List;


public interface OnFilterToViewListener {

    /**
     * 筛选监听
     *
     * @param resultBean
     */
    void onFilterToView(FilterResultBean resultBean, int postion);

    /**
     * 筛选集合监听
     *
     * @param resultBean
     */
    void onFilterListToView(List<FilterResultBean> resultBean, int postion);

}
