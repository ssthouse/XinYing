package ssthouse.love.xinying.utils;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ssthouse on 07/12/2016.
 */

public class TimeUtil {


    /**
     * judge if the timeStampAfter is after timeStampBefore more than a day
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

    public static int dayInterval(final Date fistDate, final Date secDate) {
        Date[] dates = {new Date(fistDate.getTime()), new Date(secDate.getTime())};
        for (Date date : dates) {
            Logger.getGlobal().log(Level.SEVERE, date.toString());
            date.setTime(date.getTime() + 8 * 60 * 60 * 1000);
            date.setTime(date.getTime() - date.getTime() % (24 * 60 * 60 * 1000));
            Logger.getGlobal().log(Level.SEVERE, date.toString());
        }
        return (int) ((dates[0].getTime() - dates[1].getTime()) / (24 * 60 * 60 * 1000));
    }

}
