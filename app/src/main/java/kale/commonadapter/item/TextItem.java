package kale.commonadapter.item;


import android.util.Log;
import android.view.View;
import android.widget.TextView;

import kale.adapter.item.AdapterItem;
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

    TextView textView;

    @Override
    public void onBindViews(View root) {
        textView = (TextView) root.findViewById(R.id.textView);
    }

    @Override
    public void onSetViews() {
        Log.d(TextItem.class.getSimpleName(), "onSetViews--------->");
    }

    @Override
    public void onUpdateViews(DemoModel model, int position) {
        textView.setText(model.content);
    }

}

