package kale.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import kale.adapter.model.AdapterModel;


/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T extends AdapterModel> extends RecyclerView.Adapter{

    protected List<T> mData;

    protected CommonRcvAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getDataType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return initItemView(parent.getContext(), viewType);
    }

    protected abstract
    @NonNull
    RcvAdapterItem initItemView(Context context, int type);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RcvAdapterItem adapterItem = (RcvAdapterItem) holder;
        adapterItem.setViews(mData.get(position), position);
    }

    public void updateData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
