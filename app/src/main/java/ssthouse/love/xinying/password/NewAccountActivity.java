package ssthouse.love.xinying.password;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.Realm;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseActivity;
import ssthouse.love.xinying.password.bean.AccountBean;
import ssthouse.love.xinying.utils.ToastUtil;

/**
 * Created by ssthouse on 23/03/2017.
 */

public class NewAccountActivity extends BaseActivity {

    @Bind(R.id.id_tb)
    Toolbar mToolbar;

    @Bind(R.id.id_et_service_name)
    EditText mEtServiceName;

    @Bind(R.id.id_et_description)
    EditText mEtDescription;

    @Bind(R.id.id_et_username)
    EditText mEtUsername;

    @Bind(R.id.id_et_password)
    EditText mEtPassword;

    private AccountBean mAccountBean;

    public static void start(Context context) {
        Intent intent = new Intent(context, NewAccountActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAccountBean = new AccountBean();
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

    @OnClick(R.id.id_btn_ensure)
    public void onBtnEnsureClicked() {
        if (!isFormValid()) {
            ToastUtil.show(this, "请输入完整信息");
        }
        //TODO: 赋值给一个新的
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(fillInAccountBean());
                ToastUtil.show(NewAccountActivity.this, "保存成功");
            }
        });
    }

    private boolean isFormValid() {
        EditText[] formArray = new EditText[]{mEtServiceName, mEtDescription, mEtUsername, mEtPassword};
        for (EditText et : formArray) {
            if (TextUtils.isEmpty(et.getText().toString())) {
                return false;
            }
        }
        return true;
    }

    private AccountBean fillInAccountBean() {
        if (mAccountBean == null) {
            mAccountBean = new AccountBean();
        }
        mAccountBean.setServiceName(mEtServiceName.getText().toString());
        mAccountBean.setDescription(mEtDescription.getText().toString());
        mAccountBean.setUsername(mEtUsername.getText().toString());
        mAccountBean.setPassword(mEtPassword.getText().toString());
        return mAccountBean;
    }
}
