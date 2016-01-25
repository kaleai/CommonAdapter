package kale.adapter.item;

import android.util.Log;
import android.view.View;

/**
 * @author Kale
 * @date 2016/1/25
 */
public abstract class BaseAdapterItem<T> implements AdapterItem<T> {

    protected View root;
    
    protected int currentPos;
    
    @Override
    public void bindViews(View root) {
        this.root = root;
        bindViews();
    }

    /**
     * 方法中可以使用{@link #getView(int)}来代替{@link View#findViewById(int)}
     */
    protected abstract void bindViews();

    @Override
    public void handleData(T model, int position) {
        currentPos = position;
    }

    public int getCurrentPosition() {
        return currentPos;
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
