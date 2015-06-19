package kale.commonadapter.item;

import android.widget.Button;

import kale.adapter.AdapterItem;
import kale.adapter.ViewHolder;
import kale.commonadapter.R;
import kale.commonadapter.model.DemoModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ButtonItem implements AdapterItem<DemoModel> {

    Button btn;

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_button;
    }

    @Override
    public void initViews(ViewHolder vh, DemoModel model, int position) {
        btn = vh.get(R.id.button);
        setViews(model);
    }

    private void setViews(DemoModel data) {
        btn.setText(data.content);
    }

}
