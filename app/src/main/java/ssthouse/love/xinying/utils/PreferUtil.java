package ssthouse.love.xinying.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ssthouse on 16/9/2.
 */
public class PreferUtil {


    private static PreferUtil mInstance;
    private Context mContext;


    public static void initInstance(Context context) {
        if (mInstance == null)
            mInstance = new PreferUtil(context);
    }

    public static PreferUtil getInstance(){
        return mInstance;
    }

    public PreferUtil(Context context) {
        this.mContext = context;
    }

    public static final String PREFER_FILE_NAME = "preference";

    public static final String KEY_IS_CONY = "isCony";

    public static final String KEY_IS_FIST_IN = "isFistIn";


    /****************
     * 第一次进入
     *****************/
    public boolean isFistIn() {
        return mContext.getSharedPreferences(PREFER_FILE_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_IS_FIST_IN, true);
    }

    public void setIsFistIn(boolean isFistIn) {
        setBoolean(KEY_IS_FIST_IN, isFistIn);
    }


    /***********
     * 是不是Cony
     *******************/
    public boolean isCony() {
        return mContext.getSharedPreferences(PREFER_FILE_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_IS_CONY, true);
    }

    public void setIsCony(boolean value) {
        setBoolean(KEY_IS_CONY, value);
    }


    /************************
     * base function
     ********************************/
    public boolean getBoolean(String keyStr, boolean defaultValue) {
        return mContext.getSharedPreferences(PREFER_FILE_NAME, Context.MODE_PRIVATE)
                .getBoolean(keyStr, defaultValue);
    }

    public void setBoolean(String keyStr, boolean value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREFER_FILE_NAME, Context.MODE_PRIVATE)
                .edit();
        editor.putBoolean(keyStr, value)
                .apply();
    }
}
