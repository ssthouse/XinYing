package ssthouse.love.xinying.jokebackup;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssthouse on 22/03/2017.
 */

public class JokeBackupManager {

    private static final String KEY_PREFERENCE_NAME = "jokeBackup";

    private static final String KEY_JOKE_SIZE = "jokeSize";
    private static final String KEY_JOKE_PREFIX = "joke_";

    private Context mContext;

    public JokeBackupManager(Context mContext) {
        this.mContext = mContext;
    }

    public List<String> getJokeList() {
        List<String> jokeList = new ArrayList<>();
        SharedPreferences preferences = mContext.getSharedPreferences(KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        int jokeSize = preferences.getInt(KEY_JOKE_SIZE, 0);
        for (int i = 0; i < jokeSize; i++) {
            String curJokeKey = KEY_JOKE_PREFIX + i;
            String curJoke = preferences.getString(curJokeKey, "");
            jokeList.add(curJoke);
        }
        return jokeList;
    }

    public void removeAllJokes() {
        SharedPreferences preferences = mContext.getSharedPreferences(KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int jokeSize = preferences.getInt(KEY_JOKE_SIZE, 0);
        for (int i = 0; i < jokeSize; i++) {
            String curJokeKey = KEY_JOKE_PREFIX + i;
            editor.remove(curJokeKey);
        }
        editor.apply();
    }

    public void addAllJokes(List<String> jokeList) {
        if (jokeList == null) {
            return;
        }
        int size = jokeList.size();
        SharedPreferences preferences = mContext.getSharedPreferences(KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_JOKE_SIZE, size);
        for (int i = 0; i < size; i++) {
            String curJokeKey = KEY_JOKE_PREFIX + i;
            editor.putString(curJokeKey, jokeList.get(i));
        }
        editor.apply();
    }

    public void appendJoke(String joke) {
        if (TextUtils.isEmpty(joke)) {
            return;
        }
        SharedPreferences preferences = mContext.getSharedPreferences(KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int size = preferences.getInt(KEY_JOKE_SIZE, 0);
        editor.putInt(KEY_JOKE_SIZE, size + 1);
        editor.putString(KEY_JOKE_PREFIX + size, joke);
        editor.apply();
    }

    public void replaceAllJokes(List<String> jokeList) {
        if (jokeList == null) {
            return;
        }
        removeAllJokes();
        addAllJokes(jokeList);
    }

}
