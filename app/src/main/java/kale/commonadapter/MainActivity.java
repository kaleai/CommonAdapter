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

import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.DefaultItem;
import kale.adapter.abs.CommonLvAdapter;
import kale.adapter.recycler.CommonRcvAdapter;
import kale.adapter.util.BaseModel;
import kale.commonadapter.item.ButtonItem;
import kale.commonadapter.item.ImageItem;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.util.DataManager;

public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    private ListView listView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);

        listView = (ListView) findViewById(R.id.listView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Button showListViewBtn = (Button) findViewById(R.id.showListView_button);

        showListViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("ListView的效果");
                listView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                
                if (listView.getAdapter() == null) {
                    addDataToListView(DataManager.loadData(getBaseContext()));
                } else {
                    ((CommonLvAdapter) listView.getAdapter()).updateData(DataManager.loadData(getBaseContext()));
                } 
            }
        });

        Button showRecyclerViewBtn = (Button) findViewById(R.id.showRecyclerView_button);
        showRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("RecyclerView的效果");
                recyclerView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);

                if (recyclerView.getAdapter() == null) {
                    addDataToRecyclerView(DataManager.loadData(getBaseContext()));
                } else {
                    if (recyclerView.getAdapter() instanceof CommonRcvAdapter) {
                        ((CommonRcvAdapter<BaseModel>) recyclerView.getAdapter()).updateData(DataManager.loadData(getBaseContext()));
                    }
                }
            }
        });
    }

    private void addDataToListView(List<BaseModel> data) {
        listView.setAdapter(new CommonLvAdapter<BaseModel>(data, 3) {
            @NonNull
            @Override
            public AdapterItem<BaseModel> getItemView(Object type) {
                Log.d(TAG, "getItem " + type + " view");
                return initItem(type);
            }
        });
    }

    private void addDataToRecyclerView(List<BaseModel> data) {
        //GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CommonRcvAdapter<BaseModel>(data) {
            @NonNull
            @Override
            public AdapterItem<BaseModel> getAdapterItem(Object type) {
                Log.d(TAG, "getItem " + type + " view");
                return initItem(type);
            }
        });
    }
    
    private AdapterItem initItem(Object type) {
       switch (type.toString()) {
            case "TextModel":
                return new TextItem();
            case "ButtonModel":
                return new ButtonItem();
            case "ImageModel":
                return new ImageItem(getBaseContext());
            default:
                Log.e(TAG, "No default item");
                return new DefaultItem();
        }
    }
    
}
