package kale.commonadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.abs.CommonAdapter;
import kale.commonadapter.item.ButtonItem;
import kale.commonadapter.item.ImageItem;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.model.DemoModel;


public class ListviewActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_listview);

        final List<DemoModel> data = loadData();
        getSupportActionBar().setTitle("ListView的效果");

        listView = (ListView) findViewById(R.id.listView);
        addDataToListView(data);
    }
    
    private void addDataToListView(List<DemoModel> data) {
        listView.setAdapter(new CommonAdapter<DemoModel>(data) {

            @NonNull
            @Override
            protected AdapterItem<DemoModel> initItemView(Object type) {
                return initItem(type);
            }
        });
    }
    
    private AdapterItem<DemoModel> initItem(Object type) {
        switch ((String)type) {
            case "text":
                return new TextItem();
            case "button":
                return new ButtonItem();
            case "image":
                return new ImageItem();
            default:
                Log.d(TAG, "default-----------");
                return new TextItem();
        }
    }


    /**
     * 模拟加载数据的操作
     */
    private List<DemoModel> loadData() {
        List<DemoModel> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int type = (int) (Math.random() * 3);
            //Log.d(TAG, "type = " + type);
            DemoModel model = new DemoModel();
            switch (type) {
                case 0:
                    model.type = "text";
                    model.content = "第一种布局";
                    break;
                case 1:
                    model.type = "button";
                    model.content = "第二种布局";
                    break;
                case 2:
                    model.type = "image";
                    model.content = String.valueOf(R.drawable.kale);
                    break;
                default:
            }
            list.add(model);
        }

        for (DemoModel tempModel : list) {
            Log.d(TAG, "type = " + tempModel.type);
        }
        
        return list;
    }
}
