package kale.commonadapter.item;


import android.util.Log;
import android.view.View;
import android.widget.TextView;

import kale.adapter.base.AdapterItem;
import kale.adapter.base.ViewHolder;
import kale.commonadapter.R;
import kale.commonadapter.model.TestModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class TextItem implements AdapterItem<TestModel> {


    public  TextItem(){

        Log.e("ddd", "TextItem");

    }

    @Override
    public int getLayoutResId() {
        return R.layout.text_adapter_item;
    }

    @Override
    public void initViews(ViewHolder vh, TestModel data, int position) {
        TextView textView = vh.get( R.id.textView);
        textView.setText(data.getContent());
    }


}

