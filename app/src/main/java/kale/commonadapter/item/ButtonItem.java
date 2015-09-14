package kale.commonadapter.item;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kale.adapter.AdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.model.DemoModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ButtonItem implements AdapterItem<DemoModel> {

    private Button btn;
    private int mPosition;

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_button;
    }

    @Override
    public void findViews(final View root) {
        btn = (Button) root.findViewById(R.id.button);
        // 这个方法仅仅在item构建时才会触发，所以在这里也仅仅建立一次监听器，不会重复建立
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(root.getContext(), "pos = " + mPosition, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void setViews(DemoModel model, int position) {
        // 在每次适配器getView的时候就会触发，这里避免做耗时的操作
        mPosition = position;
        btn.setText(model.content);
    }

}
