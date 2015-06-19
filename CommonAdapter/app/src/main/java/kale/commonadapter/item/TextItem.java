package kale.commonadapter.item;


import android.widget.TextView;

import kale.adapter.AdapterItem;
import kale.adapter.ViewHolder;
import kale.commonadapter.R;
import kale.commonadapter.model.DemoModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class TextItem implements AdapterItem<DemoModel> {

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_text;
    }

    @Override
    public void initViews(ViewHolder vh, DemoModel model, int position) {
        TextView textView = vh.get(R.id.textView);
        textView.setText(model.content);
    }

}

