package kale.commonadapter.item;

import android.view.View;
import android.widget.ImageView;

import kale.adapter.base.AdapterItem;
import kale.adapter.base.ViewHolder;
import kale.commonadapter.R;
import kale.commonadapter.model.TestModel;


/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ImageItem implements AdapterItem<TestModel> {

    @Override
    public void initViews(View convertView, TestModel data, int position) {
        ImageView imageView = ViewHolder.getView(convertView, R.id.imageView);
        imageView.setImageResource(Integer.parseInt(data.getContent()));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.image_adapter_item;
    }

}
