package kale.adapter.base;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import kale.adapter.AdapterModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class CommonAdapter<T extends AdapterModel> extends BaseAdapter {

    protected List<T> mData;

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
        AdapterItem item = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            item = initItemView(getItemViewType(position));
            if (item == null) {
                throw new RuntimeException(
                        "The item type { " + getItemViewType(position) + " } is unknown,please ensure you had this type in data list");
            }
            convertView = inflater.inflate(item.getLayoutResId(), null);
        }

        if (item == null) {
            item = initItemView(getItemViewType(position));
        }
        item.initViews(convertView, mData.get(position), position);
        return convertView;
    }

    protected abstract
    @NonNull
    AdapterItem initItemView(int type);

    public void updateData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
