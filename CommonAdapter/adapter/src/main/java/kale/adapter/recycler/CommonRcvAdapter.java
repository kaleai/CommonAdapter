package kale.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.AdapterModel;
import kale.adapter.ViewHolder;


/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T extends AdapterModel> extends RecyclerView.Adapter {

    private List<T> mData;

    private SparseArray<AdapterItem<T>> mItemSparseArr = new SparseArray<>();

    protected CommonRcvAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemType(mData.get(position).getDataType());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), initItemView(mTypeArr.valueAt(viewType)));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RcvAdapterItem adapterItem = (RcvAdapterItem) holder;
        adapterItem.setViews(getItemByType(getItemViewType(position)), mData.get(position), position);
    }

    protected abstract
    @NonNull
    AdapterItem<T> initItemView(Object type);

    int typeNum = 0;
    private SparseArray<Object> mTypeArr = new SparseArray<>();

    private int getItemType(Object type) {
        int index = mTypeArr.indexOfValue(type);
        if (index == -1) {
            mTypeArr.put(typeNum, type);
            index = typeNum;
            typeNum++;
        }
        return index;
    }
    
    /**
     * 根据相应的类型得到item对象
     *
     * @param type item的类型
     */
    private AdapterItem<T> getItemByType(int type) {
        AdapterItem<T> item = mItemSparseArr.get(type, null);

        if (item == null) {
            item = initItemView(mTypeArr.valueAt(type));
            mItemSparseArr.put(type, item);
        }
        return item;
    }

    /**
     * 可以被复写用于单条刷新等
     */
    public void updateData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }
    
    private class RcvAdapterItem extends RecyclerView.ViewHolder {

        public RcvAdapterItem(Context context, AdapterItem item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(), null));
        }

        /**
         * 设置Item内部view的方法
         *
         * @param model     数据对象
         * @param position 当前item的position
         */
        public void setViews(AdapterItem<T> item, T model, int position){
            item.initViews(ViewHolder.getInstance(itemView), model, position);
        }
    }
}
