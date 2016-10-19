package kale.commonadapter.util;

import android.util.Log;
import android.view.View;

import kale.adapter.item.AdapterItem;

/**
 * @author Kale
 * @date 2016/1/25
 */
public abstract class BaseAdapterItem<T> implements AdapterItem<T> {

    protected View root;
    
    @Override
    public void bindViews(View root) {
        this.root = root;
        bindViews();
    }

    /**
     * 方法中可以使用{@link #getView(int)}来代替{@link View#findViewById(int)}
     */
    protected abstract void bindViews();

    public View getRoot() {
        return root;
    }

    public final <E extends View> E getView(int id) {
        try {
            return (E) root.findViewById(id);
        } catch (ClassCastException ex) {
            Log.e("BaseAdapterItem", "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }
}
