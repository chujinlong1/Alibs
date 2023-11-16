package io.gitee.filtertab.listener;

import io.gitee.filtertab.bean.FilterResultBean;

import java.util.List;


public interface OnSelectResultListener {

    void onSelectResult(FilterResultBean resultBean, int postion);

    void onSelectResultList(List<FilterResultBean> resultBeans, int postion);
}
