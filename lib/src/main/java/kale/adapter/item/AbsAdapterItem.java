package kale.adapter.item;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author Kale
 * @date 2018/4/23
 */
public abstract class AbsAdapterItem<T> implements AdapterItem<T> {

    private Context context;

    private View root;
    
    @Override
    public void bindViews(@NonNull View root) {
        this.context = root.getContext();
        this.root = root;
    }

    @Override
    public void setViews() {

    }

    public Context getContext() {
        return context;
    }

    public View getRoot() {
        return root;
    }
}
