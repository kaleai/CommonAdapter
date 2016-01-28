package kale.commonadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import java.util.List;

import kale.adapter.CommonPagerAdapter;
import kale.adapter.item.AdapterItem;
import kale.commonadapter.item.ButtonItem;
import kale.commonadapter.item.ImageItem;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.model.DemoModel;
import kale.commonadapter.util.DataManager;

/**
 * @author Kale
 * @date 2016/1/26
 *
 * 这里演示的是viewpager的适配器.
 * 有懒加载和正常加载两种的情况.
 */
@Deprecated
public class ViewPagerTestActivity extends AppCompatActivity {

    // TODO: 2016/1/28 这里还有一些问题，正在处理。目前不推荐用viewpager的适配器。 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPager viewPager = new ViewPager(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(params);
        setContentView(viewPager);

        final List<DemoModel> data = DataManager.loadData(getBaseContext());

        viewPager.setAdapter(test01(data));
    }

    /**
     * 懒加载
     */
    private CommonPagerAdapter<DemoModel> test01(List<DemoModel> data) {
        return new CommonPagerAdapter<DemoModel>(data) {

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

    /**
     * 正常加载
     */
    private CommonPagerAdapter<DemoModel> test02(List<DemoModel> data) {
        return null;
    }

}
