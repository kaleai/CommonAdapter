package kale.commonadapter.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/**
 * @author Kale
 * @date 2016/3/16
 */
public class OnItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private AdapterView.OnItemClickListener mListener;

    private GestureDetector mGestureDetector;

    public OnItemClickListener(Context context, AdapterView.OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            int position = view.getChildAdapterPosition(childView);
            mListener.onItemClick(null, childView, position, position);
            return false; // 如果是false，那么item和外面的recycleView都能捕获点击事件
        }
        return false;
    }

}
