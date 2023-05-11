package transitutility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * A class for log in of the system.
 */
public class Login {

    /**
     * A static method that checks the user information: the user is either
     * an administrator or a card holder user.
     *
     * @param name  the name of the user.
     * @param email the email of the user.
     * @return the role of user as a String from the given user list file.
     */
    public static String check(String name, String email) {
        String role = "";
        try {
            FileInputStream fis = new FileInputStream("src/auth.txt");
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String s;
            while (!((s = br.readLine()) == null)) {
                String[] userInfo = s.split(",");
                if (userInfo[0].equals(name) && userInfo[1].equals(email)) {
                    role = userInfo[2];
                    return role;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return role;
    }
}
