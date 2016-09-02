package ssthouse.love.xinying.main;

import android.app.Application;

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
    }
}
