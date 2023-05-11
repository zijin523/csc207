package transitmodel;

import java.util.ArrayList;

/**
 * The AdminUser class representing a admin user of the system.
 */
public class AdminUser extends User {
    private final ArrayList<CardHolder> user_list;

    /**
     * Initialize the AdminUser class by giving the name, email and user_list.
     *
     * @param name      The name of this AdminUser.
     * @param email     The email of this AdminUser.
     * @param user_list The ArrayList of all CardHolder.
     */
    public AdminUser(String name, String email, ArrayList<CardHolder> user_list) {
        super(name, email);
        this.user_list = user_list;
    }

    /**
     * Add a CardHolder to the user_list given the user doesn't exist in user_list.
     *
     * @param user The CardHolder to be added.
     * @return True if user doesn't already exist in user_list, false otherwise.
     */
    public boolean addUser(CardHolder user) {
        if (this.isUserExist(user)) return false;
        this.user_list.add(user);
        return true;
    }

    /**
     * Remove a CardHolder to the user_list given the user exist in user_list.
     *
     * @param user The CardHolder to be removed
     * @return True if user already exist in user_list, false otherwise.
     */
    public boolean removeUser(CardHolder user) {
        if (!this.isUserExist(user)) return false;
        this.user_list.remove(user);
        return true;
    }

    /**
     * Determine whether the user is in the user list.
     *
     * @param user a card holder
     * @return true if the user is in the user list, false otherwise.
     */
    private boolean isUserExist(CardHolder user) {
        return this.user_list.contains(user);
    }

    /**
     * Generate a daily report of the system containing the traffic,
     * daily revenue and other statistics.
     *
     * @return The message to be presented to the end user.
     */
    public String getDailyReport(String date) {
        double revenue = 0.0;
        int total_trips = 0;
        int traffic = 0;
        for (CardHolder user : user_list) {
            for (Trip trip : user.getDailyTrip()) {
                if (trip.get_date().equals(date)) {
                    traffic += trip.getTotalRoutes();
                    ++total_trips;
                    revenue += trip.get_costs();
                }
            }
        }
        return String.format("---Daily Report---\nDate: %s\nRoutes arrived number: %o routes" +
                "\nTotal trips: %o trips\n", date, traffic, total_trips) + "Revenue: " + String.format("%1.2f", revenue) + "\n------------------";
    }

    /**
     * End the day, clear all daily trip ArrayLists for all CardHolders.
     */
    public void endDay() {
        for (CardHolder user : user_list) {
            user.getDailyTrip().clear();
        }
    }

}
