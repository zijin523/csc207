package transitutility;

import java.util.Calendar;

/**
 * A class that gets the date with the correct format.
 */
public class GetDate {

    /**
     * A static method that gets the day and month, if month is
     * single digit add a 0 in the front; if day is single digit
     * add a 0 in the front.
     *
     * @return the correct date of the day that calls the method
     * in String.
     */
    public static String date() {
        Calendar d = Calendar.getInstance();
        int month = d.get(Calendar.MONTH) + 1;
        int day = d.get(Calendar.DAY_OF_MONTH);
        String date = "";
        if (Integer.toString(month).length() == 1) {
            date += "0";
        }
        date += Integer.toString(month);
        date += "-";
        if (Integer.toString(day).length() == 1) {
            date += "0";
        }
        date += Integer.toString(day);
        return date;
    }
}
