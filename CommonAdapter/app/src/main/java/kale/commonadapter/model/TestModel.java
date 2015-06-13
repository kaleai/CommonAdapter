package kale.commonadapter.model;


import kale.adapter.AdapterModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class TestModel implements AdapterModel {

    public String content;

    public String type;

    /**
     * 返回数据的类型数
     */
    @Override
    public int getDataTypeCount() {
        return 3;
    }

    /**
     * 这个model中决定数据类型的字段
     */
    @Override
    public Object getDataType() {
        return type;
    }

}
