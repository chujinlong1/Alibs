package io.cjl.filtertab.popupwindow;

import static android.view.View.MeasureSpec.AT_MOST;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.cjl.filtertab.adapter.PopupSingleAdapter;
import io.cjl.filtertab.base.BaseFilterBean;
import io.cjl.filtertab.base.BasePopupWindow;
import io.cjl.filtertab.bean.FilterResultBean;
import io.cjl.filtertab.R;
import io.cjl.filtertab.listener.OnFilterToViewListener;
import io.cjl.filtertab.util.Utils;

import java.util.List;

/**
 * 竖直单选样式
 */
public class SingleSelectPopupWindow extends BasePopupWindow {
    private int mTabPostion;
    private RecyclerView rv_content;
    private PopupSingleAdapter mAdapter;

    public SingleSelectPopupWindow(Context context, List<BaseFilterBean> data, int filterType, int position, OnFilterToViewListener onFilterToViewListener) {
        super(context, data, filterType, position, onFilterToViewListener);
        mTabPostion = position;
    }

    @Override
    public View initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.popup_single_select, null, false);
        rv_content = rootView.findViewById(R.id.rv_content);
        mAdapter = new PopupSingleAdapter(getContext(), getData());
        final int maxHeight = Utils.dp2px(getContext(), 273);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public void setMeasuredDimension(Rect childrenBounds, int wSpec, int hSpec) {
                super.setMeasuredDimension(childrenBounds, wSpec, View.MeasureSpec.makeMeasureSpec(maxHeight, AT_MOST));
            }
        };
        rv_content.setLayoutManager(linearLayoutManager);
        rv_content.setAdapter(mAdapter);

        View v_outside = rootView.findViewById(R.id.v_outside);
        v_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        return rootView;
    }

    @Override
    public void initSelectData() {
        mAdapter.setOnItemClickListener(new PopupSingleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int itemId = getData().get(position).getId();
                String itemName = getData().get(position).getItemName();
                FilterResultBean resultBean = new FilterResultBean();
                resultBean.setPopupIndex(getPosition());
                resultBean.setPopupType(getFilterType());
                resultBean.setItemId(itemId);
                resultBean.setName(itemName);
                getOnFilterToViewListener().onFilterToView(resultBean, mTabPostion);
                dismiss();
            }
        });
    }

    @Override
    public void refreshData() {

    }
}
