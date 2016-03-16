package kale.commonadapter;

import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import kale.adapter.CommonRcvAdapter;
import kale.adapter.ExRcvAdapterWrapper;
import kale.adapter.item.AdapterItem;
import kale.adapter.util.rcv.RcvOnItemClickListener;
import kale.commonadapter.item.ButtonItem;
import kale.commonadapter.item.ImageItem;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.model.DemoModel;
import kale.commonadapter.util.DataManager;
import kale.commonadapter.util.Util;

/**
 * @author Kale
 * @date 2016/3/16
 */
public class RcvHeaderTestActivity extends AppCompatActivity {

    private ObservableArrayList<DemoModel> data = new ObservableArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = new RecyclerView(this);
        Util.setContentView(this, recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

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

        final ExRcvAdapterWrapper<CommonRcvAdapter<DemoModel>> wrapper = new ExRcvAdapterWrapper<>(adapter, layoutManager);
        Button header = new Button(this);
        header.setText("Header");
        wrapper.setHeaderView(header);
        
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
