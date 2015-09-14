package kale.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.util.AdapterItemUtil;

/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T> extends RecyclerView.Adapter {

    protected final String TAG = getClass().getSimpleName();

    private List<T> mDataList;

    protected CommonRcvAdapter(List<T> data) {
        mDataList = data;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    /**
     * 可以被复写用于单条刷新等
     */
    public void updateData(List<T> data) {
        mDataList = data;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    AdapterItemUtil<T> mUtil = new AdapterItemUtil<>();

    Object mItemType;

    /**
     * instead by{@link #getItemViewType(Object)}
     */
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        mItemType = getItemViewType(mDataList.get(position));
        //Log.d("ddd", "getType = " + mUtil.getIntType(mItemType));
        return mUtil.getIntType(mItemType);
    }

    public Object getItemViewType(T t) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), parent, getItemView(mItemType));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RcvAdapterItem) holder).getItem().setViews(mDataList.get(position), position);
    }

    public abstract
    @NonNull
    AdapterItem<T> getItemView(Object type);

    private class RcvAdapterItem extends RecyclerView.ViewHolder {

        private AdapterItem<T> mItem;

        protected RcvAdapterItem(Context context, ViewGroup parent, AdapterItem<T> item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false));
            mItem = item;
            mItem.findViews(itemView);
        }

        protected AdapterItem<T> getItem() {
            return mItem;
        }

    }

}
