package transitutility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for checking the pattern of the email address.
 */
public class CheckEmail {

    /**
     * A static method for checking email address pattern correctness,
     * which is composed of letters, numbers, and @ .
     *
     * @param email the email of the user.
     * @return either true if email has the correct email address pattern or false otherwise.
     */
    public static boolean check(String email) {
        if (email == null)
            return false;
        String rule = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
