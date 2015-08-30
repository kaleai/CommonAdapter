package kale.adapter.base;

import android.support.annotation.NonNull;

import kale.adapter.AdapterItem;

/**
 * @author Jack Tony
 * @date 2015/8/30
 */
public interface BaseCommonAdapter<T> {

    @NonNull
    AdapterItem<T> getItemView(Object type);
}
