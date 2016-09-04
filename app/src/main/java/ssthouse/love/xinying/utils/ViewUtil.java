package ssthouse.love.xinying.utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import net.frakbot.jumpingbeans.JumpingBeans;

/**
 * Created by ssthouse on 16/9/2.
 */
public class ViewUtil {

    public static void loadThreeDot(TextView textView) {
        JumpingBeans.with(textView)
                .appendJumpingDots()
                .build();
    }

    public static void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
