package kale.adapter.base;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import kale.adapter.R;


/**
 * 比较规范独立的的ViewHolder.
 * @author Jack Tony
 * 
 * @see "http://www.cnblogs.com/tianzhijiexian/p/4157889.html"
 * 
 * @date 2015/4/28
 */
public class ViewHolder {

    private SparseArray<View> viewHolder;
    private View view;

    public static  ViewHolder newInstant(View view){
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tagViewHolder);
        if (viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(R.id.tagViewHolder,viewHolder);
        }
        return viewHolder;
    }


    private ViewHolder(View view) {
        this.view = view;
        viewHolder = new SparseArray<View>();
        view.setTag(viewHolder);
    }

    public <T extends View> T get(int id) {

        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public View getConvertView() {
        return view;
    }

    public TextView getTextView(int id) {

        return get(id);
    }
    public Button getButton(int id) {

        return get(id);
    }

    public ImageView getImageView(int id) {
        return get(id);
    }

    public void setTextView(int  id,CharSequence charSequence){
        getTextView(id).setText(charSequence);
    }
}
