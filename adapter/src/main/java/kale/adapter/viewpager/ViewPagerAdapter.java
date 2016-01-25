package kale.adapter.viewpager;

import android.view.View;

/**
 * @author Kale
 * @date 2016/1/24
 */
public abstract class ViewPagerAdapter extends BasePagerAdapter<View>{

    @Override
    protected View getWillBeAddedView(View item, int position) {
        return item;
    }

    @Override
    protected View getViewFromItem(View item) {
        return item;
    }

    @Override
    protected View getWillBeDestroyedView(View item, int position) {
        return item;
    }

}
