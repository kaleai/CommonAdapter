# CommonAdapter

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-CommonAdapter-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1861)  

通过封装BaseAdapter和RecyclerView.Adapter得到的通用的，简易的Adapter对象。  


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
```  
dependencies {
	compile 'com.github.tianzhijiexian:CommonAdapter:1.1.4'
}    
```
### 示例     
![](./demo/demo.png)

### ListView+GridView的通用适配器——CommonAdapter    

**1. Adapter中的Item实现`AdapterItem`这个接口**    

接口的源码如下：  
```JAVA  
/**
 * adapter的所有item必须实现此接口.
 * 通过返回{@link #getLayoutResId()}来自动初始化view，之后在{@link #onBindViews(View)}中就可以初始化item的内部视图了。<br>
 */
public interface AdapterItem<T> {

    /**
     * @return item布局文件的layoutId
     */
    @LayoutRes
    int getLayoutResId();

    /**
     * 初始化views
     */
    void onBindViews(final View root);

    /**
     * 设置view的参数
     */
    void onSetViews();

    /**
     * 根据数据来设置item的内部views
     *
     * @param model    数据list内部的model
     * @param position 当前adapter调用item的位置
     */
    void onUpdateViews(T model, int position);

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
    public void onBindViews(View root) {
        textView = (TextView) root.findViewById(R.id.textView);
    }

    @Override
    public void onSetViews() { }

    @Override
    public void onUpdateViews(DemoModel model, int position) {
        textView.setText(model.content);
    }

}
```  

**2. 通过继承`CommonAdapter`来实现适配器**  
现在所需要做的只剩下继承CommonAdapter实现自己的适配器了，下面是一个简单的例子：  
```java
listView.setAdapter(new CommonAdapter<DemoModel>(data) {
    @Override
    public AdapterItem<DemoModel> getAdapterItem(Object type) {
        return new TextItem();
    }
});
```

### RecyclerView的通用适配器——CommonRcvAdapter 
1. Adapter中的每个Item需要实现`AdapterItem`这个接口（同上）  
2. 通过继承`CommonRcvAdapter`来实现适配器  

  
### 设计思路  
其实现在的效果和原本的adapter差不多，只是做了点小的重构，这种重构最终保持了和原本一样的可扩展性。下面我来分析下具体的细节：  

**1. Adapter**  
因为adapter原始的代码很多，所以如果你把adapter作为activity的内部类的话很别扭，而且如果adapter中如果有多个类型的Item，你就必须在getView()中写很多if-else语句，而且里面都是一些设置view的方法，很乱，你要更换Item的话还需要去删减代码，而现在我让adapter的代码量减少到一个方法，如果你需要更新item或者添加一个新的item你直接在initItem中返回即可，实现了可插拔化。最关键的是item现在作为一个独立的对象，内部view的设置完全可以和adapter独立出来。  

**2. AdapterItem**  
和原来方式最为不同的就是我把adapter的item作为了一个实体，这种方式借鉴了RecyclerView的ViewHolder的设计。把Item作为实体的好处有很多，就不细说了。

**3. One more thing**  
如果你是一个倾向于MVP的开发者，你完全可以把原本项目中独立的adapter变成activity的内部类，这样做增加了adapter和activity的聚合性，同时减少了项目中的众多adapter类。这样的坏处是什么呢？activity现在和adapter的聚合度高了，而现在adapter中仅仅有view，这样activity和view的聚合度也会很高。如果你认为activity是一个controler，那么请千万不要用我的做法，因为这样会让你的项目层次出现混乱。但如果你认为activity就是一个view管理对象，逻辑是写在presenter中的，那么你可以放心的用这种方式。欢迎大家来继续讨论。



## 开发者
![](https://avatars3.githubusercontent.com/u/9552155?v=3&s=460)

Jack Tony: <developer_kale@qq.com>  


## License

    Copyright 2015 Jack Tony

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 
