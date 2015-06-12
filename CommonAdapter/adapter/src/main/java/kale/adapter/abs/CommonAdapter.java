package kale.adapter.abs;

import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.ViewHolder;
import kale.adapter.AdapterModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class CommonAdapter<T extends AdapterModel> extends BaseAdapter {

    private List<T> mData;
    private SparseArray<AdapterItem<T>> mAdapterItemSparseArr = new SparseArray<>();
    
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
        AdapterItem<T> item = getItemByType(getItemViewType(position));
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(item.getLayoutResId(), null);
        }
        item.initViews(ViewHolder.getInstance(convertView), mData.get(position), position);
        return convertView;
    }

    protected abstract
    @NonNull
    AdapterItem<T> initItemView(int type);

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

    /**
     * 根据相应的类型得到item对象
     *
     * @param type item的类型
     */
    private AdapterItem<T> getItemByType(int type) {
        AdapterItem<T> item = mAdapterItemSparseArr.get(type, null);

        if (item == null) {
            item = initItemView(type);
            mAdapterItemSparseArr.put(type, item);
        }
        return item;
    }
}
