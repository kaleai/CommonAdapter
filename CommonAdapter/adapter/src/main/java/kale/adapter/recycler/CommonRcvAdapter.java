package kale.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kale.adapter.base.AdapterItem;
import kale.adapter.model.AdapterModel;


/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T extends AdapterModel> extends RecyclerView.Adapter {

    protected List<T> mData;
    private Map<Integer,AdapterItem> adapterItemMap=new HashMap<>();

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
    public RcvAdapterItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new  RcvAdapterItem(parent.getContext(),initItemView(viewType));
    }

    protected abstract
    @NonNull
    AdapterItem initItemView( int type);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RcvAdapterItem adapterItem = (RcvAdapterItem) holder;
        adapterItem.setViews(initAdapterItem(position),mData.get(position), position);
    }

    private AdapterItem initAdapterItem(int position){

        AdapterItem  item= adapterItemMap.get(getItemViewType(position));

        if(item == null){
            item = initItemView(getItemViewType(position));
            adapterItemMap.put(getItemViewType(position),item);
        }
        return   item;
    }

    /**
     * 可以被复写用于单条刷新等
     */
    public void updateData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
