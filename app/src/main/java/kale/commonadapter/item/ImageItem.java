package kale.commonadapter.item;

import android.view.View;
import android.widget.ImageView;

import kale.adapter.AdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.model.DemoModel;


/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ImageItem implements AdapterItem<DemoModel> {

    ImageView imageView;

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_image;
    }

    @Override
    public void findViews(View root) {
        imageView = (ImageView) root.findViewById(R.id.imageView);
    }

    @Override
    public void setViews(DemoModel model, int position) {
        imageView.setImageResource(Integer.parseInt(model.content));
    }

}
