package kale.commonadapter.item;

import android.widget.ImageView;

import kale.adapter.AdapterItem;
import kale.adapter.ViewHolder;
import kale.commonadapter.R;
import kale.commonadapter.model.DemoModel;


/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ImageItem implements AdapterItem<DemoModel> {


    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_image;
    }

    @Override
    public void initViews(ViewHolder vh, DemoModel model, int position) {
        ImageView imageView = vh.getView(R.id.imageView);
        imageView.setImageResource(Integer.parseInt(model.content));
    }

}
