package ssthouse.love.xinying.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ssthouse on 16/5/9.
 */
public class StringUtils {


    public static String getLoveTimeStr() {
        Calendar beginCalendar = new GregorianCalendar(2016, 3, 14);
        Calendar currentCalendar = Calendar.getInstance();
        //return "已相爱: " + (currentCalendar.getTimeInMillis() - loveBeginCalendar.getTimeInMillis()) / (1000 * 60 * 60 * 24) + "天"
        return "已相爱" + (currentCalendar.getTimeInMillis() - beginCalendar.getTimeInMillis()) / (1000 * 60 * 60 * 24) + "天";
    }

}
