package kale.commonadapter.item;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import kale.adapter.base.AdapterItem;
import kale.adapter.base.ViewHolder;
import kale.adapter.recycler.RcvAdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.model.TestModel;

/**
 * @author Jack Tony
 * @date 2015/5/18
 */
public class RcvImageItem implements AdapterItem<TestModel> {

    public  RcvImageItem(){


    }

    private int layoutId;

    public RcvImageItem( int layoutResId) {
        layoutId=layoutResId;

        Log.e("ddd", "RcvImageItem");
    }





    @Override
    public int getLayoutResId() {
        return layoutId;
    }

    @Override
    public void initViews(ViewHolder vh, TestModel data, int position) {
        ImageView imageView = vh.get(R.id.imageView);
        imageView.setImageResource(Integer.parseInt(data.getContent()));
    }
}
