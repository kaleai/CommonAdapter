/**
 * @author Jack Tony
 * @date 2015/5/15
 */
package kale.commonadapter.item;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import kale.adapter.AdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.databinding.DemoItemButtonBinding;
import kale.commonadapter.model.DemoModel;

/**
 * @tips
 * 优化小技巧：这个就等于一个viewHolder，用于复用，所以不会重复建立对象
 */
public class ButtonItem implements AdapterItem<DemoModel> {

    private int mPosition;

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_button;
    }
    
    private DemoItemButtonBinding b;

    @Override
    public void onBindViews(final View root) {
        b = DataBindingUtil.bind(root);
    }

    /**
     * @tips
     * 优化小技巧：在这里直接设置按钮的监听器。
     * 因为这个方法仅仅在item建立时才调用，所以不会重复建立监听器。
     */
    @Override
    public void onSetViews() {
        Log.d(ButtonItem.class.getSimpleName(), "onSetViews--------->");
        // 这个方法仅仅在item构建时才会触发，所以在这里也仅仅建立一次监听器，不会重复建立
        b.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(b.getRoot().getContext(), "pos = " + mPosition, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onUpdateViews(DemoModel model, int position) {
        // 在每次适配器getView的时候就会触发，这里避免做耗时的操作
        mPosition = position;
        b.button.setText(model.content);
    }

}
