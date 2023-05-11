package transitutility;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * A class that represents the name changing.
 */
public class ChangeName {
    /**
     * A static method that changes the user name and updates the change in the user file.
     *
     * @param newName the String name that the user want to change to.
     * @param oldName the String name of the user
     * @param email   the email of the user
     */
    public static void change(String newName, String oldName, String email) {
        try {
            FileInputStream fis = new FileInputStream("src/auth.txt");
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String s;
            ArrayList<String> auth = new ArrayList<>();
            while (!((s = br.readLine()) == null)) {
                String[] user = s.split(",");
                if (user[0].equals(oldName) && user[1].equals(email)) {
                    s = newName + "," + user[1] + "," + user[2];
                }
                auth.add(s);
            }
            br.close();
            File file = new File("src/auth.txt");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String str : auth) {
                bw.append(str);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
