package kale.adapter.base;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import kale.adapter.R;
import kale.adapter.model.AdapterModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class CommonAdapter<T extends AdapterModel> extends BaseAdapter {

    protected List<T> mData;

    private Map<Integer,AdapterItem> adapterItemMap=new HashMap<>();

    protected CommonAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getDataType();
    }

    @Override
    public int getViewTypeCount() {
        return mData.get(0).getDataTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        AdapterItem item =initAdapterItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(item.getLayoutResId(), null);
        }
        item.initViews(ViewHolder.newInstant(convertView),mData.get(position), position);
        return convertView;
    }


    private AdapterItem initAdapterItem(int position){

       AdapterItem  item= adapterItemMap.get(getItemViewType(position));

        if(item == null){
            item = initItemView(getItemViewType(position));
            adapterItemMap.put(getItemViewType(position),item);
        }
        return   item;
    }

    protected abstract
    @NonNull
    AdapterItem initItemView(int type);

    /**
     * 可以被复写用于单条刷新等
     */
    public void updateData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
