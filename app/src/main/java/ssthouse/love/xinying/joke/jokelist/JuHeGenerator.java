package ssthouse.love.xinying.joke.jokelist;

import android.content.Context;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ssthouse.love.xinying.joke.bean.JuheJokeBean;

/**
 * Created by ssthouse on 07/12/2016.
 */

public class JuHeGenerator {

    public static void getJuHeJoke(Context context, Callback<JuheJokeBean> callback) {
        //load from internet
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JuHeService.BASIC_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JuHeService service = retrofit.create(JuHeService.class);
        service.getJuHeJoke(JuHeService.KEY)
                .enqueue(callback);
    }

}
