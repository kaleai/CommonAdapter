package kale.adapter.recycler;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.util.AdapterItemHelper;
import kale.adapter.util.BaseModel;

/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T extends BaseModel> extends RecyclerView.Adapter {
    private static final boolean DEBUG = false;

    private AdapterItemHelper mAdapterItemHelper = new AdapterItemHelper();
    private List<T> mDataList;
    private Object mItemType;

    protected CommonRcvAdapter(List<T> data) {
        mDataList = data;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * instead by{@link #getItemViewType(T)}
     */
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        mItemType = getItemViewType(mDataList.get(position));
        return mAdapterItemHelper.getIntType(mItemType);
    }

    public Object getItemViewType(T t) {
        return t.itemType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), parent, getAdapterItem(mItemType));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (DEBUG) {
            RcvAdapterItem item = (RcvAdapterItem) holder;
            item.itemView.setBackgroundColor(item.isNew ? Color.GREEN : Color.GRAY);
            item.isNew = false;
        }
        ((RcvAdapterItem) holder).getItem().onUpdateViews(mDataList.get(position), position);
    }

    public List<T> getDataList() {
        return mDataList;
    }

    /**
     * 可以被复写用于单条刷新等
     */
    public void updateData(@NonNull List<T> data) {
        mDataList = data;
        notifyDataSetChanged();
    }

    public abstract
    @NonNull
    AdapterItem<T> getAdapterItem(Object type);

    private class RcvAdapterItem extends RecyclerView.ViewHolder {

        private AdapterItem<T> mItem;
        
        public boolean isNew = true; // debug中才用到
        
        protected RcvAdapterItem(Context context, ViewGroup parent, AdapterItem<T> item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false));
            mItem = item;
            mItem.onBindViews(itemView);
            mItem.onSetViews();
        }

        protected AdapterItem<T> getItem() {
            return mItem;
        }
    }
    
}