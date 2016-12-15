package ssthouse.love.xinying;

import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import ssthouse.love.xinying.base.BaseActivity;
import ssthouse.love.xinying.note.FastNoteConfigUtil;

/**
 * Created by ssthouse on 16/9/4.
 */
public class SettingActivity extends BaseActivity {

    @Bind(R.id.id_tb)
    Toolbar toolbar;

    @Bind(R.id.id_cv_pull_my_note)
    CardView cvPullFastNote;

    @Override
    public void init() {
        initToolbar();

        cvPullFastNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastNoteConfigUtil.getInstance(SettingActivity.this).pullFastNoteFromCloud();
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
