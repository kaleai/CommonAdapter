# CommonAdapter
通过封装BaseAdapter和RecyclerView.Adapter得到的通用的，简易的adapter  
--
# ListView+GridView的通用适配器——CommonAdapter  
**使用步骤：**  
## 1. 让你的List中的model实现`AdapterModel`这个接口  
接口的源码如下：  
```java 
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
```  
举例：  
```java
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

```  
  
## 2. Adapter中的item需要实现`AdapterItem`这个接口  
接口的源码如下：  
```java  
/**
 * adapter的所有item必须实现此接口.
 * 通过返回layoutResId来自动初始化view，之后在initView中就可以初始化item的内部视图了。<br>
 * @author Jack Tony
 * @date 2015/5/15
 */
public interface AdapterItem<T extends AdapterModel> {

    /**
     * 返回item的布局文件id
     *
     * @return layout的id
     */
    @LayoutRes
    public int getLayoutResId();

    /**
     * 根据数据来初始化item的内部view
     *
     * @param convertView adapter中复用的view
     * @param data        数据list内部的model
     * @param position    当前adapter调用item的位置
     */
    public void initViews(View convertView, T data, int position);

}  
```  
例子：
```java
public class TextItem implements AdapterItem<TestModel> {

    @Override
    public int getLayoutResId() {
        return R.layout.text_adapter_item;
    }

    @Override
    public void initViews(View convertView, TestModel data, int position) {
        TextView textView = ViewHolder.getView(convertView, R.id.textView);
        textView.setText(data.getContent());
    }

}

```  

## 3. 通过继承CommonAdapter来实现适配器  
现在所需要做的只剩下继承CommonAdapter实现自己的适配器了，下面是一个简单的例子：  
```java
ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CommonAdapter<TestModel>(data) {

            @NonNull
            @Override
            protected AdapterItem initItemView(int type) {
                AdapterItem item;
                switch (type) {
                    case TestModel.TYPE_TEXT:
                        item = new TextItem();
                        break;
                    case TestModel.TYPE_BUTTON:
                        item = new ButtonItem();
                        break;
                    case TestModel.TYPE_IMAGE:
                        item = new ImageItem();
                        break;
                    default:
                        item = new TextItem();
                }
                return item;
            }
        });
```

# RecyclerView的通用适配器（未完成）
