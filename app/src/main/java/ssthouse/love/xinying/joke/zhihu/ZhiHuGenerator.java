package ssthouse.love.xinying.joke.zhihu;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ssthouse.love.xinying.joke.bean.ZhiHuBean;

/**
 * Created by ssthouse on 06/12/2016.
 */

public class ZhiHuGenerator {

    public static void getStories(Callback<ZhiHuBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ZhiHuService zhiHuService = retrofit.create(ZhiHuService.class);
        zhiHuService.getZhiHuBean().enqueue(callback);
    }
}
