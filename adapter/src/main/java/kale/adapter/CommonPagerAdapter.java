package kale.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kale.adapter.item.AdapterItem;

/**
 * @author Jack Tony
 * @date 2015/11/29
 */
public abstract class CommonPagerAdapter<T extends AdapterItem> extends BasePagerAdapter<View> implements IAdapter<T>{

    private List<T> mData;

    LayoutInflater mInflater;

    public CommonPagerAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    protected View getViewFromItem(View item) {
        return item;
    }

    @Override
    protected View getWillBeAddedView(View item, int position) {
        return item;
    }

    @Override
    protected View getWillBeDestroyedView(View item, int position) {
        return item;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, @NonNull Object object) {
        // 这里应该放置数据更新的操作
        if (object != currentItem) {
            ((AdapterItem<T>) ((View) object).getTag(R.id.tag_item)).onUpdateViews(mData.get(position), position);
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    protected View onCreateItem(ViewGroup container, int position) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(container.getContext());
        }
        AdapterItem<T> item = onCreateItem(getItemType(position));
        View view = mInflater.inflate(item.getLayoutResId(), null);
        view.setTag(R.id.tag_item, item); // 万一你要用到这个item可以通过这个tag拿到
        item.onBindViews(view);
        item.onSetViews();
        return view;
    }

    /**
     * 强烈建议返回string,int,bool类似的基础对象做type
     */
    @Override
    public Object getItemType(int position) {
        return getItemType(mData.get(position));
    }

    @Override
    public Object getItemType(T t) {
        return -1; // default
    }

    @Override
    public void setData(@NonNull List<T> data) {
        mData = data;
    }

    @Override
    public List<T> getData() {
        return mData;
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

}
