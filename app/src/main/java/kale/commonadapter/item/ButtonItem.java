/**
 * @author Jack Tony
 * @date 2015/5/15
 */
package kale.commonadapter.item;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kale.adapter.AdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.model.ButtonModel;

/**
 * @tips
 * 优化小技巧：这个就等于一个viewHolder，用于复用，所以不会重复建立对象
 */
public class ButtonItem implements AdapterItem<ButtonModel> {

    private int mPosition;
    private Button mButton;
    private ButtonModel mMode;

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_button;
    }

    @Override
    public void onBindViews(final View root) {
        mButton = (Button) root;
    }

    /**
     * @tips
     * 优化小技巧：在这里直接设置按钮的监听器。
     * 因为这个方法仅仅在item建立时才调用，所以不会重复建立监听器。
     */
    @Override
    public void onSetViews() {
        Log.d(ButtonItem.class.getSimpleName(), "onSetViews--------->");
        mButton.setBackgroundColor(Color.BLACK);
        mButton.setTextColor(Color.WHITE);
        // 这个方法仅仅在item构建时才会触发，所以在这里也仅仅建立一次监听器，不会重复建立
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMode.content += " clicked";
                mButton.setText(mMode.content);
                Toast.makeText(mButton.getContext(), "ButtonItem pos = " + mPosition, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onUpdateViews(ButtonModel model, int position) {
        mMode = model;
        // 在每次适配器getView的时候就会触发，这里避免做耗时的操作
        mPosition = position;
        mButton.setText(model.content);
    }

}
