package kale.adapter;

import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Jack Tony
 * @date 2015/11/21
 *
 * 如果调用{@link #notifyDataSetChanged()}来更新，
 * 它会自动调用{@link #instantiateItem(ViewGroup, int)}重新new出需要的item，算是完全初始化一次。
 */
abstract class BasePagerAdapter<T> extends PagerAdapter {

    public static final String TAG = "BasePagerAdapter";

    private int mChildCount = 0;

    protected T currentItem;

    /**
     * 这的cache的最大大小是：type x pageSize
     */
    private final PagerCache<T> mCache;

    public BasePagerAdapter() {
        mCache = new PagerCache<>();
    }

    /**
     * 注意：这里必须是view和view的比较
     */
    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == getViewFromItem((T) obj);
    }

    @Override
    public T instantiateItem(ViewGroup container, int position) {
        T item = mCache.getItem(getItemType(position));
        if (item == null) {
            item = onCreateItem(container, position);
        }
        container.addView(getWillBeAddedView(item, position));
        return item;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (object != currentItem) {
            // 可能是currentItem不等于null，可能是二者不同
            currentItem = (T) object;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        T item = (T) object;
        Object type = getItemType(position);
        container.removeView(getWillBeDestroyedView(item, position));
        mCache.putItem(type, item);
    }

    @Override
    public int getItemPosition(Object object) {
        // 开始逐个刷新item
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    public Object getItemType(int position) {
        return -1; // default
    }

    public T getCurrentItem() {
        return currentItem;
    }

    protected PagerCache<T> getCache() {
        return mCache;
    }

    /**
     * @return obj中的view对象
     */
    protected abstract View getViewFromItem(T item);

    /**
     * 得到初始化后的item中的view
     */
    protected abstract View getWillBeAddedView(T item, int position);

    /**
     * 当{@link ViewPager#getOffscreenPageLimit()}缓存的大小不够时，会移出最早显示的item
     *
     * @return 被移除的item中view的对象（如果item是view那么直接返回即可）
     */
    protected abstract View getWillBeDestroyedView(T item, int position);

    /**
     * 当缓存中无法得到所需item时才会调用
     */
    protected abstract T onCreateItem(ViewGroup container, int position);

    ///////////////////////////////////////////////////////////////////////////
    // 缓存类
    ///////////////////////////////////////////////////////////////////////////

    public static class PagerCache<T> {

        private Map<Object, Queue<T>> mCacheMap;

        public PagerCache() {
            mCacheMap = new ArrayMap<>();
        }

        public T getItem(Object key) {
            Queue<T> queue;
            if ((queue = mCacheMap.get(key)) != null) {
                return queue.poll(); // 如果拿不到也会返回null
            } else {
                return null;
            }
        }

        public void putItem(Object key, T item) {
            Queue<T> queue;
            if ((queue = mCacheMap.get(key)) == null) {
                queue = new LinkedList<>();
                mCacheMap.put(key, queue);
            }
            queue.offer(item);
        }
    }


}
