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
    /**
     * 保存typePool 中value 和 key
     */
    private HashMap<Integer, Object> objectHashMap = new HashMap<>();

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
            int size = typePool.size();
            typePool.put(type, size);
            objectHashMap.put(size,type);
        }
        return typePool.get(type);
    }

    /**
     *
     * @param type int类型的type
     * @return item的类型
     */
    public Object getKeyByInt(int type) {

        if (objectHashMap.containsKey(type)) {
            Object o = objectHashMap.get(type);
            return o;
        }
        return null;
    }

}
