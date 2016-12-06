package ssthouse.love.xinying.joke.zhihu;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ssthouse.love.xinying.joke.bean.ZhiHuBean;
import ssthouse.love.xinying.joke.bean.ZhiHuDetailBean;

/**
 * Created by ssthouse on 06/12/2016.
 */

public interface ZhiHuService {

    @GET("api/4/news/latest")
    Call<ZhiHuBean> getZhiHuBean();

    @GET("api/4/news/{id}")
    Call<ZhiHuDetailBean> getZhiHuDetailBean(@Path("id") String id);
}
