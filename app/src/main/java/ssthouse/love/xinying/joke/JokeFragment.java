package ssthouse.love.xinying.joke;

import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.joke.zhihu.ZhiHuFragment;

/**
 * Created by ssthouse on 05/12/2016.
 */

public class JokeFragment extends BaseFragment {

    private ZhiHuFragment zhiHuFragment;

    @Override
    public int getContentView() {
        return R.layout.fragment_joke;
    }

    @Override
    public void init() {
        getFragmentManager().beginTransaction()
                .replace(R.id.id_fragment_holder, getZhiHuFragment())
                .commit();
    }


    public ZhiHuFragment getZhiHuFragment() {
        if (zhiHuFragment == null)
            zhiHuFragment = new ZhiHuFragment();
        return zhiHuFragment;
    }

}
