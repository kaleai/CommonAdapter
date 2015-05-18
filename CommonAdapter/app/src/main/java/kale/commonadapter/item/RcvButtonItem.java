package kale.commonadapter.item;

import android.content.Context;
import android.widget.Button;

import kale.adapter.recycler.RcvAdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.model.TestModel;

/**
 * @author Jack Tony
 * @date 2015/5/18
 */
public class RcvButtonItem extends RcvAdapterItem<TestModel>{

    public RcvButtonItem(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    @Override
    public void setViews(TestModel data, int position) {
        Button btn = getView(R.id.button);
        btn.setText(data.getContent());
    }
}
