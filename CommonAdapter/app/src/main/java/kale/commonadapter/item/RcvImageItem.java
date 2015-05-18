package kale.commonadapter.item;

import android.content.Context;
import android.widget.ImageView;

import kale.adapter.recycler.RcvAdapterItem;
import kale.commonadapter.R;
import kale.commonadapter.model.TestModel;

/**
 * @author Jack Tony
 * @date 2015/5/18
 */
public class RcvImageItem extends RcvAdapterItem<TestModel> {

    public RcvImageItem(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    @Override
    public void setViews(TestModel data, int position) {
        ImageView imageView = getView(R.id.imageView);
        imageView.setImageResource(Integer.parseInt(data.getContent()));
    }
}
