package ssthouse.love.xinying.joke.zhihu;

import retrofit2.Call;
import retrofit2.http.GET;
import ssthouse.love.xinying.joke.bean.ZhiHuBean;

/**
 * Created by ssthouse on 06/12/2016.
 */

public interface ZhiHuService {

    @GET("api/4/news/latest")
    public Call<ZhiHuBean> getZhiHuBean();
}
