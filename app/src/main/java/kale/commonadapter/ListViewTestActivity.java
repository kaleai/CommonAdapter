package kale.commonadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import kale.adapter.CommonAdapter;
import kale.adapter.item.AdapterItem;
import kale.adapter.util.IAdapter;
import kale.commonadapter.item.ButtonItem;
import kale.commonadapter.item.ImageItem;
import kale.commonadapter.item.ImageItem2;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.model.DemoModel;
import kale.commonadapter.util.DataManager;
import kale.commonadapter.util.LayoutUtil;

/**
 * @author Kale
 * @date 2016/1/28
 * 
 * 这里展示adapter和item类型相同和不同时的各种处理方案
 */
public class ListViewTestActivity extends AppCompatActivity{

    ListView listView;
    List<DemoModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = new ListView(this);
        LayoutUtil.setContentView(this, listView);

        data = DataManager.loadData(getBaseContext());
        listView.setAdapter(test01(data));
    }

    /**
     * CommonAdapter的类型和item的类型是一致的
     * 这里的都是{@link DemoModel}
     *
     * 多种类型的type
     */
    private CommonAdapter<DemoModel> test01(List<DemoModel> data) {
        return new CommonAdapter<DemoModel>(data, 3) {

            @Override
            public Object getItemType(DemoModel demoModel) {
                return demoModel.type;
            }

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                switch (((String) type)) {
                    case "text":
                        return new TextItem();
                    case "button":
                        return new ButtonItem();
                    case "image":
                        return new ImageItem();
                    default:
                        throw new IllegalArgumentException("不合法的type");
                }
            }
        };
    }

    /**
     * CommonPagerAdapter的类型和item的类型是一致的
     * 这里的都是{@link DemoModel}
     *
     * 一种类型的type
     */
    private CommonAdapter<DemoModel> test02(List<DemoModel> data) {
        return new CommonAdapter<DemoModel>(data, 1) {

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                // 如果就一种，那么直接return一种类型的item即可。
                return new TextItem();
            }
        };
    }

    /**
     * CommonAdapter的类型和item的类型是不一致的
     * 这里的adapter的类型是{@link DemoModel}，但item的类型是Integer.
     * 所以需要调用{@link IAdapter#getConvertedData(Object, Object)}方法，来进行数据的转换
     *
     * 多种类型的type
     */
    private CommonAdapter<DemoModel> test03(List<DemoModel> data) {
        return new CommonAdapter<DemoModel>(data, 3) {

            @Override
            public Object getItemType(DemoModel demoModel) {
                return demoModel.type;
            }

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                switch (((String) type)) {
                    case "text":
                        return new TextItem();
                    case "button":
                        return new ButtonItem();
                    case "image":
                        return new ImageItem2(ListViewTestActivity.this, new ImageItem2.ImageItemCallback() {
                            @Override
                            public void onImageClick(View view) {
                                Toast.makeText(ListViewTestActivity.this, "click", Toast.LENGTH_SHORT).show();
                            }
                        });
                    default:
                        throw new IllegalArgumentException("不合法的type");
                }
            }

            /**
             * 做数据的转换，这里算是数据的精细拆分
             */
            @NonNull
            @Override
            public Object getConvertedData(DemoModel data, Object type) {
                if (type.equals("image")) {
                    return Integer.valueOf(data.content); // String -> Integer
                } else {
                    return data;
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //添加菜单项
        MenuItem oneType = menu.add(0, 0, 0, "three");
        oneType.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        //添加菜单项
        MenuItem threeType = menu.add(0, 1, 0, "one");
        threeType.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            listView.setAdapter(test01(data));
            return true;
        } else if (item.getItemId() == 1) {
            listView.setAdapter(test02(data));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
