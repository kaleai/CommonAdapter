package kale.adapter.util;

import java.util.ArrayList;

/**
 * Created by JWBlue.Liu on 15/12/1.
 */
public class AdapterItemHelper {
    private static final int NONE = -1;
    private ArrayList<Object> typeSArr = new ArrayList<>();

    /**
     * @param type item的类型
     * @return 通过object类型的type来得到int类型的type
     */
    public int getIntType(Object type) {
        int index = typeSArr.indexOf(type);
        if (index == NONE) {
            typeSArr.add(type);
        }
        return index;
    }
}
