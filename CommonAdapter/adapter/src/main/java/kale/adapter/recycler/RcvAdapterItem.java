package kale.adapter.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import kale.adapter.AdapterModel;


/**
 * RecyclerView的Item都需要继承这个类，继承后就可以通过getView来得到Item中的view了
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class RcvAdapterItem<T extends AdapterModel> extends RecyclerView.ViewHolder {

    /**
     * 构造方法
     * @param context context对象
     * @param layoutResId 这个item布局文件的id
     */
    public RcvAdapterItem(Context context, @LayoutRes int layoutResId) {
        super(LayoutInflater.from(context).inflate(layoutResId, null));
    }

    /**
     * 设置Item内部view的方法
     * @param data 数据对象
     * @param position 当前item的position
     */
    public abstract void setViews(T data, int position);

    /**
     * 找到view的方法，等同于findViewById()
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(int id) {
        return (T) itemView.findViewById(id);
    }

}  