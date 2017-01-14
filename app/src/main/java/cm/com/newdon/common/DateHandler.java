package cm.com.newdon.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * class with methods to handle date format
 */
public class DateHandler {

    /*returns different between future date and today in format DDD : HH : MM, e.g 020 : 12 : 45*/

    public static String getTimeCountDown(Date date){

        Date currentDay = new Date();
        long diffInMinutes =  (date.getTime() - currentDay.getTime())/(1000*60);
        int days = (int) (diffInMinutes/(24*60));
        int hours = (int) (diffInMinutes%(24*60)/24);
        int minutes = (int) (diffInMinutes%24);

        String sDays ="";
        if (days<10){
            sDays = "00"+days;
        }else if(days<100){
            sDays = "0"+days;
        }else sDays = "" + days;

        String sHours = (hours<10 ? "0" + hours : "" + hours);
        String sMinutes = (minutes<10 ? "0" + minutes : "" + minutes);

        return sDays + " : " + sHours + " : " + sMinutes;
    }

    public static String getDaySimpleFormat(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
