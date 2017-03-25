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


    public AccountBean getAccountBean(int accountBeanId) {
        return mRealm.where(AccountBean.class)
                .equalTo("id", accountBeanId)
                .findFirst();
    }

    public static int getNextId() {
        Number num = Realm.getDefaultInstance().where(AccountBean.class).max("id");
        int result = num == null ? 0 : num.intValue() + 1;
        return result;
    }
}
