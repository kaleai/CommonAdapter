package kale.commonadapter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import kale.adapter.CommonRcvAdapter;
import kale.adapter.RcvAdapterWrapper;
import kale.adapter.item.AdapterItem;
import kale.commonadapter.item.ButtonItem;
import kale.commonadapter.item.ImageItem;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.model.DemoModel;
import kale.commonadapter.util.DataManager;
import kale.commonadapter.util.LayoutUtil;
import kale.commonadapter.util.ObservableArrayList;
import kale.commonadapter.util.RcvOnItemClickListener;

/**
 * @author Kale
 * @date 2016/3/16
 */
public class HeaderFooterTestActivity extends AppCompatActivity {

    private ObservableArrayList<DemoModel> data = new ObservableArrayList<>();

    private RcvAdapterWrapper wrapper;

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private GridLayoutManager layoutManager1;

    private StaggeredGridLayoutManager layoutManager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = new RecyclerView(this);
        LayoutUtil.setContentView(this, recyclerView);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager1 = new GridLayoutManager(this, 2);
        layoutManager2 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        data.addAll(DataManager.loadData(getBaseContext()));

        final CommonRcvAdapter<DemoModel> adapter = initAdapter();

        wrapper = new RcvAdapterWrapper(adapter, recyclerView.getLayoutManager());

        final Button header = new Button(this);
        header.setText("Header\n\n (click to add)");
        header.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 300));

        final Button footer = new Button(this);
        footer.setText("footer");

        wrapper.setHeaderView(header);
        wrapper.setFooterView(null);

        final Button empty = new Button(this);
        empty.setBackgroundColor(Color.RED);
        empty.setText("empty text");
        wrapper.setEmptyView(empty, recyclerView);

        recyclerView.setAdapter(wrapper);
        
        handItemClick();

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                data.reset(DataManager.loadData(getBaseContext(),10));
                wrapper.setFooterView(footer);
            }
        }, 1000);
    }

    private void handItemClick() {
        // 建议把点击事件写入item里面，在外面写会有各种各样的不可控的问题。这里仅仅是给出一个实现方案，但是不推荐使用
        recyclerView.addOnItemTouchListener(new RcvOnItemClickListener(this,
                new AdapterView.OnItemClickListener() {
                    @Deprecated
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        position = position - wrapper.getHeaderCount();
                        if (position >= 0 && position < data.size()) {
                            Toast.makeText(HeaderFooterTestActivity.this, "pos = " + position, Toast.LENGTH_SHORT).show();
                            data.remove(position);
                        }

                        if (position == -1) {
                            // click header
                            DemoModel model = new DemoModel();
                            model.type = "text";
                            model.content = "kale";
                            data.add(0, model);
                        }
                    }
                }));
    }

    @NonNull
    private CommonRcvAdapter<DemoModel> initAdapter() {
        return new CommonRcvAdapter<DemoModel>(data) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem linear = menu.add(0, 0, 0, "L");
        linear.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        MenuItem grid = menu.add(1, 1, 0, "G");
        grid.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        MenuItem staggered = menu.add(2, 2, 0, "S");
        staggered.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            recyclerView.setLayoutManager(layoutManager);
            wrapper.setLayoutManager(layoutManager);
            return true;
        } else if (item.getItemId() == 1) {
            recyclerView.setLayoutManager(layoutManager1);
            wrapper.setLayoutManager(layoutManager1);
            return true;
        } else if (item.getItemId() == 2) {
            recyclerView.setLayoutManager(layoutManager2);
            wrapper.setLayoutManager(layoutManager2);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
