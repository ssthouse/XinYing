package ssthouse.love.xinying.password;

import android.text.TextUtils;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.password.bean.ChangePasswordFragmentEvent;
import ssthouse.love.xinying.password.dao.MasterKeyManager;
import ssthouse.love.xinying.utils.ToastUtil;
import timber.log.Timber;

import static ssthouse.love.xinying.password.dao.MasterKeyManager.md5;

/**
 * Created by ssthouse on 26/03/2017.
 */

public class UserVerifyFragment extends BaseFragment {

    private MasterKeyManager mKeyManager;

    @Bind(R.id.id_et_master_password)
    EditText mEtPassword;

    @OnClick(R.id.id_tv_verify)
    public void onBtnVerifyClicked() {
        if (TextUtils.isEmpty(mEtPassword.getText())) {
            ToastUtil.show(getContext(), "password should not be empty :)");
            return;
        }
        if (!md5(mEtPassword.getText().toString()).equals(mKeyManager.getMasterPassword())) {
            ToastUtil.show(getContext(), "wrong password, who are you :)");
            return;
        }
        EventBus.getDefault().post(new ChangePasswordFragmentEvent(true));
    }

    @Bind(R.id.id_et_original_password)
    EditText mEtOriginalPassword;

    @Bind(R.id.id_et_new_password)
    EditText mEtNewPassword;

    @OnClick(R.id.id_tv_ensure)
    public void onChangePasswordEnsure() {
        if (TextUtils.isEmpty(mEtOriginalPassword.getText()) ||
                !md5(mEtOriginalPassword.getText().toString()).equals(mKeyManager.getMasterPassword())) {
            ToastUtil.show(getContext(), "wrong password, who are you :)");
            return;
        }
        if (TextUtils.isEmpty(mEtNewPassword.getText())) {
            ToastUtil.show(getContext(), "what is wrong with you? ;)");
            return;
        }
        mKeyManager.setMasterPassword(mEtNewPassword.getText().toString());
        Timber.e("new password: " + mEtNewPassword.getText().toString());
        mEtOriginalPassword.getText().clear();
        mEtNewPassword.getText().clear();
        ToastUtil.show(getContext(), "change master password successfully :)");
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_user_verify;
    }

    @Override
    public void init() {
        mKeyManager = new MasterKeyManager(getContext());
    }
}
