package ssthouse.love.xinying.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Map;

import timber.log.Timber;

/**
 * Created by ssthouse on 01/11/2016.
 */

public abstract class BaseFragmentManager {

    private FragmentManager mFragmentManager;

    private Map<String, Fragment> mFragmentMap;

    private int mFragmentId;

    public BaseFragmentManager(FragmentManager mFragmentManager, int fragmentId) {
        this.mFragmentManager = mFragmentManager;
        this.mFragmentId = fragmentId;
        mFragmentMap = initFragmentManager();
    }

    protected abstract Map<String, Fragment> initFragmentManager();

    public void initFragment(String key) {
        if (!mFragmentMap.keySet().contains(key))
            return;
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for (String strKey : mFragmentMap.keySet()) {
            fragmentTransaction.add(mFragmentId, mFragmentMap.get(strKey))
                    .hide(mFragmentMap.get(strKey));
        }
        fragmentTransaction.commit();
        mFragmentManager.beginTransaction()
                .show(mFragmentMap.get(key))
                .commit();
    }

    public void change2Fragment(String key) {
        Timber.e(key + "*****************");
        if (!mFragmentMap.keySet().contains(key))
            return;
//        mFragmentManager.beginTransaction()
//                .show(mFragmentMap.get(key))
//                .commit();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for (String strKey : mFragmentMap.keySet()) {
            fragmentTransaction.hide(mFragmentMap.get(strKey));
        }
        fragmentTransaction.commit();
        mFragmentManager.beginTransaction()
                .show(mFragmentMap.get(key))
                .commit();
    }

    public void changeFragment(String key, Fragment fragment) {
        if (!mFragmentMap.keySet().contains(key)) {
            return;
        }
        Fragment formerFragment = mFragmentMap.get(key);
        mFragmentManager.beginTransaction()
                .remove(formerFragment)
                .add(mFragmentId, fragment)
                .commit();
        mFragmentMap.put(key, fragment);
    }
}
