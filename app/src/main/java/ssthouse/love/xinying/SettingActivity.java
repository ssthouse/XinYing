package ssthouse.love.xinying;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import butterknife.Bind;
import ssthouse.love.xinying.base.BaseActivity;

/**
 * Created by ssthouse on 16/9/4.
 */
public class SettingActivity extends BaseActivity {

    @Bind(R.id.id_tb)
    Toolbar toolbar;

    @Bind(R.id.id_lv)
    ListView listView;

    @Override
    public void init() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Setting");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
