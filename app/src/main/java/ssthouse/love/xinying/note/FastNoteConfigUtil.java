package ssthouse.love.xinying.note;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ssthouse on 2015/12/9.
 */
public class FastNoteConfigUtil {

    private static FastNoteConfigUtil preferenceHelper;

    private SharedPreferences sharedPreferences;

    private static final String KEY_NOTE = "note";
    private static final String KEY_COLOR = "color";
    private static final String PREFER_FILE_NAME = "preference";

    private FastNoteConfigUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFER_FILE_NAME, Context.MODE_PRIVATE);
    }

    public synchronized static FastNoteConfigUtil getInstance(Context context) {
        if (preferenceHelper == null) {
            preferenceHelper = new FastNoteConfigUtil(context.getApplicationContext());
        }
        return preferenceHelper;
    }

    public void saveNote(String note) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NOTE, note);
        editor.apply();
    }

    public String getNote() {
        return sharedPreferences.getString(KEY_NOTE, "");
    }

    public void setColor(int color) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_COLOR, color);
        editor.apply();
    }

    public int getColor() {
        return sharedPreferences.getInt(KEY_COLOR, 0xffffffff);
    }
}
