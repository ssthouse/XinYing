package ssthouse.love.xinying.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.fragment.EnergyGirlFragment;
import ssthouse.love.xinying.main.fragment.FistImpressionFragment;

public class MainActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("love you so much(⊙_⊙)");

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
        energyGirlFragment = new EnergyGirlFragment();
        fistImpressionFragment = new FistImpressionFragment();

        //填充当前fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_fragment_container, mainFragment)
                .commit();
    }
}
