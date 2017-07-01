package ssthouse.love.xinying.vue.java_interface;

import android.app.Activity;

import org.greenrobot.eventbus.EventBus;

import ssthouse.love.xinying.utils.PreferUtil;
import ssthouse.love.xinying.vue.event.OpenDrawerEvent;

/**
 * Created by ssthouse on 30/06/2017.
 */

public class JavaInterface {

    private Activity mContext;

    public JavaInterface(Activity mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取用户是否为cony信息
     *
     * @return
     */
    @android.webkit.JavascriptInterface
    public boolean isCony() {
        return PreferUtil.getInstance(mContext).isCony();
    }

    @android.webkit.JavascriptInterface
    public void openDrawer() {
        EventBus.getDefault().post(new OpenDrawerEvent());
    }
}
