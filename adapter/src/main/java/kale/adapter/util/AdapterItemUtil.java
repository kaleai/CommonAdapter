package kale.adapter.util;

import java.util.LinkedHashMap;
import java.util.Map;

import kale.adapter.AdapterItem;
import kale.adapter.base.BaseCommonAdapter;

/**
 * @author Jack Tony
 * @date 2015/8/29
 */
public class AdapterItemUtil<T> {

    // (key - value) is (type - item)
    private Map<Object, AdapterItem<T>> mItemMap = new LinkedHashMap<>();

    /**
     * 根据相应的类型得到item对象
     *
     * @param type item的类型
     */
    public AdapterItem<T> getItemByType(Object type,BaseCommonAdapter<T> adapter) {
        AdapterItem<T> item = mItemMap.get(type);
        if (item == null) {
            item = adapter.getItemView(type);
            mItemMap.put(type, item);
        }
        return item;
    }

    /**
     * @param type item的类型
     *
     * @return 通过object类型的type来得到int类型的type
     */
    public int getRealType(Object type) {
        int index = 0;
        for (Map.Entry<Object, AdapterItem<T>> entry : mItemMap.entrySet()) {
            if (entry.getKey() == type) {
                break;
            }
            index++;
        }
        return index;
    }
}
