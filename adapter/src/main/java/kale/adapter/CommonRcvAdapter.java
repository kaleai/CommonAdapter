package kale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import kale.adapter.item.AdapterItem;
import kale.adapter.util.AdapterItemUtil;
import kale.adapter.util.IAdapter;

/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T> extends RecyclerView.Adapter implements IAdapter<T> {

    private final boolean DEBUG = false;
    
    private List<T> mDataList;

    private Object mItemType;

    private AdapterItemUtil mUtil = new AdapterItemUtil();

    protected CommonRcvAdapter(@NonNull List<T> data) {
        mDataList = data;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }
    
    @Override
    public void setData(@NonNull List<T> data) {
        mDataList = data;
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * instead by{@link #getItemType(Object)}
     */
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        mItemType = getItemType(mDataList.get(position));
        return mUtil.getIntType(mItemType);
    }

    @Override
    public Object getItemType(T t) {
        return -1; // default
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), parent, createItem(mItemType));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (DEBUG) {
            RcvAdapterItem item = (RcvAdapterItem) holder;
            item.itemView.setBackgroundColor(item.isNew ? 0xffff0000 : 0xff00ff00);
            item.isNew = false;
        }
        ((RcvAdapterItem) holder).item.handleData(convertData(mDataList.get(position)), position);
    }

    @NonNull
    @Override
    public Object convertData(T data) {
        return data;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 内部用到的viewHold
    ///////////////////////////////////////////////////////////////////////////
    
    private static class RcvAdapterItem extends RecyclerView.ViewHolder {

        protected AdapterItem item;
        
        public boolean isNew = true; // debug中才用到
        
        protected RcvAdapterItem(Context context, ViewGroup parent, AdapterItem item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false));
            this.item = item;
            this.item.bindViews(itemView);
            this.item.setViews();
        }
    }
    
}
