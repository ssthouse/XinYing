package ssthouse.love.xinying.password.bean;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ssthouse on 23/03/2017.
 */

public class ServiceBean extends RealmObject {

    private String serviceName;

    private RealmList<AccountBean> accountBeanRealmList;

    @PrimaryKey
    private int id;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<AccountBean> getAccountBeanRealmList() {
        return accountBeanRealmList;
    }

    public void setAccountBeanRealmList(RealmList<AccountBean> accountBeanRealmList) {
        this.accountBeanRealmList = accountBeanRealmList;
    }
}
