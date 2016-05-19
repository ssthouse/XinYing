package ssthouse.love.xinying.utils;

import android.content.Context;

/**
 * Created by ssthouse on 16/5/19.
 */
public class ImageUtil {

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", Constant.PACKAGE_NAME);
    }

}


