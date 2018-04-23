package kale.commonadapter.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import kale.adapter.item.AdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.model.DemoModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ImageItem implements AdapterItem<DemoModel> {

    private int mOldImageUrl = 0;

    private ImageView mIv;

    private ImageItemCallback mCallback;

    public ImageItem() {
    }

    /**
     * @param callback 一般的点击事件可以在内部处理，如果需要通知到外部的事件。可以通过构造函数传入监听器，
     *                 或者是通过public方法set监听器。
     */
    public ImageItem(ImageItemCallback callback) {
        mCallback = callback;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_image;
    }

    @Override
    public void bindViews(@NonNull View root) {
        mIv = (ImageView) root;
    }

    @Override
    public void setViews() {
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "is clicked", Toast.LENGTH_SHORT).show();
                if (mCallback != null) {
                    mCallback.onImageClick(view);
                }
            }
        });
    }

    @Override
    public void handleData(DemoModel model, int position) {
        int drawableId = Integer.valueOf(model.content);
        // 我们先判断要加载的图片是不是之前的图片，如果是就不重复加载了
        if (mOldImageUrl == 0 && mOldImageUrl != drawableId) {
            mIv.setImageResource(drawableId); // load image
            mOldImageUrl = drawableId;
        }
    }

    /**
     * 作为item的回调可以放一个内部类在这里，然后从外部传入callback的实现。
     *
     * 为什么做空实现，而不是一个接口呢？如果item被多个页面复用了，而且多个页面接收到的回调是不同的，
     * 那么别的页面可以针对性的实现某方法，如onImageClick()，不用全部实现。
     */
    public static class ImageItemCallback {

        /**
         * 这个名字一定要取得和item中具体的事务有关，不要叫的太抽象。比如onClick就不好了。
         */
        public void onImageClick(View view) {
            // do nothing
        }

        public void onViewChanged() {
            // do nothing
        }

        public void otherListener() {
            // do noting
        }
    }

}
