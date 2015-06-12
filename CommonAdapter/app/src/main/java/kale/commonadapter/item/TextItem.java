package kale.commonadapter.item;


import android.widget.TextView;

import kale.adapter.AdapterItem;
import kale.adapter.ViewHolder;
import kale.commonadapter.R;
import kale.commonadapter.model.TestModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class TextItem implements AdapterItem<TestModel> {

    @Override
    public int getLayoutResId() {
        return R.layout.text_adapter_item;
    }

    @Override
    public void initViews(ViewHolder vh, TestModel model, int position) {
        TextView textView = vh.get(R.id.textView);
        textView.setText(model.getContent());
    }

}

