package kale.commonadapter;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import kale.adapter.CommonRcvAdapter;
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
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerViewActivity";

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = new RecyclerView(this);
        LayoutUtil.setContentView(this, mRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setRecycleChildrenOnDetach(true);
        mRecyclerView.setLayoutManager(layoutManager);

        // 放一个默认空数据
        mRecyclerView.setAdapter(getAdapter(null));

        // 现在得到数据
        final List<DemoModel> data = DataManager.loadData(getBaseContext());
        ((IAdapter<DemoModel>) mRecyclerView.getAdapter()).setData(data); // 设置新的数据
        mRecyclerView.getAdapter().notifyDataSetChanged(); // 通知数据刷新

        loadNewData(data);
    }

    private void loadNewData(final List<DemoModel> data) {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                data.clear();
                data.addAll(DataManager.loadData(getBaseContext())); // 对data进行操作
                
                mRecyclerView.getAdapter().notifyDataSetChanged(); // 通知数据刷新
                
                Toast.makeText(RecyclerViewActivity.this, "refresh completed", Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }

    /**
     * CommonAdapter的类型和item的类型是一致的
     * 这里的都是{@link DemoModel}
     *
     * 多种类型的type
     */
    private CommonRcvAdapter<DemoModel> getAdapter(List<DemoModel> data) {
        return new CommonRcvAdapter<DemoModel>(data) {

            @Override
            public Object getItemType(DemoModel demoModel) {
                return demoModel.type;
            }

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                Log.d(TAG, "createItem " + type + " view");
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

}
