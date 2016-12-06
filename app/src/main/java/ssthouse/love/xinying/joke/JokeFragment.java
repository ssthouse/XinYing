package ssthouse.love.xinying.joke;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.joke.jokelist.JokeListFragment;
import ssthouse.love.xinying.joke.zhihu.ZhiHuFragment;
import timber.log.Timber;

/**
 * Created by ssthouse on 05/12/2016.
 */

public class JokeFragment extends BaseFragment {

    private static final int TAB_SIZE = 3;

    @Bind(R.id.id_view_pager)
    ViewPager viewPager;

    @Bind(R.id.id_tab_layout)
    TabLayout tabLayout;

    private JokeListFragment jokeListFragment;
    private ZhiHuFragment zhiHuFragment;
    private GiantBabyJokeFragment giantBabyJokeFragment;

    @Override
    public int getContentView() {
        return R.layout.fragment_joke;
    }

    @Override
    public void init() {
        initViewPager();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.e("oncreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.e("resume");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Timber.e("onattach");
    }

    private void initViewPager() {
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Joke";
                    case 1:
                        return "ZhiHu";
                    case 2:
                        return "Giant Baby";
                }
                return "";
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return getJokeListFragment();
                    case 1:
                        return getZhiHuFragment();
                    case 2:
                        return getGiantBabyJokeFragment();
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

    public GiantBabyJokeFragment getGiantBabyJokeFragment() {
        if (giantBabyJokeFragment == null)
            giantBabyJokeFragment = new GiantBabyJokeFragment();
        return giantBabyJokeFragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Timber.e("detatch");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.e("destory");
    }
}
