package kale.commonadapter.item;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import kale.adapter.AdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.model.ImageModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ImageItem implements AdapterItem<ImageModel> {
    private Context mContext;
    private ImageView mIcon;
    private int mPosition;

    public ImageItem(Context context) {
        mContext = context;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_image;
    }

    @Override
    public void onBindViews(View root) {
        mIcon = (ImageView) root;
    }

    @Override
    public void onSetViews() {
        Log.d(ImageItem.class.getSimpleName(), "onSetViews--------->");
        mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "ImageItem pos = " + mPosition, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @tips 优化小技巧：对于图片这样的对象，我们先判断要加载的图片是不是之前的图片，如果是就不重复加载了
     * 这里为了演示方便没从网络加图，所以url是用int标识的，一般情况下都是用string标识
     * 
     * 这里仅仅是用图片做个说明，你完全可以在textview显示文字前判断一下要显示的文字和已经显示的文字是否不同
     */
    @Override
    public void onUpdateViews(ImageModel model, int position) {
        mPosition = position;
        Glide.with(mContext)
                .load(model.imgUrl)
                .centerCrop()
                .placeholder(android.R.drawable.sym_call_incoming)
                .crossFade()
                .into(mIcon);
    }

}
