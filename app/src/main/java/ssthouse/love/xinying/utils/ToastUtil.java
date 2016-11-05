package ssthouse.love.xinying.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ssthouse on 05/11/2016.
 */

public class ToastUtil {

    public static void show(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
