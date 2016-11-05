package ssthouse.love.xinying.utils;

import android.widget.TextView;

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

}
