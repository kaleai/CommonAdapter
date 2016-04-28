package kale.commonadapter;

import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final RecyclerView recyclerView = new RecyclerView(this);
        LayoutUtil.setContentView(this, recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 2);
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

        final RcvAdapterWrapper wrapper = new RcvAdapterWrapper(adapter, layoutManager1);
        
        Button header = new Button(this);
        header.setText("Header");
        
        Button footer = new Button(this);
        footer.setText("footer");
        
        wrapper.setHeaderView(header);
        wrapper.setFooterView(footer);
        
        recyclerView.setAdapter(wrapper);

        recyclerView.addOnItemTouchListener(new RcvOnItemClickListener(this, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position - wrapper.getHeaderCount();
                if (position >= 0) {
                    data.remove(position);
                }
            }
        }));

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                data.clear();
                data.addAll(DataManager.loadData(getBaseContext(),10));
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //添加菜单项
        MenuItem add = menu.add(0, 0, 0, "add");
        add.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
        } else {
            return super.onOptionsItemSelected(item);
        } 
    }
}
