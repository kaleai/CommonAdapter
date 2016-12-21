package kale.adapter.util;

import java.util.HashMap;

import android.support.annotation.VisibleForTesting;

/**
 * @author Jack Tony
 * @date 2015/8/29
 */
@VisibleForTesting
/*package*/ public class ItemTypeUtil {

    private HashMap<Object, Integer> typePool;

    public void setTypePool(HashMap<Object, Integer> typePool) {
        this.typePool = typePool;
    }

    /**
     * @param type item的类型
     * @return 通过object类型的type来得到int类型的type
     */
    public int getIntType(Object type) {
        if (typePool == null) {
            typePool = new HashMap<>();
        }
        
        if (!typePool.containsKey(type)) {
            typePool.put(type, typePool.size());
        }
        return typePool.get(type);
    }
}
