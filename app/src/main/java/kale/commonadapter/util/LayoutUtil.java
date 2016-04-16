package kale.commonadapter.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Kale
 * @date 2016/3/16
 */
public class LayoutUtil {

    public static void setContentView(Activity activity,View view) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        activity.setContentView(view);
    }
    
}
