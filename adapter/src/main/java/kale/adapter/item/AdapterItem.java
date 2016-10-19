package kale.adapter.item;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Adapter的所有item必须实现的接口.<br>
 * 
 * 通过{@link #getLayoutResId()}初始化view;<br>
 * 在{@link #bindViews(View)}中就初始化item的内部视图<br>
 * 在{@link #handleData(Object, int)}中处理每一行的数据<p>
 * 
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

}  