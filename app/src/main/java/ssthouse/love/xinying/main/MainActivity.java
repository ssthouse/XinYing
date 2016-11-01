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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SaveCallback;
import com.squareup.picasso.Picasso;
import com.umeng.message.PushAgent;
import com.vdurmont.emoji.EmojiParser;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.base.BaseActivity;
import ssthouse.love.xinying.main.bean.SignNumber;
import ssthouse.love.xinying.main.fragment.NoteIntoFragment;
import ssthouse.love.xinying.main.msg.LeaveMsgFragment;
import ssthouse.love.xinying.main.todo.MainFragment;
import ssthouse.love.xinying.utils.ActivityUtil;
import ssthouse.love.xinying.utils.PermissionUtil;
import ssthouse.love.xinying.utils.PreferUtil;
import ssthouse.love.xinying.utils.ViewUtil;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    //drawer
    private ImageView ivAvatar;
    private TextView tvName;
    private Button btnSign;

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
        initActionbar();
        initDrawer();
        initFragment();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    private void initActionbar() {
        setSupportActionBar(toolbar);
        String title = "love you so much :kissing_heart::kissing_heart::kissing_heart:";
        title = EmojiParser.parseToUnicode(title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDrawer() {
        initDrawerNameAndAvatar();
        initDrawerItem();
        initSignInButton();
    }

    private void initDrawerNameAndAvatar() {
        tvName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.id_tv_name);
        ivAvatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.id_iv_avatar);
        if (PreferUtil.getInstance().isCony()) {
            tvName.setText("学弟的学姐");
            Picasso.with(this)
                    .load(R.drawable.cony_avatar)
                    .into(ivAvatar);
        } else {
            tvName.setText("学弟的学姐");
            Picasso.with(this)
                    .load(R.drawable.brown_avatar)
                    .into(ivAvatar);
        }
    }

    private void initSignInButton() {
        btnSign = (Button) navigationView.getHeaderView(0).findViewById(R.id.id_btn_sign);
        updateBtnSign();
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long lastTime = Long.parseLong(PreferUtil.getInstance().getLastSignTimeInMillisStr());
                if (System.currentTimeMillis() - lastTime > 24 * 60 * 60 * 1000) {
                    btnSign.setBackgroundResource(R.color.grey);
                    btnSign.setEnabled(false);
                    //设置今天的0点
                    long curTime = System.currentTimeMillis();
                    curTime = curTime - curTime % (24 * 60 * 60 * 1000);
                    PreferUtil.getInstance().setLastSignTimeInMillis(curTime + "");

                    //网络端签到
                    sign();
                }
            }
        });
    }

    private void initDrawerItem() {
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
                    case R.id.id_menu_setting:
                        ActivityUtil.startAty(MainActivity.this, SettingActivity.class);
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

    private void updateBtnSign() {
        long lastTime = Long.parseLong(PreferUtil.getInstance().getLastSignTimeInMillisStr());
        if (System.currentTimeMillis() - lastTime > 24 * 60 * 60 * 1000) {
            btnSign.setBackgroundResource(R.color.colorAccent);
            btnSign.setEnabled(true);
        } else {
            btnSign.setBackgroundResource(R.color.grey);
            btnSign.setEnabled(false);
        }
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

    /**
     * 签到
     */
    private void sign() {
        Observable.just("")
                .map(new Func1<String, AVObject>() {
                    @Override
                    public AVObject call(String s) {
                        AVQuery<AVObject> query = new AVQuery<>(SignNumber.CLASS_NAME);
                        AVObject avObject = null;
                        try {
                            avObject = query.whereEqualTo(SignNumber.KEY_IS_CONY, PreferUtil.getInstance().isCony())
                                    .getFirst();
                        } catch (AVException e) {
                            e.printStackTrace();
                        }
                        return avObject;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AVObject>() {
                    @Override
                    public void call(AVObject avObject) {
                        //第一次签到
                        if (avObject == null) {
                            String str = "第一次签到哦, 啵 :kissing_heart:";
                            str = EmojiParser.parseToUnicode(str);
                            ViewUtil.toast(MainActivity.this, str);
                            AVObject avobject = new AVObject(SignNumber.CLASS_NAME);
                            avobject.put(SignNumber.KEY_IS_CONY, PreferUtil.getInstance().isCony());
                            avobject.put(SignNumber.KEY_SIGN_NUMBER, 1);
                            avobject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    Timber.e(e == null ? "保存成功" : "保存失败");
                                }
                            });
                            return;
                        }
                        int signNumber = avObject.getInt(SignNumber.KEY_SIGN_NUMBER) + 1;
                        avObject.put(SignNumber.KEY_SIGN_NUMBER, signNumber);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                Timber.e(e == null ? "保存成功" : "保存失败");
                            }
                        });
                        ViewUtil.toast(MainActivity.this, "签到次数: " + signNumber);
                    }
                });
    }
}
