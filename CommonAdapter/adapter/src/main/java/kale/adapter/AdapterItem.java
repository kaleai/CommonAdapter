package kale.adapter;

import android.view.View;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public interface AdapterItem<T extends AdapterModel> {

    public void initViews(View convertView, T data, int position);

    public int getLayoutResId();

}  