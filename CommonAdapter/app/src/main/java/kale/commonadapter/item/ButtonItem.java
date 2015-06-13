package kale.commonadapter.item;

import android.widget.Button;

import kale.adapter.AdapterItem;
import kale.adapter.ViewHolder;
import kale.commonadapter.R;
import kale.commonadapter.model.TestModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ButtonItem implements AdapterItem<TestModel> {

    Button btn;

    @Override
    public int getLayoutResId() {
        return R.layout.button_adapter_item;
    }

    @Override
    public void initViews(ViewHolder vh, TestModel model, int position) {
        btn = vh.get(R.id.button);
        setViews(model);
    }

    private void setViews(TestModel data) {
        btn.setText(data.content);
    }

}
