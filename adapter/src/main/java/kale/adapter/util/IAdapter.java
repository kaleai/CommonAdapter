package kale.adapter.util;

import android.support.annotation.NonNull;

import java.util.List;

import kale.adapter.item.AdapterItem;

/**
 * @author Jack Tony
 * @date 2015/11/29
 * 通用的adapter必须实现的接口，作为方法名统一的一个规范
 */
public interface IAdapter<T> {

    void setData(@NonNull List<T> data);

    List<T> getData();

    Object getItemType(T t);

    T getItem(int position);

    /**
     * 当缓存中无法得到所需item时才会调用
     */
    @NonNull
    AdapterItem createItem(Object type);

    /**
     * @param data 从原始的list中取得得数据
     * @return 放入adapterItem的最终数据
     */
    @NonNull
    Object convertData(T data);
}
