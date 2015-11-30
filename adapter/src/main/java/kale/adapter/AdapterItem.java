package kale.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;

import kale.adapter.util.BaseModel;

/**
 * adapter的所有item必须实现此接口.
 * 通过返回{@link #getLayoutResId()}来自动初始化view，之后在{@link #onBindViews(View)}中就可以初始化item的内部视图了。<br>
 *
 * @author Jack Tony
 * @date 2015/5/15
 */
public interface AdapterItem<T extends BaseModel> {

    /**
     * @return item布局文件的layoutId
     */
    @LayoutRes
    int getLayoutResId();

    /**
     * 初始化views
     */
    void onBindViews(final View root);

    /**
     * 设置view的参数
     */
    void onSetViews();

    /**
     * 根据数据来设置item的内部views
     *
     * @param model    数据list内部的model
     * @param position 当前adapter调用item的位置
     */
    void onUpdateViews(T model, int position);

}