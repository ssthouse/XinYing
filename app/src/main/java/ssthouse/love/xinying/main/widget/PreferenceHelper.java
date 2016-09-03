package ssthouse.love.xinying.main.widget;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ssthouse on 2015/12/9.
 */
public class PreferenceHelper {
    private static final String TAG = "PreferenceHelper";

    /**
     * 单例
     */
    private static PreferenceHelper preferenceHelper;

    private SharedPreferences sharedPreferences;

    private static final String KEY_NOTE = "note";
    private static final String KEY_IS_FIST_IN = "isFistIn";

    private PreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("preference", Context.MODE_PRIVATE);
    }

    public synchronized static PreferenceHelper getInstance(Context context) {
        if (preferenceHelper == null) {
            preferenceHelper = new PreferenceHelper(context);
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

    private static final String KEY_COLOR = "color";

    /**
     * 修改颜色
     *
     * @param color
     */
    public void setColor(int color) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_COLOR, color);
//        Log.e(TAG, "我现在保存的颜色是:"+Integer.toHexString(color));
        editor.apply();
    }

    /**
     * 获取颜色
     */
    public int getColor() {
//        Log.e(TAG, "我现在的颜色是:"+Integer.toHexString(sharedPreferences.getInt(KEY_COLOR, 0xffffffff)));
        return sharedPreferences.getInt(KEY_COLOR, 0xffffffff);
    }

    public void setIsFistIn(boolean isFistIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_FIST_IN, isFistIn);
        editor.apply();
    }

    public boolean isFistIn(){
        return sharedPreferences.getBoolean(KEY_IS_FIST_IN, true);
    }
}
