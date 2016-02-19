package kale.adapter.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

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
    
    private T mWrappedAdapter;

    protected View mHeaderView = null;

    protected View mFooterView = null;
    
    public ExRcvAdapterWrapper(@NonNull T adapter, RecyclerView.LayoutManager layoutManager) {
        mWrappedAdapter = adapter;
        mWrappedAdapter.registerAdapterDataObserver(getObserver());
        mLayoutManager = layoutManager;
        
        if (mLayoutManager instanceof GridLayoutManager) {
            setSpanSizeLookup(); // 设置头部和尾部都是跨列的
        }
    }

    @Override
    public int getItemCount() {
        int headerOrFooter = 0;
        if (mHeaderView != null) {
            headerOrFooter++;
        }
        if (mFooterView != null) {
            headerOrFooter++;
        }
        return headerOrFooter + mWrappedAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        } else if (mFooterView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return mWrappedAdapter.getItemViewType(position - getHeaderCount());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new SimpleViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER) {
            return new SimpleViewHolder(mFooterView);
        } else {
            return mWrappedAdapter.onCreateViewHolder(parent, viewType);
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
            mWrappedAdapter.onBindViewHolder(viewHolder, position - getHeaderCount());
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 添加/移除头部、尾部的操作
    ///////////////////////////////////////////////////////////////////////////

    public void setHeaderView(@NonNull View headerView) {
        mHeaderView = headerView;
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            setFulSpanLayoutParams(mHeaderView);
        }
        notifyDataSetChanged();
    }

    public void setFooterView(@NonNull View footerView) {
        mFooterView = footerView;
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            setFulSpanLayoutParams(mFooterView);
        }
        notifyDataSetChanged();
    }
    
    public View getHeaderView() {
        return mHeaderView;
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void removeHeaderView() {
        mHeaderView = null;
        // notifyItemRemoved(0);如果这里需要做头部的删除动画，
        // 可以复写这个方法，然后进行改写
        notifyDataSetChanged();
    }

    public void removeFooterView() {
        mFooterView = null;
        notifyItemRemoved(getItemCount());
        // 这里因为删除尾部不会影响到前面的pos的改变，所以不用刷新了。
    }

    public T getWrappedAdapter() {
        return mWrappedAdapter;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 数据更新后的操作
    ///////////////////////////////////////////////////////////////////////////
    
    @NonNull
    private RecyclerView.AdapterDataObserver getObserver() {
        return new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                notifyItemRangeChanged(positionStart + getHeaderCount(), itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int count = getHeaderCount();
                notifyItemRangeInserted(positionStart + count, itemCount);
                notifyItemRangeChanged(positionStart + count + itemCount - 1, getItemCount() - itemCount - positionStart - getFooterCount());
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                int count = getHeaderCount();
                notifyItemRangeRemoved(count + positionStart, itemCount);
                notifyItemRangeChanged(count + positionStart, getItemCount() - count - positionStart - getFooterCount());
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                // TODO: 2015/11/23 还没支持转移的操作 
            }
        };
    }

    public int getHeaderCount() {
        return mHeaderView != null ? 1 : 0;
    }

    public int getFooterCount() {
        return mFooterView != null ? 1 : 0;
    }

    
    ///////////////////////////////////////////////////////////////////////////
    // 设置头和尾部跨列
    ///////////////////////////////////////////////////////////////////////////

    public static void setFulSpanLayoutParams(View view) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setFullSpan(true);
        view.setLayoutParams(layoutParams);
    }

    private void setSpanSizeLookup() {
        final GridLayoutManager gridLayoutManager = ((GridLayoutManager) mLayoutManager);
        final GridLayoutManager.SpanSizeLookup lookup = gridLayoutManager.getSpanSizeLookup();
        final int spanCount = gridLayoutManager.getSpanCount();

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                final int type = getItemViewType(position);
                if (type == TYPE_HEADER || type == TYPE_FOOTER) {
                    return spanCount;
                } else {
                    return lookup.getSpanSize(position - getHeaderCount());
                }
            }
        });
    }


    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    private static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            itemView.setLayoutParams(params);
        }
    }

}
