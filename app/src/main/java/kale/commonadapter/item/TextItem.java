package kale.commonadapter.item;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import kale.adapter.AdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.model.TextModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class TextItem implements AdapterItem<TextModel> {
    private TextView textView;
    private int mPosition;

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_text;
    }

    @Override
    public void onBindViews(View root) {
        textView = (TextView) root;
    }

    @Override
    public void onSetViews() {
        Log.d(TextItem.class.getSimpleName(), "onSetViews--------->");
        textView.setBackgroundColor(Color.WHITE);
        textView.setTextColor(Color.BLACK);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(textView.getContext(), "TextItem pos = " + mPosition, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onUpdateViews(TextModel model, int position) {
        mPosition = position;
        textView.setText(model.text);
    }

}