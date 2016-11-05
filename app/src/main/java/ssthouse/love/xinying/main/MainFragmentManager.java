package ssthouse.love.xinying.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssthouse.love.xinying.main.base.BaseFragmentManager;
import ssthouse.love.xinying.main.fragment.ShareNoteFragment;
import ssthouse.love.xinying.main.msg.LeaveMsgFragment;
import ssthouse.love.xinying.main.todo.TodoFragment;

/**
 * Created by ssthouse on 01/11/2016.
 */

public class MainFragmentManager extends BaseFragmentManager {

    public static final String KEY_FRAGMENT_TODO = "Todo";
    public static final String KEY_FRAGMENT_FAST_NOTE = "FastNode";
    public static final String KEY_FRAGMENT_LEAVE_MSG = "LeaveMsg";

    private List<String> mFragmentKeyList;

    public MainFragmentManager(FragmentManager mFragmentManager, int fragmentId) {
        super(mFragmentManager, fragmentId);
    }

    @Override
    protected Map<String, Fragment> initFragmentManager() {
        mFragmentKeyList = new ArrayList<>();
        mFragmentKeyList.add(KEY_FRAGMENT_TODO);
        mFragmentKeyList.add(KEY_FRAGMENT_FAST_NOTE);
        mFragmentKeyList.add(KEY_FRAGMENT_LEAVE_MSG);
        Map<String, Fragment> fragmentMap = new HashMap<>();
        fragmentMap.put(mFragmentKeyList.get(0), new TodoFragment());
        fragmentMap.put(mFragmentKeyList.get(1), new ShareNoteFragment());
        fragmentMap.put(mFragmentKeyList.get(2), new LeaveMsgFragment());
        return fragmentMap;
    }

}
