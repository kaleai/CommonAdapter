package kale.adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import kale.adapter.util.BaseModel;
import kale.adapter.util.DebugUtil;

/**
 * Created by JWBlue.Liu on 15/12/1.
 */
public class DefaultItem implements AdapterItem<BaseModel> {
    private TextView tv;

    @Override
    public int getLayoutResId() {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public void onBindViews(View root) {
        tv = (TextView) root;
    }

    @Override
    public void onSetViews() {
        tv.setTextColor(Color.RED);
        tv.setGravity(Gravity.CENTER);
    }

    @Override
    public void onUpdateViews(BaseModel model, int position) {
        if (DebugUtil.DEBUG) {
            tv.setText("error!!!");
        } else {
            // TODO hide item
        }
    }
}
