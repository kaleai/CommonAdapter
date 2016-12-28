package kale.commonadapter;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import kale.adapter.CommonAdapter;
import kale.adapter.item.AdapterItem;
import kale.adapter.util.IAdapter;
import kale.commonadapter.item.ButtonItem;
import kale.commonadapter.item.ImageItem;
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
public class ListViewActivity extends AppCompatActivity {

    private ListView mListView;

    private List<DemoModel> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = new ListView(this);
        LayoutUtil.setContentView(this, mListView);

        mData = DataManager.loadData(getBaseContext());
        mListView.setAdapter(multiType(mData));
    }

    /**
     * 多种类型的type
     */
    private CommonAdapter<DemoModel> multiType(List<DemoModel> data) {
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
                    default:
                        return new ImageItem();
                }
            }
        };
    }

    /**
     * 一种类型的type
     */
    private CommonAdapter<DemoModel> singleType(List<DemoModel> data) {
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
     * 这里的adapter的类型是{@link DemoModel}，但item的类型是Integer.
     * 所以需要调用{@link IAdapter#getConvertedData(Object, Object)}方法，来进行数据的转换
     *
     * 多种类型的type
     */
    private CommonAdapter<DemoModel> convertedData(List<DemoModel> data) {
        return new CommonAdapter<DemoModel>(data, 3) {

            @Override
            public Object getItemType(DemoModel demoModel) {
                return demoModel.type;
            }

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                switch (((String) type)) {
                    case "image":
                    case "text":
                        return new TextItem();
                    case "button":
                        return new ButtonItem();
                    default:
                        return new ImageItem(new ImageItem.ImageItemCallback() {
                            @Override
                            public void onImageClick(View view) {
                                Toast.makeText(ListViewActivity.this, "click", Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            }

            /**
             * 做数据的转换，这里算是数据的精细拆分
             */
            @NonNull
            @Override
            public Object getConvertedData(DemoModel data, Object type) {
                // 这样可以允许item自身的数据和list数据不同，这里item接收DemoModel，数据也是DemoModel，所以直接返回了data
                return data;
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "1").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 1, 0, "2").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 2, 0, "3").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                mListView.setAdapter(multiType(mData));
                return true;
            case 1:
                mListView.setAdapter(singleType(mData));
                return true;
            case 2:
                mListView.setAdapter(convertedData(mData));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
