package kale.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import kale.adapter.AdapterModel;


/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T extends AdapterModel> extends BaseRecyclerAdapter {

    protected List<T> mData;

    protected CommonRcvAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    @Override
    public int getAdapterItemType(int position) {
        return mData.get(position).getDataType();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(Context context, int viewType) {
        return initItemView(context, viewType);
    }

    protected abstract
    @NonNull
    RcvAdapterItem initItemView(Context context, int type);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, boolean nothing) {
        RcvAdapterItem adapterItem = (RcvAdapterItem) viewHolder;
        adapterItem.setViews(mData.get(position), position);
    }

    public void updateData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
