package ssthouse.love.xinying.joke.jokelist;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * save today's joke and joke timestamp
 * Created by ssthouse on 07/12/2016.
 */

public class TodayJokeRecorder {


    private static TodayJokeRecorder instance;

    private Context mContext;

    private static final String PREFER_FILE_NAME = "joke_recorder";

    private SharedPreferences mSharedPreference;

    private TodayJokeRecorder(Context context) {
        this.mContext = context;
        mSharedPreference = context.getSharedPreferences(PREFER_FILE_NAME, Context.MODE_PRIVATE);
    }

    public TodayJokeRecorder getInstance(Context context) {
        if (instance == null)
            instance = new TodayJokeRecorder(context);
        return instance;
    }




}
