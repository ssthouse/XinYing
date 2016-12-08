package ssthouse.love.xinying.joke;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.joke.jokelist.JokeListFragment;
import ssthouse.love.xinying.joke.zhihu.ZhiHuFragment;

/**
 * Created by ssthouse on 05/12/2016.
 */

public class JokeFragment extends BaseFragment {

    private static final int TAB_SIZE = 2;

    @Bind(R.id.id_view_pager)
    ViewPager viewPager;

    @Bind(R.id.id_tab_layout)
    TabLayout tabLayout;

    private JokeListFragment jokeListFragment;
    private ZhiHuFragment zhiHuFragment;

    @Override
    public int getContentView() {
        return R.layout.fragment_joke;
    }

    @Override
    public void init() {
        initViewPager();
    }

    private void initViewPager() {
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return TAB_SIZE;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "ZHIHU";
                    case 1:
                        return "JOKE";
                }
                return "";
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return getZhiHuFragment();
                    case 1:
                        return getJokeListFragment();
                }
                return null;
            }
        });

        for (int i = 0; i < TAB_SIZE; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.setupWithViewPager(viewPager);
    }

    public JokeListFragment getJokeListFragment() {
        if (jokeListFragment == null)
            jokeListFragment = new JokeListFragment();
        return jokeListFragment;
    }

    public ZhiHuFragment getZhiHuFragment() {
        if (zhiHuFragment == null)
            zhiHuFragment = new ZhiHuFragment();
        return zhiHuFragment;
    }

}
