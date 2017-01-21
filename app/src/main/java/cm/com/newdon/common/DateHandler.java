package cm.com.newdon.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * class with methods to handle date format
 */

public class DateHandler {

    private static final int MINUTES_IN_DAY = 24*60;

    public enum Period {
        DAY("day"), WEEK ("week"), MONTH("month"), YEAR("year");

        private String period;

        Period(String period) {
            this.period = period;
        }

        public String getValue(){
            return period;
        }
    }

    /*returns different between future date and today in format DDD : HH : MM, e.g 020 : 12 : 45*/
    public static String getTimeCountDown(Date date){

        int diffInMinutes =  getDiffInMinutes(date);
        int days = (diffInMinutes/MINUTES_IN_DAY);
        int hours = (diffInMinutes%MINUTES_IN_DAY/24);
        int minutes = (diffInMinutes%24);

        String sDays;
        if (days<10){
            sDays = "00"+days;
        }else if(days<100){
            sDays = "0"+days;
        }else sDays = "" + days;

        String sHours = (hours<10 ? "0" + hours : "" + hours);
        String sMinutes = (minutes<10 ? "0" + minutes : "" + minutes);

        return sDays + " : " + sHours + " : " + sMinutes;
    }

//  returns String dd/MM/yyyy from Date
    public static String getDaySimpleFormat(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

//    returns Date from String "yyyy-mm-dd hh:mm:ss"
    public static Date parseDateFromString(String stringDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

//    returns String - how long ago was the Date, e.g. 3 days ago, 1 week ago, 2 months ago
    public static String howLongAgoWasDate(Date date){
        Period period;
        int number;

        int diffInMinutes = getDiffInMinutes(date);
        if (diffInMinutes<MINUTES_IN_DAY){
            return "today";
        }else if (diffInMinutes<MINUTES_IN_DAY*2){
            return "yesterday";
        }else {
            int days = diffInMinutes/MINUTES_IN_DAY;
            if (days<7){
                number = days;
                period = Period.DAY;
            }else if (days<30){
                number = days/4;
                period = Period.WEEK;
            }else if (days<365){
                number = days/30;
                period = Period.MONTH;
            }else {
                number = days/365;
                period = Period.YEAR;
            }
        }

        StringBuilder sb = new StringBuilder();

        sb = sb.append(number + " ");
        sb = sb.append(period.getValue());
        if(number>1){
            sb.append("s");
        }
        sb.append(" ago");
        return sb.toString();
    }

    private static int getDiffInMinutes(Date date){
        Date currentDay = new Date();
        return (int) Math.abs((date.getTime() - currentDay.getTime())/(1000*60));
    }
}
