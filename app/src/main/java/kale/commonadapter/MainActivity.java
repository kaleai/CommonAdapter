package kale.commonadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViewById(R.id.list_view_btn).setOnClickListener(this);
        findViewById(R.id.rcv_btn).setOnClickListener(this);
        findViewById(R.id.rcv_btn2).setOnClickListener(this);
        findViewById(R.id.viewpager_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class clz = null;
        switch (v.getId()) {
            case R.id.list_view_btn:
                clz = ListViewTestActivity.class;
                break;
            case R.id.rcv_btn:
                clz = RcvTestActivity.class;
                break;
            case R.id.rcv_btn2:
                clz = HeaderFooterTestActivity.class;
                break;
            case R.id.viewpager_btn:
                clz = ViewPagerTestActivity.class;
                break;
        }
        startActivity(new Intent(this, clz));
    }
}
