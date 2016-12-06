package ssthouse.love.xinying.joke;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.joke.jokelist.JokeListFragment;

/**
 * Created by ssthouse on 05/12/2016.
 */

public class JokeFragment extends BaseFragment {

    @Bind(R.id.id_view_pager)
    ViewPager viewPager;

    @Bind(R.id.id_tab_layout)
    TabLayout tabLayout;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_joke;
    }

    @Override
    public void init() {
        initViewPager();
    }

    private void initViewPager() {
        fragmentList.add(new JokeListFragment());
        fragmentList.add(new JokeListFragment());
        fragmentList.add(new JokeListFragment());

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
                return fragmentList.get(position);
            }
        });

        for (int i = 0; i < fragmentList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.setupWithViewPager(viewPager);
    }
}
