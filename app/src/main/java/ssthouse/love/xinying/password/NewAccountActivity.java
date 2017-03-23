package ssthouse.love.xinying.password;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseActivity;

/**
 * Created by ssthouse on 23/03/2017.
 */

public class NewAccountActivity extends BaseActivity {

    @Bind(R.id.id_tb)
    Toolbar mToolbar;

    public static void start(Context context) {
        Intent intent = new Intent(context, NewAccountActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_new_account;
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
