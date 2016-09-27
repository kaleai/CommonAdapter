package kale.commonadapter;

import android.databinding.ObservableArrayList;
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
import android.view.ViewGroup;
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
import kale.commonadapter.util.RcvOnItemClickListener;

/**
 * @author Kale
 * @date 2016/3/16
 */
public class RcvHeaderFooterTestActivity extends AppCompatActivity {

    private ObservableArrayList<DemoModel> data = new ObservableArrayList<>();
    private RcvAdapterWrapper wrapper;
    private GridLayoutManager layoutManager1;
    private StaggeredGridLayoutManager layoutManager2;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = new RecyclerView(this);
        LayoutUtil.setContentView(this, recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager1 = new GridLayoutManager(this, 2);
        layoutManager2 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager1);

        data.addAll(DataManager.loadData(getBaseContext()));

        CommonRcvAdapter<DemoModel> adapter = new CommonRcvAdapter<DemoModel>(data) {
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

        wrapper = new RcvAdapterWrapper(adapter, recyclerView.getLayoutManager());

        Button header = new Button(this);
        header.setText("Header");
        header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));

        final Button footer = new Button(this);
        footer.setText("footer");

        wrapper.setHeaderView(header);

        recyclerView.setAdapter(wrapper);

        // 建议把点击事件写入item里面，在外面写会有各种各样的不可控的问题。这里仅仅是给出一个实现方案，但是不推荐使用
        recyclerView.addOnItemTouchListener(new RcvOnItemClickListener(this, new AdapterView.OnItemClickListener() {
            @Deprecated
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position - wrapper.getHeaderCount(); // 写在外面就得判断是否有头部和底部，还不能集中控制事件
                if (position >= 0) {
                    Toast.makeText(RcvHeaderFooterTestActivity.this, "pos = " + position, Toast.LENGTH_SHORT).show();
                    data.remove(position);
                }
            }
        }));

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                data.clear();
                data.addAll(DataManager.loadData(getBaseContext(), 10));
//                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.list_footer, null);
                wrapper.setFooterView(footer);
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //添加菜单项
        MenuItem add = menu.add(0, 0, 0, "add");
        add.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        
        MenuItem change = menu.add(1, 1, 0, "change");
        change.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            DemoModel model = new DemoModel();
            model.type = "text";
            model.content = "kale";
            data.add(0, model);
            return true;
        } else if (item.getItemId() == 1) {
            if (wrapper.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                recyclerView.setLayoutManager(layoutManager1);
                wrapper.setLayoutManager(layoutManager1);
            } else {
                recyclerView.setLayoutManager(layoutManager2);
                wrapper.setLayoutManager(layoutManager2);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
