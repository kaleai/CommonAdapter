package kale.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import kale.adapter.util.rcv.LayoutParamsSpan;
import kale.adapter.util.rcv.RcvAdapterDataObserver;

/**
 * @author Jack Tony
 * @date 2015/6/2
 */
public class ExRcvAdapterWrapper<T extends RecyclerView.Adapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * view的基本类型，这里只有头/底部/普通，在子类中可以扩展
     */
    public static final int TYPE_HEADER = 99930;

    public static final int TYPE_FOOTER = 99931;

    private final RecyclerView.LayoutManager mLayoutManager;

    private T mWrapped;

    protected View mHeaderView = null;

    protected View mFooterView = null;

    public ExRcvAdapterWrapper(@NonNull T adapter, @NonNull RecyclerView.LayoutManager layoutManager) {
        mWrapped = adapter;
        mWrapped.registerAdapterDataObserver(new RcvAdapterDataObserver(this));
        mLayoutManager = layoutManager;

        if (mLayoutManager instanceof GridLayoutManager) {
            LayoutParamsSpan.setSpanSizeLookup(this, (GridLayoutManager) mLayoutManager); // 设置头部和尾部都是跨列的
        }
    }

    @Override
    public int getItemCount() {
        int offset = 0;
        if (mHeaderView != null) {
            offset++;
        }
        if (mFooterView != null) {
            offset++;
        }
        return offset + mWrapped.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        } else if (mFooterView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return mWrapped.getItemViewType(position - getHeaderCount());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new SimpleViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER) {
            return new SimpleViewHolder(mFooterView);
        } else {
            return mWrapped.onCreateViewHolder(parent, viewType);
        }
    }

    /**
     * 载入ViewHolder，这里仅仅处理header和footer视图的逻辑
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        final int type = getItemViewType(position);
        if (type != TYPE_HEADER && type != TYPE_FOOTER) {
            mWrapped.onBindViewHolder(viewHolder, position - getHeaderCount());
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 添加/移除头部、尾部的操作
    ///////////////////////////////////////////////////////////////////////////

    public void setHeaderView(@NonNull View headerView) {
        mHeaderView = headerView;
        setSpanView(mHeaderView);
    }

    public void setFooterView(@NonNull View footerView) {
        mFooterView = footerView;
        setSpanView(mFooterView);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public View getFooterView() {
        return mFooterView;
    }

    private void setSpanView(View footerView) {
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            LayoutParamsSpan.setFulSpanLayoutParams(footerView);
        }
        notifyDataSetChanged();
    }

    /**
     * notifyItemRemoved(0);如果这里需要做头部的删除动画，
     */
    public void removeHeaderView() {
        mHeaderView = null;
        notifyDataSetChanged();
    }

    /**
     * 这里因为删除尾部不会影响到前面的pos的改变，所以不用刷新
     */
    public void removeFooterView() {
        mFooterView = null;
        int footerPos = getItemCount();
        notifyItemRemoved(footerPos);
    }

    public T getWrappedAdapter() {
        return mWrapped;
    }

    public int getHeaderCount() {
        return mHeaderView != null ? 1 : 0;
    }

    public int getFooterCount() {
        return mFooterView != null ? 1 : 0;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    private static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
        }
    }

}
