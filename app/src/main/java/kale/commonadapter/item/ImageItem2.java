package kale.commonadapter.item;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import kale.adapter.item.BaseAdapterItem;
import kale.commonadapter.R;

/**
 * @Tips 继承自{@link BaseAdapterItem}后就可以用一些很方便的方法了。
 * 如果以后不想用继承，那么可以相当灵活的变成{@link kale.adapter.item.AdapterItem}的实现。
 */
public class ImageItem2 extends BaseAdapterItem<Integer> {

    private int mOldImageUrl = 0;

    private ImageView mImageView;

    private Activity mActivity;

    private View.OnClickListener mListener;

    /**
     * @param activity 可以通过构造方法得到Activity的引用，然后就可以做很多事情了。
     * @param listener 一般的点击事件可以在内部处理，如果需要通知到外部的事件。可以通过构造函数传入监听器，
     *                 或者是通过public方法set监听器。
     * @Tips 通过构造方法可以做很多事情
     */
    public ImageItem2(Activity activity, View.OnClickListener listener) {
        mActivity = activity;
        mListener = listener;
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
                mListener.onClick(v);
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


}
