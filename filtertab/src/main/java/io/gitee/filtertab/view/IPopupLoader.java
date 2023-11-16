package io.gitee.filtertab.view;

import android.content.Context;
import android.widget.PopupWindow;

import io.gitee.filtertab.listener.OnFilterToViewListener;

import java.util.List;


public interface IPopupLoader {

    PopupWindow getPopupEntity(Context context, List data, int filterType, int position, OnFilterToViewListener onFilterToViewListener, FilterTabView view);
}
