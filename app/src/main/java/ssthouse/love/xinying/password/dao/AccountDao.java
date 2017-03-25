package ssthouse.love.xinying.password.dao;

import io.realm.Realm;
import io.realm.RealmResults;
import ssthouse.love.xinying.password.bean.AccountBean;

/**
 * Created by ssthouse on 23/03/2017.
 */

public class AccountDao {

    private Realm mRealm;

    public AccountDao() {
        mRealm = Realm.getDefaultInstance();
    }

    public RealmResults<AccountBean> getAllAccounts() {
        //TODO to be judged
        return mRealm.where(AccountBean.class)
                .findAllSorted("serviceName");
    }


}
