package ssthouse.love.xinying.joke.jokelist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ssthouse.love.xinying.joke.bean.JokeBean;

/**
 * Created by ssthouse on 06/12/2016.
 */

public interface JokeService {

    public static final String BASIC_URL = "http://api.laifudao.com/";

    @GET("/open/xiaohua.json")
    Call<List<JokeBean>> getJokeLIst();
}
