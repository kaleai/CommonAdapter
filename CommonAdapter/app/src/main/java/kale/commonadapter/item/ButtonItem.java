package kale.commonadapter.item;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import kale.adapter.base.AdapterItem;
import kale.adapter.base.ViewHolder;
import kale.commonadapter.R;
import kale.commonadapter.model.TestModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ButtonItem implements AdapterItem<TestModel> {

    Button btn;

    public  ButtonItem(){

        Log.e("ddd", "ButtonItem");

    }

    @Override
    public int getLayoutResId() {
        return R.layout.button_adapter_item;
    }

    @Override
    public void initViews(ViewHolder vh, TestModel data, int position) {
        btn = vh.getButton(R.id.button);
        setViews(data);
    }



    private void setViews(TestModel data) {
        btn.setText(data.getContent());
    }

}
