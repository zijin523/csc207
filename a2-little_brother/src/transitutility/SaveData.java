package transitutility;

import transitmodel.CardHolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that provides the data saving function.
 */
public class SaveData {

    /**
     * A static method that stores any user information while the user is using the
     * system
     *
     * @param users an ArrayList of card holder users.
     */
    public static void save(ArrayList<CardHolder> users) {
        try {
            File file = new File("src/data.txt");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (CardHolder user : users) {
                bw.append(user.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

