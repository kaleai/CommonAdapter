package kale.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * 比较规范独立的的ViewHolder.
 *
 * @author Jack Tony
 * @date 2015/4/28
 * @see "http://www.cnblogs.com/tianzhijiexian/p/4157889.html"
 */
public class ViewHolder {

    private SparseArray<View> viewHolderArr;

    private View mView;

    @SuppressWarnings("unchecked")
    private ViewHolder(View view) {
        mView = view;
        viewHolderArr = (SparseArray<View>) mView.getTag(R.id.tagViewHolder);
        if (viewHolderArr == null) {
            viewHolderArr = new SparseArray<>();
            mView.setTag(R.id.tagViewHolder, viewHolderArr);
        }
    }

    public static ViewHolder getInstance(View view) {
        ViewHolder instance = (ViewHolder) view.getTag();
        if (instance == null) {
            instance = new ViewHolder(view);
            view.setTag(instance);
        }
        return instance;
    }

    // I added a generic return type to reduce the casting noise in client code  
    @SuppressWarnings("unchecked")
    public <T extends View> T get(int id) {
        View childView = viewHolderArr.get(id);
        if (childView == null) {
            childView = mView.findViewById(id);
            viewHolderArr.put(id, childView);
        }
        return (T) childView;
    }

    public View getConvertView() {
        return mView;
    }
}
