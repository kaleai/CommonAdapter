package kale.commonadapter.item;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import kale.adapter.AdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.databinding.DemoItemImageBinding;
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

    private DemoItemImageBinding b;
    
    @Override
    public void bindViews(View root) {
        b = DataBindingUtil.bind(root);
    }

    @Override
    public void setViews() {
        Log.d(ImageItem.class.getSimpleName(), "setViews--------->");
    }

    @Override
    public void updateViews(DemoModel model, int position) {
        b.imageView.setImageResource(Integer.parseInt(model.content));
    }

}
