package kale.adapter.abs;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.util.AdapterItemUtil;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    
    private List<T> mDataList;
    
    private int mViewTypeCount;

    protected CommonAdapter(List<T> data) {
        this(data, 1);
    }
    
    protected CommonAdapter(List<T> data, int viewTypeCount) {
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    AdapterItemUtil<T> util = new AdapterItemUtil<>();
    
    /**
     * instead by
     * 
     * @see #getItemViewType(Object) 
     */
    @Override
    @Deprecated
    public int getItemViewType(int position) {
        // 如果不写这个方法，会让listView更换dataList后无法刷新数据
        return util.getRealType(getItemViewType(mDataList.get(position)));
    }

    public Object getItemViewType(T t){
        return null;
    }

    @Override
    public int getViewTypeCount(){
        return mViewTypeCount;
    }

    LayoutInflater mInflater;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        // 通过类型得到item对象
        Object type = getItemViewType(mDataList.get(position));
        AdapterItem<T> item = util.getItemByType(type, getItemView(type));
        if (convertView == null) {
            convertView = mInflater.inflate(item.getLayoutResId(), parent, false);
            item.findViews(convertView);
        }
        
        item.setViews(mDataList.get(position), position);
        return convertView;
    }

    protected abstract
    @NonNull
    AdapterItem<T> getItemView(Object type);
    
}
