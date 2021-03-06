package ssthouse.love.xinying.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssthouse.love.xinying.base.BaseFragmentManager;
import ssthouse.love.xinying.joke.JokeFragment;
import ssthouse.love.xinying.jokebackup.JokeBackupFragment;
import ssthouse.love.xinying.msg.LeaveMsgFragment;
import ssthouse.love.xinying.note.sharenote.ShareNoteFragment;
import ssthouse.love.xinying.password.UserVerifyFragment;
import ssthouse.love.xinying.vue.VueFragment;

/**
 * Created by ssthouse on 01/11/2016.
 */

public class MainFragmentManager extends BaseFragmentManager {

    static final String KEY_FRAGMENT_VUE = "vue";
    static final String KEY_FRAGMENT_FAST_NOTE = "FastNode";
    static final String KEY_FRAGMENT_LEAVE_MSG = "LeaveMsg";
    static final String KEY_FRAGMENT_JOKE = "joke";
    static final String KEY_FRAGMENT_JOKE_BACKUP = "jokeBackup";
    static final String KEY_FRAGMENT_PASSWORD = "password";

    private static final String[] FRAGMENT_ARRAY = {
            KEY_FRAGMENT_VUE,
            KEY_FRAGMENT_FAST_NOTE,
            KEY_FRAGMENT_LEAVE_MSG,
            KEY_FRAGMENT_JOKE,
            KEY_FRAGMENT_JOKE_BACKUP,
            KEY_FRAGMENT_PASSWORD
    };

    public MainFragmentManager(FragmentManager mFragmentManager, int fragmentId) {
        super(mFragmentManager, fragmentId);
    }

    @Override
    protected Map<String, Fragment> initFragmentManager() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new VueFragment());
        fragmentList.add(new ShareNoteFragment());
        fragmentList.add(new LeaveMsgFragment());
        fragmentList.add(new JokeFragment());
        fragmentList.add(new JokeBackupFragment());
        fragmentList.add(new UserVerifyFragment());
        Map<String, Fragment> fragmentMap = new HashMap<>();
        for (int i = 0; i < fragmentList.size(); i++) {
            fragmentMap.put(FRAGMENT_ARRAY[i], fragmentList.get(i));
        }
        return fragmentMap;
    }

}
