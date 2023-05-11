package transitutility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that checks the user name correctness.
 */
public class CheckName {

    /**
     * A static method checks if name is in a correct String pattern,
     * which is composed of letters and numbers only.
     *
     * @param name the name of the user.
     * @return true if name is composed of letters and numbers only, false otherwise.
     */
    public static boolean check(String name) {
        if (name == null)
            return false;
        String rule = "^[a-zA-Z]{1}+[a-z]+$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
