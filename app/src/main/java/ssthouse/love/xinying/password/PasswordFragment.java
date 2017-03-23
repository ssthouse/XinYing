package ssthouse.love.xinying.password;

import butterknife.OnClick;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;

/**
 * Created by ssthouse on 23/03/2017.
 */

public class PasswordFragment extends BaseFragment {


    @OnClick(R.id.id_btn_add_account)
    public void onAddAccountClicked() {
        NewAccountActivity.start(getContext());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_password;
    }

    @Override
    public void init() {

    }
}
