package kale.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

    public static final int TYPE_EMPTY = 99932;

    @Getter
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView.Adapter mWrapped;

    private boolean hasShownEmptyView = false;

    private RecyclerView emptyViewParent;

    @Nullable
    @Getter
    private View headerView = null;

    @Nullable
    @Getter
    private View footerView = null;

    @Nullable
    @Getter
    private View emptyView = null;


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
                if (hasShownEmptyView && getItemCount() != 0) {
                    notifyItemRemoved(getHeaderCount());
                    hasShownEmptyView = false;
                }
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart + getHeaderCount(), itemCount);
                /*if (getFooterCount() != 0) {
                    if (positionStart + getFooterCount() + 1 == getItemCount()) { // last one
                        notifyDataSetChanged();
                    }
                }*/
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                // FIXME: 2015/11/23 还没支持"多个"item的转移的操作 
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
        int count = mWrapped.getItemCount();

        int offset = 0;
        if (headerView != null) {
            offset++;
        }
        if (footerView != null) {
            offset++;
        }
        if (emptyView != null) {
            if (count == 0) {
                offset++;
                hasShownEmptyView = true;
            } else {
                hasShownEmptyView = false;
            }
        }
        return offset + count;
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView != null && position == 0) {
            return TYPE_HEADER;
        } else if (footerView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else if (emptyView != null && mWrapped.getItemCount() == 0 && position == getHeaderCount()) {
            return TYPE_EMPTY;
        } else {
            return mWrapped.getItemViewType(position - getHeaderCount());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new SimpleViewHolder(headerView);
            case TYPE_FOOTER:
                return new SimpleViewHolder(footerView);
            case TYPE_EMPTY:
                return new SimpleViewHolder(emptyView);
            default:
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
        if (type != TYPE_HEADER && type != TYPE_FOOTER && type != TYPE_EMPTY) {
            mWrapped.onBindViewHolder(viewHolder, position - getHeaderCount());
        }

        if (type == TYPE_EMPTY && emptyViewParent != null) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewHolder.itemView.getLayoutParams();
            int headerHeight = headerView != null ? headerView.getHeight() : 0;
            params.height = emptyViewParent.getHeight() - headerHeight;
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (this.layoutManager == layoutManager) {
            return;
        }

        this.layoutManager = layoutManager;
        if (this.layoutManager instanceof GridLayoutManager) {
            setSpanSizeLookup(this, (GridLayoutManager) this.layoutManager); // 设置头部和尾部都是跨列的
        }
        setFullSpan(headerView, layoutManager);
        setFullSpan(footerView, layoutManager);
        setFullSpan(emptyView, layoutManager);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 添加/移除头部、尾部的操作
    ///////////////////////////////////////////////////////////////////////////

    public void setHeaderView(@Nullable View headerView) {
        if (this.headerView == headerView) {
            return;
        }
        this.headerView = headerView;
        setFullSpan(headerView, layoutManager);
    }

    public void setFooterView(@Nullable View footerView) {
        if (this.footerView == footerView) {
            return;
        }
        this.footerView = footerView;
        setFullSpan(footerView, layoutManager);
    }

    /**
     * 设置空状态的view
     *
     * @param emptyViewParent 如果需要EmptyView的高度占满RecyclerView，则此参数必填；
     *                        传null，则保持EmptyView的自有高度
     */
    public void setEmptyView(@Nullable View emptyView, @Nullable RecyclerView emptyViewParent) {
        if (this.emptyView == emptyView) {
            return;
        }
        this.emptyView = emptyView;
        this.emptyViewParent = emptyViewParent;
        setFullSpan(emptyView, layoutManager);
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

    private void setFullSpan(@Nullable View view, RecyclerView.LayoutManager layoutManager) {
        if (view != null) {
            final int itemHeight = view.getLayoutParams() != null ?
                    view.getLayoutParams().height : RecyclerView.LayoutParams.WRAP_CONTENT;

            if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager.LayoutParams layoutParams =
                        new StaggeredGridLayoutManager.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
            } else if (layoutManager instanceof GridLayoutManager
                    || layoutManager instanceof LinearLayoutManager) {
                view.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 设置头和底部的跨列
     */
    private void setSpanSizeLookup(final RecyclerView.Adapter adapter, final GridLayoutManager layoutManager) {
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                final int type = adapter.getItemViewType(position);
                if (type == TYPE_HEADER || type == TYPE_FOOTER || type == TYPE_EMPTY) {
                    return layoutManager.getSpanCount();
                } else {
                    // 如果是普通的，那么就保持原样
                    //return layoutManager.getSpanSizeLookup().getSpanSize(position - adapter.getHeaderCount());
                    return 1;
                }
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (!(holder instanceof SimpleViewHolder)) {
            mWrapped.onViewAttachedToWindow(holder);
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (!(holder instanceof SimpleViewHolder)) {
            mWrapped.onViewDetachedFromWindow(holder);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mWrapped.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mWrapped.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (!(holder instanceof SimpleViewHolder)) {
            mWrapped.onViewRecycled(holder);
        }
    }

    /**
     * Keep it simple!
     */
    private static class SimpleViewHolder extends RecyclerView.ViewHolder {

        SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

}
