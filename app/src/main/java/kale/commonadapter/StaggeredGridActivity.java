package kale.commonadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.recycler.CommonRcvAdapter;
import kale.commonadapter.item.StaggeredGridImageItem;
import kale.commonadapter.item.StaggeredGridButtonItem;
import kale.commonadapter.item.StaggeredGridTextItem;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.model.DemoModel;

public class StaggeredGridActivity extends AppCompatActivity {

	private final String TAG = getClass().getSimpleName();

	private RecyclerView recyclerView;
	private RcvAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_activity_recyclerview);

		getSupportActionBar().setTitle("ListView的效果");

		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView.setHasFixedSize(true);
		addDataToRecyclerView();

	}

	private void addDataToRecyclerView() {

		final List<DemoModel> data = loadData();

//		 GridLayoutManager layoutManager = new GridLayoutManager(this, 2,
//		 GridLayoutManager.VERTICAL, false);
//		LinearLayoutManager layoutManager = new LinearLayoutManager(this,
//		        LinearLayoutManager.VERTICAL, false);
		StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
		mAdapter = new RcvAdapter(data);
		recyclerView.setAdapter(new RcvAdapter(data));
	}

	class RcvAdapter extends CommonRcvAdapter<DemoModel> {

		protected RcvAdapter(List<DemoModel> data) {
			super(data);
		}

		@NonNull
		@Override
		protected AdapterItem<DemoModel> initItemView(Object type) {

			switch ((String) type) {
				case "text":
					return new StaggeredGridTextItem();
				case "button":
					return new StaggeredGridButtonItem();
				case "image":
					return new StaggeredGridImageItem();
				default:
					Log.d(TAG, "default-----------");
					return new TextItem();
			}
		}
	}

	/**
	 * 模拟加载数据的操作
	 */
	private List<DemoModel> loadData() {
		List<DemoModel> list = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			int type = (int) (Math.random() * 3);
			// Log.d(TAG, "type = " + type);
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
			Log.d(TAG, "type = " + tempModel.type);
		}

		return list;
	}
}
