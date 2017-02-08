package kale.commonadapter;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import kale.adapter.CommonRcvAdapter;
import kale.adapter.item.AdapterItem;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.model.DemoModel;
import kale.commonadapter.util.DataManager;
import kale.commonadapter.util.LayoutUtil;

/**
 * @author Kale
 * @date 2017/2/8
 */
public class DiffRcvActivity extends AppCompatActivity {

    private static final String TAG = "DiffRcvActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = new RecyclerView(this);
        LayoutUtil.setContentView(this, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final DiffRcvAdapter<DemoModel> adapter = new DiffRcvAdapter<DemoModel>(DataManager.loadData(this, 3)) {
            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new TextItem();
            }

            @Override
            protected boolean isContentSame(DemoModel oldItemData, DemoModel newItemData) {
                return oldItemData.content.equals(newItemData.content);
            }
        };
        
        recyclerView.setAdapter(adapter);

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setData(DataManager.loadData(DiffRcvActivity.this, 3));
            }
        }, 1000);
    }

    public static abstract class DiffRcvAdapter<T> extends CommonRcvAdapter<T> {

        DiffRcvAdapter(@Nullable List<T> data) {
            super(data);
        }

        @Override
        public void setData(@NonNull final List<T> data) {
            DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return getItemCount();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                /**
                 * 检测是否是相同的item，这里暂时通过位置判断
                 */
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    boolean result = oldItemPosition == newItemPosition;
                    Log.d(TAG, "areItemsTheSame: " + result);
                    return result;
                }

                /**
                 * 检测是否是相同的数据
                 * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
                 */
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    boolean result = isContentSame(getData().get(oldItemPosition), data.get(newItemPosition));
                    Log.d(TAG, "areContentsTheSame: " + result);
                    return result;
                }
            }).dispatchUpdatesTo(this);
            super.setData(data);

        }

        protected abstract boolean isContentSame(T oldItemData, T newItemData);
    }

}
