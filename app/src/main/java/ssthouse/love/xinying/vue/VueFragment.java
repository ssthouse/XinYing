package ssthouse.love.xinying.vue;

import android.webkit.WebView;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;

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
}
