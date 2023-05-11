package transitcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import transitmodel.AdminUser;
import transitmodel.CardHolder;
import transitutility.GetDate;
import transitutility.ReadData;
import transitutility.SaveData;
import transitview.CardHolderView;

import java.util.ArrayList;

/**
 * A AdminUserController that controls the AdminUser class and is a EventHandler for the AdminUserView class.
 */
public class AdminUserController implements EventHandler<ActionEvent> {
    private static final ArrayList<CardHolder> cardHolder = new ArrayList<>();
    private static AdminUser adminUser;

    /**
     * Return the AdminUser of this system.
     *
     * @return the AdminUser
     */
    public static AdminUser getAdminUsers() {
        return adminUser;
    }

    /**
     * Set the AdminUser of this system.
     *
     * @param adminUser the AdminUser
     */
    public static void setAdminUsers(AdminUser adminUser) {
        AdminUserController.adminUser = adminUser;
    }

    /**
     * Return an ArrayList of all CardHolders.
     *
     * @return the CardHolders.
     */
    public static ArrayList<CardHolder> getCardHolders() {
        return cardHolder;
    }

    /**
     * Handle 3 different types of action by the AdminUser including Opening/Closing the Transit System,
     * as well as generating Daily Report
     *
     * @param event the event that triggers this AdminUserController.
     */
    @Override
    public void handle(ActionEvent event) {
        String operation = ((Button) event.getSource()).getText();
        switch (operation) {
            case "Daily Report":
                System.out.println(AdminUserController.adminUser.getDailyReport(GetDate.date()));
                break;
            case "Open":
                ArrayList<CardHolder> s = ReadData.read();
                SystemController.setCardHolders(s);
                CardHolderView.status = false;
                CardHolderView.operationControl.forEach(item -> item.setDisable(CardHolderView.status));
                System.out.println("System opened. Good morning.");
                break;
            case "Close":
                SaveData.save(SystemController.getCardHolders());
                CardHolderView.status = true;
                CardHolderView.operationControl.forEach(item -> item.setDisable(CardHolderView.status));
                adminUser.endDay();
                System.out.println("System closed. Good night.");
                break;
        }

    }
}
