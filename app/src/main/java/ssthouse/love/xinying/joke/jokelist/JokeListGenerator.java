package ssthouse.love.xinying.joke.jokelist;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ssthouse.love.xinying.joke.bean.JokeBean;

/**
 * TODO
 * generate today's joke:
 * check the joke object on leancloud>>>if the joke object on leancloud is today's use the joke in leancloud which is
 * upload by giant baby
 * Created by ssthouse on 05/12/2016.
 */

public class JokeListGenerator {

    public static void getJokeList(Callback<List<JokeBean>> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JokeService.BASIC_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JokeService jokeService = retrofit.create(JokeService.class);
        jokeService.getJokeLIst().enqueue(callback);
    }
}
