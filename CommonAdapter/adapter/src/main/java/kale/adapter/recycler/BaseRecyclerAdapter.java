package kale.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * @author Jack Tony
 * @brief recycleView的基础适配器，处理了添加头和底的逻辑
 * @date 2015/4/10
 */
abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final String TAG = getClass().getSimpleName();

    protected View customHeaderView = null;

    protected View customFooterView = null;

    protected AdapterView.OnItemClickListener mOnItemClickListener;

    protected AdapterView.OnItemLongClickListener mOnItemLongClickListener;

    /**
     * view的基本类型，这里只有头/底部/普通，在子类中可以扩展
     */
    class VIEW_TYPES {

        public static final int HEADER = 7;

        public static final int FOOTER = 8;

        public static final int NORMAL = 9;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPES.HEADER && customHeaderView != null) {
            return new SimpleViewHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.FOOTER && customFooterView != null) {
            return new SimpleViewHolder(customFooterView);
        }
        return onCreateItemViewHolder(parent.getContext(), viewType);
    }

    /**
     * 得到一个view holder对象
     *
     * @param viewType 视图类型
     */
    protected abstract RecyclerView.ViewHolder onCreateItemViewHolder(Context context, int viewType);

    /**
     * 返回adapter中总共的item数目，包括头部和底部
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        int headerOrFooter = 0;
        if (customHeaderView != null) {
            headerOrFooter++;
        }
        if (customFooterView != null) {
            headerOrFooter++;
        }
        return getAdapterItemCount() + headerOrFooter;
    }

    /**
     * 返回adapter中不包括头和底view的item数目
     *
     * @return The number of items in the bound adapter
     */
    public abstract int getAdapterItemCount();


    @Override
    public int getItemViewType(int position) {
        if (customFooterView != null && position == getItemCount() - 1) {
            return VIEW_TYPES.FOOTER;
        } else if (customHeaderView != null && position == 0) {
            return VIEW_TYPES.HEADER;
        } else {
            if (customHeaderView != null) {
                return getAdapterItemType(position - 1);
            }
            return getAdapterItemType(position);
        }
    }

    /**
     * 根据位置得到view的类型
     */
    public abstract int getAdapterItemType(int position);

    /**
     * 载入ViewHolder，这里仅仅处理header和footer视图的逻辑
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if ((customHeaderView != null && position == 0) || (customFooterView != null && position == getItemCount() - 1)) {
            // 如果是header或者是footer则不处理
        } else {
            if (customHeaderView != null) {
                position--;
            }
            onBindViewHolder(viewHolder, position, true);
            final int pos = position;
            // 设置点击事件  
            if (mOnItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(null, viewHolder.itemView, pos, pos);
                    }
                });
            }
            // 设置长按事件
            if (mOnItemLongClickListener != null) {
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return mOnItemLongClickListener.onItemLongClick(null, viewHolder.itemView, pos, pos);
                    }
                });
            }
        }
    }

    /**
     * 载入viewHolder
     *
     * @param nothing 无意义的参数
     */
    public abstract void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position, boolean nothing);

}
