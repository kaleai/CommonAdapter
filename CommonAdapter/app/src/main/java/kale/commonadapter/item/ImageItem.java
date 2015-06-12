package kale.commonadapter.item;

import android.widget.ImageView;

import kale.adapter.AdapterItem;
import kale.adapter.ViewHolder;
import kale.commonadapter.R;
import kale.commonadapter.model.TestModel;


/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ImageItem implements AdapterItem<TestModel> {


    @Override
    public int getLayoutResId() {
        return R.layout.image_adapter_item;
    }

    @Override
    public void initViews(ViewHolder vh, TestModel model, int position) {
        ImageView imageView = vh.get(R.id.imageView);
        imageView.setImageResource(Integer.parseInt(model.getContent()));
    }

}
