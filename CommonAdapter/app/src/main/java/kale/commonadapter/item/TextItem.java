package kale.commonadapter.item;


import android.util.Log;
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
        Log.d("item", "pos = " + position);
         
        TextView textView = vh.get(R.id.textView);

        if (textView == null) {
            Log.d("item", "textView is null");
        } else {
            Log.d("item", "textView not null");
        }
        textView.setText(model.content);
    }

}

