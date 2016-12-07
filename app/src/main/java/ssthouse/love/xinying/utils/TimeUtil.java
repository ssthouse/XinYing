package ssthouse.love.xinying.utils;

import java.util.Date;

/**
 * Created by ssthouse on 07/12/2016.
 */

public class TimeUtil {


    /**
     * juge if the timeStampAfter is after timeStampBefore more than a day
     *
     * @param timeStampAfter
     * @param timeStampBefore
     * @return
     */
    public static boolean isAfterDays(long timeStampAfter, long timeStampBefore) {
        Date lastSignDate = new Date(timeStampBefore);
        Date currentDate = new Date(timeStampAfter);
        boolean isAfterDays = false;
        if (currentDate.getYear() > lastSignDate.getYear())
            isAfterDays = true;
        if (currentDate.getYear() == lastSignDate.getYear() && currentDate.getMonth() > lastSignDate.getMonth())
            isAfterDays = true;
        if (currentDate.getYear() == lastSignDate.getYear() && currentDate.getMonth() == lastSignDate.getMonth()
                && currentDate.getDate() > lastSignDate.getDate())
            isAfterDays = true;
        return isAfterDays;

    }
}
