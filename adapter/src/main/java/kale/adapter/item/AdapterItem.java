package kale.adapter.item;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public interface AdapterItem<T> {

    /**
     * @return item布局文件的layoutId
     */
    @LayoutRes
    int getLayoutResId();

    /**
     * 初始化views
     */
    void bindViews(final View root);

    /**
     * 设置view的参数
     */
    void setViews();

    /**
     * 根据数据来设置item的内部views
     *
     * @param t    数据list内部的model
     * @param position 当前adapter调用item的位置
     */
    void handleData(T t, int position);

    /**
     * item 添加到了window，离开屏幕，可以设置监听，注册观察者
     * @param t
     * @param position
     */
    void onViewAttachedToWindow(T t, int position);

    /**
     * item 从离开屏幕，可以回收监听，取消观察者
     * @param t
     * @param position
     */
    void onViewDetachedFromWindow(T t, int position);
}