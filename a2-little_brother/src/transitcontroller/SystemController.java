package transitcontroller;

import transitmodel.CardHolder;
import transitview.SystemView;

import java.util.ArrayList;

/**
 * The SystemController that controls the the Transit System.
 */
public class SystemController {
    private static boolean status = true;
    private static AdminUserController admin;
    private static ArrayList<CardHolder> cardHolders;

    /**
     * Get the status of the view.
     *
     * @return true if the system is opened, false otherwise.
     */
    public static boolean getStatus() {
        return SystemController.status;
    }

    /**
     * Set the status of the view.
     *
     * @param b either be true or false.
     */
    public static void setStatus(boolean b) {
        SystemController.status = b;
    }

    /**
     * Set the AdminUser of this system.
     *
     * @param admin the AdminUser of this system.
     */
    public static void setAdmin(AdminUserController admin) {
        SystemController.admin = admin;
    }

    /**
     * Get the card holders loaded in the system view.
     *
     * @return an ArrayList of card holders.
     */
    public static ArrayList<CardHolder> getCardHolders() {
        return cardHolders;
    }

    /**
     * Set the card holders in this view
     *
     * @param cardHolders an ArrayList of card holders
     */
    public static void setCardHolders(ArrayList<CardHolder> cardHolders) {
        SystemController.cardHolders = cardHolders;
    }

    /**
     * Open the main page of the system.
     *
     * @param args args that passes along
     */
    public void openSystemView(String[] args) {
        SystemView.main(args);
    }
}

