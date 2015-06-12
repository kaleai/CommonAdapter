package kale.commonadapter.model;


import kale.adapter.AdapterModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class TestModel implements AdapterModel {

    /**
     * 定义类型标识的时候，必须是从0到type数-1，否则会数组越界.
     * 这里我们是三种类型，所以类型的标识就是0~2
     */
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_BUTTON = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_NORMAL = TYPE_TEXT;

    private String content;

    private String type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

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
    public int getDataType() {
        switch (type) {
            case "button":
                return TYPE_BUTTON;
            case "image":
                return TYPE_IMAGE;
            case "text":
                return TYPE_TEXT;
            default:
                return TYPE_NORMAL;
        }
    }

}
