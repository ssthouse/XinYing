package ssthouse.love.xinying.base.app;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.avos.avoscloud.AVOSCloud;
import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by ssthouse on 16/5/18.
 */
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "al5DXaGvmjcmCByiGKixzBDb-gzGzoHsz", "SXrCzX0RMXHDN1HGb8w1oP4i");
        //detect memory leak
        if (LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
        //TODO change from activeAndroid to Realm later
        ActiveAndroid.initialize(this);
        //Realm
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
