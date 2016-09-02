package ssthouse.love.xinying.main;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.umeng.message.PushAgent;
import com.vdurmont.emoji.EmojiParser;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseActivity;
import ssthouse.love.xinying.main.fragment.EnergyGirlFragment;
import ssthouse.love.xinying.main.fragment.FistImpressionFragment;
import ssthouse.love.xinying.utils.PermissionUtil;

public class MainActivity extends BaseActivity {

    //UI
    @Bind(R.id.id_tb)
    Toolbar toolbar;

    @Bind(R.id.id_navigation)
    NavigationView navigationView;

    @Bind(R.id.id_drawer_view)
    DrawerLayout drawerLayout;

    //Fragments
    private FragmentManager mFragmentManager;
    private MainFragment mainFragment;
    private EnergyGirlFragment energyGirlFragment;
    private FistImpressionFragment fistImpressionFragment;

    @Override
    public void init() {
        //检查写setting权限
        PermissionUtil.checkWriteSetting(this);

        //开启友盟推送
        PushAgent mPushAgent = PushAgent.getInstance(MainActivity.this);
        mPushAgent.enable();

        //初始化view
        initView();
        initFragment();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    private void initView() {
        setSupportActionBar(toolbar);
        String title = "love you so much :kissing_heart::kissing_heart::kissing_heart:";
        title = EmojiParser.parseToUnicode(title);
        getSupportActionBar().setTitle(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.str_drawer_open,
                R.string.str_drawer_close);
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.id_menu_main:
                        mFragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.id_fragment_container, mainFragment)
                                .commit();
                        break;
                    case R.id.id_menu_energy_girl:
                        mFragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.id_fragment_container, energyGirlFragment)
                                .commit();
                        break;
                    case R.id.id_menu_fist_impression:
                        mFragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.id_fragment_container, fistImpressionFragment)
                                .commit();
                        break;
                }
                return true;
            }
        });
    }


    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();

        mainFragment = new MainFragment();
        //energyGirlFragment = new EnergyGirlFragment();
        //fistImpressionFragment = new FistImpressionFragment();

        //填充当前fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_fragment_container, mainFragment)
                .commit();
    }
}
