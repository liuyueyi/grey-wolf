import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yihui on 16/10/20.
 */
public class DateTest {

    @Test
    public void dataTest() throws ParseException {
        String begin = "2013-12-17";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date beginDate = simpleDateFormat.parse(begin);
        Date date = new Date();

        long day = date.getTime() - beginDate.getTime();
        System.out.println("date: + " + (day / (24 * 60 * 60 * 1000)));


    }
}
