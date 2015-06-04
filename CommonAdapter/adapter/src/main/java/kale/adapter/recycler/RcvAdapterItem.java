package kale.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import kale.adapter.R;
import kale.adapter.base.AdapterItem;
import kale.adapter.base.ViewHolder;
import kale.adapter.model.AdapterModel;


/**
 * RecyclerView的Item都需要继承这个类，继承后就可以通过getView来得到Item中的view了
 *
 * @author Jack Tony
 * @date 2015/5/15
 */
public class RcvAdapterItem extends RecyclerView.ViewHolder {


    private ViewHolder vh;

    /**
     * 构造方法
     *
     * @param context     context对象
     * @param layoutResId 这个item布局文件的id
     */
    public RcvAdapterItem(Context context, AdapterItem t) {
        super((LayoutInflater.from(context).inflate(t.getLayoutResId(), null)));
        itemView.setTag(t);
        vh = ViewHolder.newInstant(itemView);
    }


    /**
     * 设置Item内部view的方法
     *
     * @param data     数据对象
     * @param position 当前item的position
     */
    public void setViews( AdapterItem t,AdapterModel data, int position) {

        t.initViews(vh, data, position);
    }


}  