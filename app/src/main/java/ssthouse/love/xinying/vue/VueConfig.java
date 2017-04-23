package ssthouse.love.xinying.vue;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by ssthouse on 23/04/2017.
 */

public class VueConfig {

    private SharedPreferences preferences;

    private static final String PREFERENCE_FILE_NAME = "vueConfig";

    private static final String KEY_URL = "url";
    private static final String DEFAULT_URL = "https://ssthouse.github.io";

    public VueConfig(Context context) {
        preferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public String getURL() {
        return preferences.getString(KEY_URL, DEFAULT_URL);
    }

    public void setUrl(String newUrl) {
        if (TextUtils.isEmpty(newUrl)) {
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_URL, newUrl);
        editor.apply();
    }

}
