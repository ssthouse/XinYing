package ssthouse.love.xinying.main.app;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

import ssthouse.love.xinying.utils.PreferUtil;
import timber.log.Timber;

/**
 * Created by ssthouse on 16/5/18.
 */
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        PreferUtil.initInstance(this);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"al5DXaGvmjcmCByiGKixzBDb-gzGzoHsz","SXrCzX0RMXHDN1HGb8w1oP4i");
    }
}
