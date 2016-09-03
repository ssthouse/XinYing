package ssthouse.love.xinying.main;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.squareup.picasso.Picasso;
import com.umeng.message.PushAgent;
import com.vdurmont.emoji.EmojiParser;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.base.BaseActivity;
import ssthouse.love.xinying.main.fragment.MainFragment;
import ssthouse.love.xinying.main.fragment.NoteIntoFragment;
import ssthouse.love.xinying.main.msg.LeaveMsgFragment;
import ssthouse.love.xinying.utils.PermissionUtil;
import ssthouse.love.xinying.utils.PreferUtil;

public class MainActivity extends BaseActivity {

    //drawer
    private ImageView ivAvatar;
    private TextView tvName;

    //main
    @Bind(R.id.id_tb)
    Toolbar toolbar;

    @Bind(R.id.id_navigation)
    NavigationView navigationView;

    @Bind(R.id.id_drawer_view)
    DrawerLayout drawerLayout;

    //Fragments
    private FragmentManager mFragmentManager;
    private MainFragment mainFragment;
    private Fragment noteIntoFragment;
    private Fragment leaveMsgFragment;

    @Override
    public void init() {
        //检查写setting权限
        PermissionUtil.checkWriteSetting(this);

        //开启友盟推送
        PushAgent mPushAgent = PushAgent.getInstance(MainActivity.this);
        mPushAgent.enable();

        //初始化view
        initView();
        initDrawer();
        initFragment();

        AVObject object = new AVObject();
        object.put("testKey", "testValue");
        object.saveInBackground();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    private void initView() {
        setSupportActionBar(toolbar);
        String title = "love you so much :kissing_heart::kissing_heart::kissing_heart:";
        title = EmojiParser.parseToUnicode(title);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            getSupportActionBar().setTitle(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initDrawer(){
        tvName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.id_tv_name);
        ivAvatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.id_iv_avatar);

        if (PreferUtil.getInstance().isCony()) {
            tvName.setText("学弟的学姐");
            Picasso.with(this)
                    .load(R.drawable.cony_avatar)
                    .into(ivAvatar);
        }else{
            tvName.setText("学弟的学姐");
            Picasso.with(this)
                    .load(R.drawable.brown_avatar)
                    .into(ivAvatar);
        }

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.str_drawer_open,
                R.string.str_drawer_close);
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                Fragment toFragment = null;
                switch (item.getItemId()) {
                    case R.id.id_menu_main:
                        toFragment = mainFragment;
                        break;
                    case R.id.id_menu_note_into:
                        toFragment = noteIntoFragment;
                        break;
                    case R.id.id_menu_leave_msg:
                        toFragment = leaveMsgFragment;
                        break;
                    default:
                        toFragment = mainFragment;
                        break;
                }
                mFragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.id_fragment_container, toFragment)
                        .commit();
                return true;
            }
        });
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();

        mainFragment = new MainFragment();
        noteIntoFragment = new NoteIntoFragment();
        leaveMsgFragment = new LeaveMsgFragment();
        //填充当前fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_fragment_container, mainFragment)
                .commit();
    }
}
