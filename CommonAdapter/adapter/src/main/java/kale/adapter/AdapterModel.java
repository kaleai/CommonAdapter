package kale.adapter;

/**
 * @author Jack Tony
 * @date 2015/5/16
 * 数据model需要实现此接口，这个mode是在list中的
 */
public interface AdapterModel {

    /**
     * 得到数据的类型数
     * @return 有多少种数据的类型（item的类型）
     */
    public int getDataTypeCount();

    /**
     * 返回每种数据类型的标识.<br>
     * 需要注意的是：这个的范围只能是[0 , typeCount - 1]
     * @return 每种数据类型的标识
     */
    public int getDataType();
}
