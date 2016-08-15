# CommonAdapter
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-CommonAdapter-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1861)
[![](https://jitpack.io/v/tianzhijiexian/CommonAdapter.svg)](https://jitpack.io/#tianzhijiexian/CommonAdapter)

通过封装BaseAdapter和RecyclerView.Adapter得到的通用、简易的Adapter对象。  

### 已解决的问题

- [x] 提升item的独立性，完美支持item被多处复用
- [x] item会根据type来做自动复用
- [x] 支持多种类型的item
- [x] 一个item仅会调用一次setViews()，避免重复建立监听器
- [x] 一个item仅会触发一次绑定视图的操作，提升效率
- [x] ​支持dataBinding和其他第三方注入框架
- [x] 支持通过item的构造方法来传入Activity对象
- [x] 支持通过item的构造方法来传入item中事件的回调
- [x] 提供了getConvertedData(data, type)方法来对item传入的数据做转换，方便拆包和提升item的复用性
- [x] 支持viewpager的正常加载模式和懒加载模式
- [x] 支持快速将ListView的适配器切换为recyclerView的适配器
- [x] 允许用viewpager的notifyDataSetChanged()来正常更新界面
- [x] 可以给recyclerView的添加头部和底部（利用了`RcvAdapterWrapper`）
- [x] 支持适配器的数据自动绑定，只用操作数据便可，adapter会自动notify界面（需要配合databinding中的`ObservableList`）
- [x] 提供了getCurrentPosition()来支持根据不同的位置选择不同item的功能

### 示例

![](./demo/ios_demo.png)

**上图是在作者的授权下引用了设计师“流浪汉国宝（QQ:515288905）”在UI中国上的作品：http://www.ui.cn/detail/149952.html**

我觉得这个设计很简洁清爽，未来可能会出这个设计的android实现。

### 添加依赖

1.在项目外层的build.gradle中添加JitPack仓库

```
repositories {
	maven {
		url "https://jitpack.io"
	}
}
```

2.在用到的项目中添加依赖  
>	compile 'com.github.tianzhijiexian:CommonAdapter:[Latest release](https://github.com/tianzhijiexian/CommonAdapter/releases)(<-click it)'  

**举例：**
```
compile 'com.github.tianzhijiexian:CommonAdapter:1.0.0'
```

### 零、重要接口

adapter的item必须实现此接口，接口源码如下：   

```java
public interface AdapterItem<T> {

    /**
     * @return item布局文件的layoutId
     */
    @LayoutRes
    int getLayoutResId();

    /**
     * 初始化views
     */
    void bindViews(final View root);

    /**
     * 设置view的参数
     */
    void setViews();

    /**
     * 根据数据来设置item的内部views
     *
     * @param model    数据list内部的model
     * @param position 当前adapter调用item的位置
     */
    void handleData(T model, int position);

}  
```

例子：  

```java
public class TextItem implements AdapterItem<DemoModel> {

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_text;
    }

    TextView textView;

    @Override
    public void bindViews(View root) {
        textView = (TextView) root.findViewById(R.id.textView);
    }

    @Override
    public void setViews() { }

    @Override
    public void handleData(DemoModel model, int position) {
        textView.setText(model.content);
    }
}
```

### 一、ListView+GridView的通用适配器——CommonAdapter

只需继承`CommonAdapter`便可实现适配器：  

```java
listView.setAdapter(new CommonAdapter<DemoModel>(list, 1) {
    public AdapterItem<DemoModel> createItem(Object type) {
        return new TextItem();
    }
});
```

### 二、RecyclerView的通用适配器——CommonRcvAdapter

通过继承`CommonRcvAdapter`来实现适配器：   

```java  	
mAdapter = new CommonRcvAdapter<DemoModel>(list) {
 public AdapterItem createItem(Object type) {
        return new TextItem();
  }
};    
```

### 三、ViewPager的通用适配器——CommonPagerAdapter   

通过继承`CommonPagerAdapter`来实现适配器：

```java
viewPager.setAdapter(new CommonPagerAdapter<DemoModel>(list) {
	public AdapterItem createItem(Object type) {
	    return new TextItem();
	}
});
```  

### 设计思路

**1. Adapter**  

如果用adapter常规写法，你会发现代码量很大，可读性低。如果adapter中有多个类型的Item，我们还得在getView()中写很多if-else语句，很乱。
而现在我让adapter的代码量减少到一个8行的内部类，如果你需要更换item只需要动一行代码，真正实现了可插拔化。最关键的是item现在作为了一个独立的对象，可以方便的进行复用。

**2. AdapterItem**  

和原来方式最为不同的一点就是我把adapter的item作为了一个实体，这种方式借鉴了RecyclerView中ViewHolder的设计。把item作为实体的好处有很多，比如复用啊，封装啊，其余的就不细说了。  

**3. 分层**  

在使用过程中，我发现如果adapter放在view层，那就会影响到view层的独立性。此外adapter中经常有很多数据处理的操作，比如通过type选择item，数据的拆包、转换等操作。于是我还是推荐把adapter放在mvp的p层，或者是mvvm的m层。通过在实际的项目中使用来看，放在m或p层的效果较好，view的复用也比较好做。


## 开发者

![](https://avatars3.githubusercontent.com/u/9552155?v=3&s=460)

Jack Tony: <developer_kale@foxmail.com>  


## License

```  
Copyright 2016-2019 Jack Tony

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
