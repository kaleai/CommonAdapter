package kale.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import lombok.Getter;

/**
 * @author Jack Tony
 * @date 2015/6/2
 */
public class RcvAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * view的基本类型，这里只有头/底部/普通，在子类中可以扩展
     */
    public static final int TYPE_HEADER = 99930;

    public static final int TYPE_FOOTER = 99931;

    @Getter
    private final RecyclerView.LayoutManager layoutManager;

    private RecyclerView.Adapter mWrapped;

    @Getter
    private View headerView = null;

    @Getter
    private View footerView = null;

    public RcvAdapterWrapper(@NonNull RecyclerView.Adapter adapter, @NonNull RecyclerView.LayoutManager layoutManager) {
        mWrapped = adapter;
        mWrapped.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart + getHeaderCount(), itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart + getHeaderCount(), itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart + getHeaderCount(), itemCount);
                if (getFooterCount() != 0) {
                    if (positionStart + getFooterCount() + 1 == getItemCount()) { // last one
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                // TODO: 2015/11/23 还没支持"多个"item的转移的操作 
                notifyItemMoved(fromPosition + getHeaderCount(), getHeaderCount() + toPosition);
            }
        });
        this.layoutManager = layoutManager;

        if (this.layoutManager instanceof GridLayoutManager) {
            setSpanSizeLookup(this, (GridLayoutManager) this.layoutManager); // 设置头部和尾部都是跨列的
        }
    }

    /**
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        int offset = 0;
        if (headerView != null) {
            offset++;
        }
        if (footerView != null) {
            offset++;
        }
        return offset + mWrapped.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView != null && position == 0) {
            return TYPE_HEADER;
        } else if (footerView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return mWrapped.getItemViewType(position - getHeaderCount());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new SimpleViewHolder(headerView);
        } else if (viewType == TYPE_FOOTER) {
            return new SimpleViewHolder(footerView);
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
        this.headerView = headerView;
        setFullSpan(headerView, layoutManager);
    }

    public void setFooterView(@NonNull View footerView) {
        this.footerView = footerView;
        setFullSpan(footerView, layoutManager);
    }

    private void setFullSpan(View view, RecyclerView.LayoutManager layoutManager) {
        final int itemHeight = view.getLayoutParams() != null ?
                view.getLayoutParams().height : ViewGroup.LayoutParams.WRAP_CONTENT;

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
        } else if (layoutManager instanceof GridLayoutManager) {
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));

        }
        notifyDataSetChanged();
    }

    /**
     * notifyItemRemoved(0);如果这里需要做头部的删除动画，
     */
    public void removeHeaderView() {
        headerView = null;
        notifyDataSetChanged();
    }

    /**
     * 这里因为删除尾部不会影响到前面的pos的改变，所以不用刷新
     */
    public void removeFooterView() {
        footerView = null;
        int footerPos = getItemCount();
        notifyItemRemoved(footerPos);
    }

    public RecyclerView.Adapter getWrappedAdapter() {
        return mWrapped;
    }

    public int getHeaderCount() {
        return headerView != null ? 1 : 0;
    }

    public int getFooterCount() {
        return footerView != null ? 1 : 0;
    }

    /**
     * 设置头和底部的跨列
     */
    static void setSpanSizeLookup(final RecyclerView.Adapter adapter, final GridLayoutManager layoutManager) {
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                final int type = adapter.getItemViewType(position);
                if (type == RcvAdapterWrapper.TYPE_HEADER || type == RcvAdapterWrapper.TYPE_FOOTER) {
                    // 如果是头部和底部，那么就横跨
                    return layoutManager.getSpanCount();
                } else {
                    // 如果是普通的，那么就保持原样
                    //return layoutManager.getSpanSizeLookup().getSpanSize(position - adapter.getHeaderCount());
                    return 1;
                }
            }
        });
    }

    /**
     * Keep it simple!
     */
    private static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

}
