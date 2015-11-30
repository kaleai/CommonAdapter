package kale.adapter.abs;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.R;
import kale.adapter.util.BaseModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class CommonAdapter<T extends BaseModel> extends BaseAdapter {

    private final boolean DEBUG = false;
    private List<T> mDataList;
    private int mViewTypeCount;
    private Object mType;
    private LayoutInflater mInflater;

    protected CommonAdapter(@NonNull List<T> data) {
        this(data, 1);
    }
    protected CommonAdapter(@NonNull List<T> data, int viewTypeCount) {
        mDataList = data;
        mViewTypeCount = viewTypeCount;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * instead by{@link #getItemViewType(T)}
     */
    @Override
    @Deprecated
    public int getItemViewType(int position) {
        mType = getItemViewType(mDataList.get(position));
        //Log.d("ddd", "getType = " + util.getIntType(mType));
        // 如果不写这个方法，会让listView更换dataList后无法刷新数据
        return mType.hashCode();
    }

    public Object getItemViewType(T t) {
        return t.itemType;
    }

    @Override
    public int getViewTypeCount() {
        return mViewTypeCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Log.d("ddd", "getView");
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        
        AdapterItem<T> item;
        if (convertView == null) {
            item = getItemView(mType);
            convertView = mInflater.inflate(item.getLayoutResId(), parent, false);
            convertView.setTag(R.id.tag_item, item);
            item.onBindViews(convertView);
            item.onSetViews();
            if (DEBUG) convertView.setBackgroundColor(0xffff0000);
        } else {
            item = (AdapterItem<T>) convertView.getTag(R.id.tag_item);
            if (DEBUG) convertView.setBackgroundColor(0xff00ff00);
        }
        item.onUpdateViews(mDataList.get(position), position);
        return convertView;
    }

    /**
     * 可以被复写用于单条刷新等
     */
    public void updateData(List<T> data) {
        mDataList = data;
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public abstract
    @NonNull
    AdapterItem<T> getItemView(Object type);

}
