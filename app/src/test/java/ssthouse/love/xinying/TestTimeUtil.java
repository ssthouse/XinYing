package ssthouse.love.xinying;

import org.junit.Test;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import ssthouse.love.xinying.utils.TimeUtil;

import static org.junit.Assert.assertEquals;


/**
 * Created by ssthouse on 06/02/2017.
 */

public class TestTimeUtil {

    @Test
    public void testTImePeriod() {

        Date date = new Date();
        date.setHours(7);
        Date futherDate = new Date();
        futherDate.setDate(date.getDate() + 7);
        futherDate.setHours(date.getHours() + 5);

        Logger.getGlobal().log(Level.SEVERE, futherDate.toString());
        Logger.getGlobal().log(Level.SEVERE, date.toString());
        assertEquals(7, TimeUtil.dayInterval(futherDate, date));
    }

    @Test
    public void testTime() {
        Date date = new Date();
        Logger.getGlobal().log(Level.SEVERE, date.toString());
        date.setTime(date.getTime()- date.getTime() % (24 * 60 * 60 * 1000));
        Logger.getGlobal().log(Level.SEVERE, date.toString());
    }
}
