package kale.commonadapter.item;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import kale.adapter.item.AdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.databinding.DemoItemImageBinding;
import kale.commonadapter.model.DemoModel;


/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ImageItem implements AdapterItem<DemoModel> {

    private int mOldImageUrl = 0;

    /**
     * @Tips item的layout命名，应该采用所处界面名+item+类型名
     * 比如这个界面叫做demo，这个item是图片，那么就叫做:demo_item_image
     * 
     * 当然，这仅仅是一个建议
     */
    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_image;
    }

    private DemoItemImageBinding b;

    /**
     * @Tips 这里的绑定如果觉得麻烦可以用dataBinding做处理，可以不用写findViewById
     * @param root 通过layout文件生成的根布局
     */
    @Override
    public void bindViews(View root) {
        b = DataBindingUtil.bind(root);
    }

    @Override
    public void setViews() {
        Log.d(ImageItem.class.getSimpleName(), "setViews--------->");
    }

    /**
     * @Tips 优化小技巧：对于图片这样的对象，我们先判断要加载的图片是不是之前的图片，如果是就不重复加载了.
     * 这里为了演示方便没从网络加图，所以url是用int标识的，一般情况下都是用string标识
     *
     * 这里仅仅是用图片做个说明，你完全可以在textview显示文字前判断一下要显示的文字和已经显示的文字是否不同
     */
    @Override
    public void handleData(DemoModel model, int position) {
        int drawableId = Integer.valueOf(model.content);
        if (mOldImageUrl == 0 && mOldImageUrl != drawableId) {
            Log.d(ImageItem.class.getSimpleName(), "update image--------->");

            b.imageView.setImageResource(drawableId); // load image
            mOldImageUrl = drawableId;
        }
    }


}
