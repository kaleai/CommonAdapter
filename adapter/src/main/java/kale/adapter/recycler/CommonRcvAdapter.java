package kale.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.ViewHolder;


/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T> extends RecyclerView.Adapter {

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

    public List<T> getData() {
        return mData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * instead by {@link kale.adapter.recycler.CommonRcvAdapter} Object getItemViewType
     */
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        mPosition = position;
        return getRealType(getItemViewType(mData.get(position)));
    }

    public abstract Object getItemViewType(T item);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), parent, initItemView(getItemViewType(mData.get(mPosition))));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AdapterItem<T> adapterItem = getItemByType(getItemViewType(mData.get(position)));
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            if (adapterItem instanceof FullSpan) {
                layoutParams.setFullSpan(true);
            } else {
                layoutParams.setFullSpan(false);
            }
        }
        ((RcvAdapterItem) holder).setViews(adapterItem, mData.get(position), position);
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

    private class RcvAdapterItem extends RecyclerView.ViewHolder {

        public RcvAdapterItem(Context context, ViewGroup parent, AdapterItem item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false));
        }
        /**
         * 设置Item内部view的方法
         *
         * @param model    数据对象
         * @param position 当前item的position
         */
        public void setViews(AdapterItem<T> item, T model, int position) {
            item.initViews(ViewHolder.getInstance(itemView), model, position);
        }

    }
}
