package kale.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import kale.adapter.AdapterModel;


/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class RcvAdapterItem<T extends AdapterModel> extends RecyclerView.ViewHolder {

    public RcvAdapterItem(Context context, int layoutResId) {
        super(LayoutInflater.from(context).inflate(layoutResId, null));
    }

    public abstract void setViews(T data, int position);

    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(int id) {
        return (T) itemView.findViewById(id);
    }

}  