package kale.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.AdapterModel;
import kale.adapter.ViewHolder;


/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T extends AdapterModel> extends RecyclerView.Adapter {

    protected final String TAG = getClass().getSimpleName();
    private List<T> mData;
    private int mPosition;

    protected CommonRcvAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * instead by getData().get(position).getDataType()
     */
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        mPosition = position;
        return getRealType(mData.get(position).getDataType());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), initItemView(mData.get(mPosition).getDataType()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RcvAdapterItem) holder).setViews(getItemByType(mData.get(position).getDataType()), mData.get(position), position);
    }

    protected abstract
    @NonNull
    AdapterItem<T> initItemView(Object type);

    // (type - item) = (key - value)
    private HashMap<Object, AdapterItem<T>> mItemMap = new HashMap<>();
    
    /**
     * 根据相应的类型得到item对象
     *
     * @param type item的类型
     */
    private AdapterItem<T> getItemByType(Object type) {
        AdapterItem<T> item = mItemMap.get(type);
        if (item == null) {
            item = initItemView(type);
            mItemMap.put(type, item);
        }
        return item;
    }

    private SparseArray<Object> mItemTypeSparseArr = new SparseArray<>();
    private int getRealType(Object type) {
        int realType = mItemTypeSparseArr.indexOfValue(type);
        if (realType == -1) {
            mItemTypeSparseArr.put(mItemTypeSparseArr.size() - 1, type);
            realType = mItemTypeSparseArr.size() - 1;
        }
        return realType;
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
