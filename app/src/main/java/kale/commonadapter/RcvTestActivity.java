package kale.commonadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

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
 *
 * 这里展示adapter和item类型相同和不同时的各种处理方案
 *
 * 注意：
 * 第二次更新数据的时候需要调用{@link IAdapter#setData(List)}后，
 * 再{@link IAdapter#notifyDataSetChanged()}
 */
public class RcvTestActivity extends AppCompatActivity {

    private static final String TAG = "RcvTestActivity";

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = new RecyclerView(this);
        LayoutUtil.setContentView(this, mRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setRecycleChildrenOnDetach(true);
        mRecyclerView.setLayoutManager(layoutManager);

        // 放一个默认数据
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
                data.addAll(DataManager.loadData(getBaseContext()));
                Toast.makeText(RcvTestActivity.this, "refresh", Toast.LENGTH_SHORT).show();
                mRecyclerView.getAdapter().notifyDataSetChanged(); // 通知数据刷新
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
