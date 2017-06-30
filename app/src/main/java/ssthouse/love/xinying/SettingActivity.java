package ssthouse.love.xinying;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import ssthouse.love.xinying.base.BaseActivity;
import ssthouse.love.xinying.utils.ToastUtil;
import ssthouse.love.xinying.vue.VueConfig;

/**
 * Created by ssthouse on 16/9/4.
 */
public class SettingActivity extends BaseActivity {

    @Bind(R.id.id_tb)
    Toolbar toolbar;

//    @Bind(R.id.id_cv_pull_my_note)
//    CardView cvPullFastNote;

    @Bind(R.id.id_et_vue_url)
    EditText etVueUrl;

    @Bind(R.id.id_tv_change_url)
    TextView tvChangeUrl;

    @Override
    public void init() {
        initToolbar();

//        cvPullFastNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FastNoteConfigUtil.getInstance(SettingActivity.this).pullFastNoteFromCloud();
//            }
//        });

        etVueUrl.setText(new VueConfig(this).getURL());

        tvChangeUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etVueUrl.getText().toString())) {
                    return;
                }
                new VueConfig(SettingActivity.this).setUrl(etVueUrl.getText().toString());
                ToastUtil.show(SettingActivity.this, "修改成功, 下次启动应用生效 :)");
            }
        });
    }

    private void initToolbar() {
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
