package kale.commonadapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kale.adapter.base.AdapterItem;
import kale.adapter.base.CommonAdapter;
import kale.adapter.recycler.CommonRcvAdapter;
import kale.adapter.recycler.RcvAdapterItem;
import kale.commonadapter.item.ButtonItem;
import kale.commonadapter.item.ImageItem;
import kale.commonadapter.item.RcvButtonItem;
import kale.commonadapter.item.RcvImageItem;
import kale.commonadapter.item.RcvTextItem;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.model.TestModel;


public class MainActivity extends ActionBarActivity {

    private final String TAG = getClass().getSimpleName();

    private ListView listView;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final List<TestModel> data = loadData();
        getSupportActionBar().setTitle("ListView的效果");

        listView = (ListView) findViewById(R.id.listView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        addDataToListView(data);
        addDataToRecyclerView(data);

        Button showListViewBtn = (Button) findViewById(R.id.showListView_button);

        showListViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("ListView的效果");
                listView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });

        Button showRecyclerViewBtn = (Button) findViewById(R.id.showRecyclerView_button);
        showRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("RecyclerView的效果");
                recyclerView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        });

    }
    


    private void addDataToListView(List<TestModel> data) {
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
    }

    private void addDataToRecyclerView(List<TestModel> data) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CommonRcvAdapter<TestModel>(data) {
            @NonNull
            @Override
            protected RcvAdapterItem initItemView(Context context, int type) {
                RcvAdapterItem item;
                switch (type) {
                    case TestModel.TYPE_TEXT:
                        item = new RcvTextItem(context, R.layout.text_adapter_item);
                        break;
                    case TestModel.TYPE_BUTTON:
                        item = new RcvButtonItem(context, R.layout.button_adapter_item);
                        break;
                    case TestModel.TYPE_IMAGE:
                        item = new RcvImageItem(context, R.layout.image_adapter_item);
                        break;
                    default:
                        item = new RcvTextItem(context, R.layout.text_adapter_item);
                }
                return item;
            }
        });
    }


    /**
     * 模拟加载数据的操作
     */
    private List<TestModel> loadData() {
        List<TestModel> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int type = (int) (Math.random() * 3);
            //Log.d(TAG, "type = " + type);
            TestModel model = new TestModel();
            switch (type) {
                case 0:
                    model.setType("text");
                    model.setContent("第一种布局");
                    break;
                case 1:
                    model.setType("button");
                    model.setContent("第二种布局");
                    break;
                case 2:
                    model.setType("image");
                    model.setContent(String.valueOf(R.drawable.kale));
                    break;
                default:
            }
            list.add(model);
        }
        return list;
    }

}
