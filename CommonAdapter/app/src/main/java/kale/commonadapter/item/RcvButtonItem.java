package kale.commonadapter.item;

import android.content.Context;
import android.util.Log;
import android.widget.Button;

import kale.adapter.base.AdapterItem;
import kale.adapter.base.ViewHolder;
import kale.adapter.recycler.RcvAdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.model.TestModel;

/**
 * @author Jack Tony
 * @date 2015/5/18
 */
public class RcvButtonItem implements AdapterItem<TestModel> {


    private int layoutId;

    public RcvButtonItem( int layoutResId) {
        layoutId=layoutResId;
        Log.e("ddd", "RcvButtonItem");
    }


    @Override
    public int getLayoutResId() {
        return layoutId;
    }

    @Override
    public void initViews(ViewHolder vh, TestModel data, int position) {
        Button btn = vh.get(R.id.button);
        btn.setText(data.getContent());
    }
}
