package transitutility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class that represents the user registration.
 */
public class Register {

    /**
     * A static method that checks the input name, email and the user role from
     * the given file, which is either admin or user, and stores the new user
     * info in the given file.
     *
     * @param name  the name of the user.
     * @param email the email of the user.
     * @param role  the role of the user, either admin or user.
     * @return true if the newly registered user is added to the given file,
     * or false otherwise.
     */
    public static boolean save(String name, String email, String role) {
        try {
            File file = new File("src/auth.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            String str = name + "," + email + "," + role;
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(str);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
