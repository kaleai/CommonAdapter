package kale.commonadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.abs.CommonAdapter;
import kale.adapter.recycler.CommonRcvAdapter;
import kale.commonadapter.item.ButtonItem;
import kale.commonadapter.item.ImageItem;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.model.DemoModel;


public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private ListView listView;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);

        final List<DemoModel> data = loadData();
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
                ((CommonAdapter<DemoModel>) listView.getAdapter()).updateData(loadData());
                recyclerView.setVisibility(View.INVISIBLE);
            }
        });

        Button showRecyclerViewBtn = (Button) findViewById(R.id.showRecyclerView_button);
        showRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("RecyclerView的效果");
                recyclerView.setVisibility(View.VISIBLE);
                ((CommonRcvAdapter<DemoModel>) recyclerView.getAdapter()).updateData(loadData());
                listView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void addDataToListView(List<DemoModel> data) {
        listView.setAdapter(new CommonAdapter<DemoModel>(data, 3) {

            @Override
            public Object getItemViewType(DemoModel demoModel) {
                return demoModel.getDataType();
            }

            @NonNull
            @Override
            protected AdapterItem<DemoModel> getItemView(Object type) {
                //Log.d(TAG, "type = " + type);
                // 每个item只会被初始化一次，之后均是复用
                return initItem(type);
            }
        });
        /*listView.setAdapter(new CommonAdapter<DemoModel>(data) {
            @NonNull
            @Override
            protected AdapterItem<DemoModel> getItemView(Object type) {
                return new TextItem();
            }
        });*/
    }

    private void addDataToRecyclerView(List<DemoModel> data) {
        //GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CommonRcvAdapter<DemoModel>(data) {

            @Override
            public Object getItemViewType(DemoModel item) {
                return item.getDataType();
            }

            @NonNull
            @Override
            protected AdapterItem<DemoModel> getItemView(Object type) {
                Log.d(TAG, "type = " + type);
                return initItem(type);
            }
        });
       /* recyclerView.setAdapter(new CommonRcvAdapter<DemoModel>(data) {
            @NonNull
            @Override
            protected AdapterItem<DemoModel> getItemView(Object type) {
                return new TextItem();
            }
        });*/
    }

    private AdapterItem<DemoModel> initItem(Object type) {
        switch ((String) type) {
            case "text":
                return new TextItem();
            case "button":
                return new ButtonItem();
            case "image":
                return new ImageItem();
            default:
                Log.e(TAG, "No default item");
                return new TextItem();
        }
    }


    /**
     * 模拟加载数据的操作
     */
    private List<DemoModel> loadData() {
        List<DemoModel> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
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
            //Log.d(TAG, "type = " + tempModel.type);
        }
        return list;
    }
}
