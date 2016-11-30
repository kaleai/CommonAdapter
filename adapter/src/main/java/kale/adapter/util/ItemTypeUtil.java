package kale.adapter.util;

import android.support.annotation.VisibleForTesting;

import java.util.HashMap;

/**
 * @author Jack Tony
 * @date 2015/8/29
 */
@VisibleForTesting
/*package*/public class ItemTypeUtil {

//    private SparseArray<Object> typeSArr = new SparseArray<>();
//
//    private List<Object> typeList = new ArrayList<>();
//
//   /**
//     * @param type item的类型
//     * @return 通过object类型的type来得到int类型的type
//     *//*
//    public int getIntType(Object type) {
//        int index = typeSArr.indexOfValue(type);
//        if (index == -1) {
//            index = typeSArr.size();
//            // 如果没用这个type，就存入这个type
//            typeSArr.put(index, type);
//        }
//        return index;
//    }
//
//    /**
//     * @param type item的类型
//     * @return 通过object类型的type来得到int类型的type
//     */
//    public int getIntType(Object type) {
//        int index = typeList.indexOf(type);
//        if (index == -1) {
//            index = typeList.size();
//            // 如果没用这个type，就存入这个type
//            typeList.add(index, type);
//        }
//        return index;
//    }

    private HashMap<Object, Integer> typePool;

    public void setTypePool(HashMap<Object, Integer> typePool) {
        this.typePool = typePool;
    }

    /**
     * @param type item的类型
     * @return 通过object类型的type来得到int类型的type
     */
    public int getIntType(Object type) {
        if (typePool == null)
            typePool = new HashMap<>();
        if (!typePool.containsKey(type)) {
            typePool.put(type, typePool.size());
        }
        return typePool.get(type);
    }
}
