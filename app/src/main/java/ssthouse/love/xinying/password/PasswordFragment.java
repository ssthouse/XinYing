package ssthouse.love.xinying.password;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.RealmResults;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.password.bean.AccountBean;
import ssthouse.love.xinying.password.bean.AccountChangeEvent;
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
        AccountDetailActivity.start(getContext(), AccountDetailActivity.DEFAULT_ACCOUNT_BEAN_ID);
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
            }

            @Override
            public void afterTextChanged(Editable s) {
                //clear former list
                mDisplayAccountBeanList.clear();
                //judge every account
                for (AccountBean accountBean : mAccountBeanList) {
                    if (accountBean.getServiceName().contains(s) || accountBean.getDescription().contains(s)) {
                        mDisplayAccountBeanList.add(accountBean);
                    }
                }
                //refresh ui
                mAccountAdapter.notifyDataSetChanged();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_account, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.ivLogo = (ImageView) convertView.findViewById(R.id.id_iv_service_logo);
                viewHolder.tvServiceName = (TextView) convertView.findViewById(R.id.id_tv_service_name);
                viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.id_tv_description);
                viewHolder.cardView = (CardView) convertView.findViewById(R.id.id_card_view);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvDescription.setText(mDisplayAccountBeanList.get(position).getDescription());
            viewHolder.tvServiceName.setText(mDisplayAccountBeanList.get(position).getServiceName());
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccountDetailActivity.start(getContext(), mDisplayAccountBeanList.get(position).getId());
                }
            });
            return convertView;
        }
    };

    private class ViewHolder {
        ImageView ivLogo;
        TextView tvServiceName;
        TextView tvDescription;
        CardView cardView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAccountListChangeEvent(AccountChangeEvent event) {
        mAccountBeanList = mAccountDao.getAllAccounts();
        mEtSearchAccount.getText().clear();
        mDisplayAccountBeanList.clear();
        mDisplayAccountBeanList.addAll(mAccountBeanList);
        mAccountAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

}
