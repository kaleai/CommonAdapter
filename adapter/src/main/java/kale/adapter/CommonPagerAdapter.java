package kale.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kale.adapter.item.AdapterItem;
import kale.adapter.util.IAdapter;

/**
 * @author Jack Tony
 * @date 2015/11/29
 */
public abstract class CommonPagerAdapter<T> extends BasePagerAdapter<View> implements IAdapter<T> {

    private List<T> mDataList;

    LayoutInflater mInflater;

    public CommonPagerAdapter(@Nullable List<T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mDataList = data;
    }

    @Override
    public int getCount() {
        return mDataList.size();
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
            AdapterItem item = (AdapterItem) ((View) object).getTag(R.id.tag_item);
            item.handleData(getConvertedData(mDataList.get(position), getItemType(position)), position);
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    protected View createItem(ViewPager viewPager, int position) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewPager.getContext());
        }
        AdapterItem item = createItem(getItemType(position));
        View view = mInflater.inflate(item.getLayoutResId(), null);
        view.setTag(R.id.tag_item, item); // 万一你要用到这个item可以通过这个tag拿到
        item.bindViews(view);
        item.setViews();
        return view;
    }

    @NonNull
    @Override
    public Object getConvertedData(T data, Object type) {
        return data;
    }

    /**
     * 强烈建议返回string,int,bool类似的基础对象做type
     */
    public Object getItemType(int position) {
        return getItemType(mDataList.get(position));
    }

    @Override
    public Object getItemType(T t) {
        return -1; // default
    }

    @Override
    public void setData(@NonNull List<T> data) {
        mDataList = data;
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }
}
