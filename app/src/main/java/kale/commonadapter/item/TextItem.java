package kale.commonadapter.item;


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
    public void findViews(View root) {
        textView = (TextView) root.findViewById(R.id.textView);
    }

    @Override
    public void setViews(DemoModel model, int position) {
        textView.setText(model.content);
    }

}

