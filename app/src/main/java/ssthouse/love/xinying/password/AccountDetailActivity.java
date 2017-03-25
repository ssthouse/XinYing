package ssthouse.love.xinying.password;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import io.realm.Realm;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseActivity;
import ssthouse.love.xinying.password.bean.AccountBean;
import ssthouse.love.xinying.password.bean.AccountChangeEvent;
import ssthouse.love.xinying.password.dao.AccountDao;
import ssthouse.love.xinying.utils.ToastUtil;
import timber.log.Timber;

/**
 * Created by ssthouse on 23/03/2017.
 */

public class AccountDetailActivity extends BaseActivity {

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

    @Bind(R.id.id_btn_delete)
    TextView mBtnDelete;

    private int accountBeanId = -1;
    private static final String KEY_ACCOUNT_KEY = "accountKey";
    public static final int DEFAULT_ACCOUNT_BEAN_ID = -1;
    private AccountBean mAccountBean;

    public static void start(Context context, int accountBeanId) {
        Intent intent = new Intent(context, AccountDetailActivity.class);
        intent.putExtra(KEY_ACCOUNT_KEY, accountBeanId);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        accountBeanId = getIntent().getIntExtra(KEY_ACCOUNT_KEY, DEFAULT_ACCOUNT_BEAN_ID);

        // 新建 account
        if (accountBeanId == DEFAULT_ACCOUNT_BEAN_ID) {
            mAccountBean = new AccountBean();
            mBtnDelete.setVisibility(View.GONE);
            return;
        }

        mAccountBean = new AccountDao().getAccountBean(accountBeanId);
        // 编辑account
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // remove the account bean from database
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        mAccountBean.deleteFromRealm();
                        ToastUtil.show(AccountDetailActivity.this, "成功移除账号 :)");
                        EventBus.getDefault().post(new AccountChangeEvent());
                        finish();
                    }
                });
            }
        });

        // 填充数据
        mEtServiceName.setText(mAccountBean.getServiceName());
        mEtDescription.setText(mAccountBean.getDescription());
        mEtUsername.setText(mAccountBean.getUsername());
        mEtPassword.setText(mAccountBean.getPassword());
    }

    @Override
    public int getContentView() {
        return R.layout.activity_account_detail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.id_action_save:
                if (!isFormValid()) {
                    ToastUtil.show(this, "请输入完整信息");
                    return super.onOptionsItemSelected(item);
                }
                saveAccountBean();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAccountBean() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (accountBeanId == DEFAULT_ACCOUNT_BEAN_ID) {
                    AccountBean accountBean = fillInAccountBean();
                    accountBean.setId(AccountDao.getNextId());
                    realm.copyToRealm(accountBean);
                } else {
                    realm.copyToRealmOrUpdate(fillInAccountBean());
                }
                ToastUtil.show(AccountDetailActivity.this, "保存成功");
                EventBus.getDefault().post(new AccountChangeEvent());
                finish();
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
        Timber.e(mAccountBean.getServiceName() + mAccountBean.getDescription() + mAccountBean.getUsername() + mAccountBean.getPassword());
        return mAccountBean;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
