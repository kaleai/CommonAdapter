package kale.commonadapter.item;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import kale.commonadapter.util.BaseAdapterItem;
import kale.commonadapter.R;

/**
 * @Tips 继承自{@link BaseAdapterItem}后就可以用一些很方便的方法了。
 * 如果以后不想用继承，那么可以相当灵活的变成{@link kale.adapter.item.AdapterItem}的实现。
 */
public class ImageItem2 extends BaseAdapterItem<Integer> {

    private int mOldImageUrl = 0;

    private ImageView mImageView;

    private Activity mActivity;

    private ImageItemCallback mListener;

    /**
     * @param activity 可以通过构造方法得到Activity的引用，然后就可以做很多事情了。
     *                 需要注意的是这里不应该做长期持有activity的操作，item中应该避免对activity做异步处理。
     * @param callback 一般的点击事件可以在内部处理，如果需要通知到外部的事件。可以通过构造函数传入监听器，
     *                 或者是通过public方法set监听器。
     * @Tips 通过构造方法可以做很多事情
     */
    public ImageItem2(Activity activity, ImageItemCallback callback) {
        mActivity = activity;
        mListener = callback;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_image;
    }

    /**
     * @Tips 可以通过getView方法得到view，比findViewById要简单很多
     */
    @Override
    protected void bindViews() {
        mImageView = getView(R.id.imageView);
        mActivity.getPackageName(); // just a example
    }

    @Override
    public void setViews() {
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onImageClick(v);
            }
        });
        Log.d(ImageItem2.class.getSimpleName(), "setViews--------->");
    }

    @Override
    public void handleData(Integer drawableId, int position) {
        if (mOldImageUrl == 0 && mOldImageUrl != drawableId) {
            mImageView.setImageResource(drawableId);
            mOldImageUrl = drawableId;
        }
    }


    /**
     * 作为item的回调可以放一个内部类在这里，然后从外部传入callback的实现。
     *
     * 为什么做空实现，而不是一个接口呢？如果item被多个页面复用了，而且多个页面接收到的回调是不同的，
     * 那么别的页面可以针对性的实现某方法即可，不用全部实现。
     */
    public static class ImageItemCallback {

        /**
         * 这个名字一定要取得和item中具体的事务有关，不要叫的太抽象。比如onClick就不好了。
         */
        public void onImageClick(View view) {
            // do nothing
        }

        public void otherListener() {
            // do noting
        }
    }


}
