package ssthouse.love.xinying.joke.zhihu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseActivity;
import ssthouse.love.xinying.joke.bean.ZhiHuBean;
import ssthouse.love.xinying.joke.bean.ZhiHuDetailBean;
import ssthouse.love.xinying.utils.ToastUtil;
import timber.log.Timber;

/**
 * Created by ssthouse on 06/12/2016.
 */

public class ZhiHuDetailAty extends BaseActivity {

    @Bind(R.id.id_tb)
    Toolbar mToolbar;

    @Bind(R.id.id_web_view)
    WebView mWebView;

    private ZhiHuBean.StoriesBean mStoriesBean;

    private ZhiHuDetailBean mZhiHuDetailBean;

    private static final String KEY_STORY_BEAN = "story";

    public static void start(Activity activity, ZhiHuBean.StoriesBean storiesBean) {
        Intent intent = new Intent(activity, ZhiHuDetailAty.class);
        intent.putExtra(KEY_STORY_BEAN, storiesBean);
        activity.startActivity(intent);
    }

    @Override
    public void init() {
        mStoriesBean = (ZhiHuBean.StoriesBean) getIntent().getSerializableExtra(KEY_STORY_BEAN);
        if (mStoriesBean == null) {
            ToastUtil.show(this, "Something is wrong");
            return;
        }

        initActionbar();

        //get data from zhihu api
        ZhiHuGenerator.getStortDetailBean(mStoriesBean.getId() + "", new Callback<ZhiHuDetailBean>() {
            @Override
            public void onResponse(Call<ZhiHuDetailBean> call, Response<ZhiHuDetailBean> response) {
                Timber.e(response.body().getBody());
                mZhiHuDetailBean = response.body();
                loadHtmlWithCss();
            }

            @Override
            public void onFailure(Call<ZhiHuDetailBean> call, Throwable t) {

            }
        });
    }

    private void initActionbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mStoriesBean.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadHtmlWithCss() {
        if (mZhiHuDetailBean == null)
            return;
        String mime = "text/html";
        String encoding = "utf-8";
        mWebView.loadDataWithBaseURL(null, getBodyWithCss(), mime, encoding, null);
    }

    private String getBodyWithCss() {
        String header = "<html><head><link href=\"%s\" type=\"text/css\" rel=\"stylesheet\"/></head><body>";
        String footer = "</body></html>";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(header, mZhiHuDetailBean.getCss().get(0)));
        sb.append(mZhiHuDetailBean.getBody());
        sb.append(footer);
        Timber.e(sb.toString());
        return sb.toString().replace("class=\"img-place-holder\"", "");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_zhihu_detail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
