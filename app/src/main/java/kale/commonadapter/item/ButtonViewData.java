package kale.commonadapter.item;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import kale.commonadapter.BR;

/**
 * @author Kale
 * @date 2016/1/5
 */
public class ButtonViewData extends BaseObservable{

    private String text;

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    @Bindable
    public String getText() {
        return text;
    }

}
