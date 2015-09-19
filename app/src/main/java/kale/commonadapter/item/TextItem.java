package kale.commonadapter.item;


import android.util.Log;
import android.view.View;
import android.widget.TextView;

import kale.adapter.AdapterItem;
import kale.adapter.recycler.FullSpan;
import kale.commonadapter.R;
import kale.commonadapter.model.DemoModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class TextItem implements AdapterItem<DemoModel> ,FullSpan{

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_text;
    }

    TextView textView;

    @Override
    public void bindViews(View root) {
        textView = (TextView) root.findViewById(R.id.textView);
    }

    @Override
    public void setViews() {
        Log.d(TextItem.class.getSimpleName(), "setViews--------->");
    }

    @Override
    public void updateViews(DemoModel model, int position) {
        textView.setText(model.content);
    }

}

