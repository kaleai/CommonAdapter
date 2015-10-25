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

    private int mOldImageUrl = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_image;
    }

    private DemoItemImageBinding b;

    @Override
    public void onBindViews(View root) {
        b = DataBindingUtil.bind(root);
    }

    @Override
    public void onSetViews() {
        Log.d(ImageItem.class.getSimpleName(), "onSetViews--------->");
    }

    /**
     * @tips 优化小技巧：对于图片这样的对象，我们先判断要加载的图片是不是之前的图片，如果是就不重复加载了
     * 这里为了演示方便没从网络加图，所以url是用int标识的，一般情况下都是用string标识
     * 
     * 这里仅仅是用图片做个说明，你完全可以在textview显示文字前判断一下要显示的文字和已经显示的文字是否不同
     */
    @Override
    public void onUpdateViews(DemoModel model, int position) {
        if (b.imageView.getTag() != null) {
            mOldImageUrl = (int) b.imageView.getTag();
        }
        int imageUrl = Integer.parseInt(model.content);
        
        if (mOldImageUrl == 0 && mOldImageUrl != imageUrl) {
            Log.d(ImageItem.class.getSimpleName(), "update image--------->");
            b.imageView.setTag(imageUrl);
            
            b.imageView.setImageResource(imageUrl); // load image
        }
    }


}
