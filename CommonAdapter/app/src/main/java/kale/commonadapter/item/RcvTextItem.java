package kale.commonadapter.item;

import android.content.Context;
import android.widget.TextView;

import kale.adapter.recycler.RcvAdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.model.TestModel;

/**
 * @author Jack Tony
 * @date 2015/5/18
 */
public class RcvTextItem extends RcvAdapterItem<TestModel> {

    public RcvTextItem(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    @Override
    public void setViews(TestModel data, int position) {
        TextView textView = getView(R.id.textView);
        textView.setText(data.getContent());
    }
}
