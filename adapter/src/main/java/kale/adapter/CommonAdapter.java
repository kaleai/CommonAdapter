package kale.adapter;

import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import kale.adapter.item.AdapterItem;
import kale.adapter.util.DataBindingJudgement;
import kale.adapter.util.IAdapter;
import kale.adapter.util.ItemTypeUtil;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class CommonAdapter<T> extends BaseAdapter implements IAdapter<T> {

    private List<T> mDataList;

    private int mViewTypeCount = 1;

    private Object mType;

    private LayoutInflater mInflater;

    private ItemTypeUtil util;

    private int currentPos;

    public CommonAdapter(@Nullable List<T> data, int viewTypeCount) {
        if (data == null) {
            data = new ArrayList<>();
        }

        if (DataBindingJudgement.SUPPORT_DATABINDING && data instanceof ObservableList) {
            ((ObservableList<T>) data).addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
                @Override
                public void onChanged(ObservableList<T> sender) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }
            });
        }
        
        mDataList = data;
        mViewTypeCount = viewTypeCount;
        util = new ItemTypeUtil();
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public void setData(@NonNull List<T> data) {
        mDataList = data;
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 通过数据得到obj的类型的type
     * 然后，通过{@link ItemTypeUtil}来转换位int类型的type
     *
     * instead by{@link #getItemType(Object)}
     */
    @Override
    @Deprecated
    public int getItemViewType(int position) {
        currentPos = position;
        mType = getItemType(mDataList.get(position));
        // 如果不写这个方法，会让listView更换dataList后无法刷新数据
        return util.getIntType(mType);
    }

    @Override
    public Object getItemType(T t) {
        return -1; // default
    }

    @Override
    public int getViewTypeCount() {
        return mViewTypeCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        final AdapterItem item;
        if (convertView == null) {
            item = createItem(mType);
            convertView = mInflater.inflate(item.getLayoutResId(), parent, false);
            convertView.setTag(R.id.tag_item, item); // get item

            item.bindViews(convertView);
            item.setViews();
        } else {
            item = (AdapterItem) convertView.getTag(R.id.tag_item); // save item
        }
        item.handleData(getConvertedData(mDataList.get(position), mType), position);
        return convertView;
    }

    @NonNull
    @Override
    public Object getConvertedData(T data, Object type) {
        return data;
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public int getCurrentPosition() {
        return currentPos;
    }
    
}
