package kale.adapter.util.rcv;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import kale.adapter.ExRcvAdapterWrapper;

/**
 * @author Kale
 * @date 2016/3/16
 * 
 * 设置头和底部的跨列
 */
public class LayoutParamsSpan {

    public static void setFulSpanLayoutParams(View view) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setFullSpan(true);
        view.setLayoutParams(layoutParams);
    }
    
    public static void setSpanSizeLookup(final ExRcvAdapterWrapper adapter, final GridLayoutManager layoutManager) {
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                final int type = adapter.getItemViewType(position);
                if (type == ExRcvAdapterWrapper.TYPE_HEADER || type == ExRcvAdapterWrapper.TYPE_FOOTER) {
                    // 如果是头部和底部，那么就横跨
                    return layoutManager.getSpanCount();
                } else {
                    // 如果是普通的，那么就保持原样
                    return layoutManager.getSpanSizeLookup().getSpanSize(position - adapter.getHeaderCount());
                }
            }
        });
    }
}
