package ssthouse.love.xinying.vue;

import android.webkit.WebView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.vue.event.VueRefreshEvent;

/**
 * Created by ssthouse on 22/04/2017.
 */

public class VueFragment extends BaseFragment {

    private VueConfig mVueConfig;

    @Bind(R.id.id_web_view)
    WebView mWebView;

    @Override
    public int getContentView() {
        return R.layout.fragment_vue;
    }

    @Override
    public void init() {
        mVueConfig = new VueConfig(getContext());
        initView();
    }

    private void initView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mVueConfig.getURL());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVueRefreshEvent(VueRefreshEvent event) {
        mWebView.reload();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
