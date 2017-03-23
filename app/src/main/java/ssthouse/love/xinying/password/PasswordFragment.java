package ssthouse.love.xinying.password;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.RealmResults;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.password.bean.AccountBean;
import ssthouse.love.xinying.password.dao.AccountDao;

/**
 * Created by ssthouse on 23/03/2017.
 */

public class PasswordFragment extends BaseFragment {

    // 用于保存初始从数据库中获取数据
    private RealmResults<AccountBean> mAccountBeanList;

    // 用于filter后显示在listview中的数据
    private List<AccountBean> mDisplayAccountBeanList;

    private AccountDao mAccountDao;

    @Bind(R.id.id_et_search_account)
    EditText mEtSearchAccount;

    @Bind(R.id.id_lv_account)
    ListView mLvAccount;

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
        mAccountDao = new AccountDao();
        mAccountBeanList = mAccountDao.getAllAccounts();
        mDisplayAccountBeanList = new ArrayList<>();
        mDisplayAccountBeanList.addAll(mAccountBeanList);

        mEtSearchAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO filter the display list
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLvAccount.setAdapter(mAccountAdapter);
    }

    private BaseAdapter mAccountAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mDisplayAccountBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDisplayAccountBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_account, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.ivLogo = (ImageView) convertView.findViewById(R.id.id_iv_service_logo);
                viewHolder.tvServiceName = (TextView) convertView.findViewById(R.id.id_tv_service_name);
                viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.id_tv_description);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvDescription.setText(mDisplayAccountBeanList.get(position).getDescription());
            viewHolder.tvServiceName.setText(mDisplayAccountBeanList.get(position).getServiceName());
            return convertView;
        }
    };

    private class ViewHolder {
        ImageView ivLogo;
        TextView tvServiceName;
        TextView tvDescription;
    }
}
