package kale.commonadapter.item;

import android.util.Log;
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


    public  ImageItem(){

        Log.e("ddd", "ImageItem");

    }



    @Override
    public int getLayoutResId() {
        return R.layout.image_adapter_item;
    }

    @Override
    public void initViews(ViewHolder vh, TestModel data, int position) {
        ImageView imageView = vh.getImageView( R.id.imageView);
        imageView.setImageResource(Integer.parseInt(data.getContent()));

    }

}
