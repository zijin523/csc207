package transitmodel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The CardHolder class representing a card holder of the system.
 */
public class CardHolder extends User {
    private final ArrayList<Trip> daily_trips;
    private ArrayList<Card> active_cards;
    private ArrayList<Card> suspended_cards;
    private ArrayList<Trip> trips;
    private HashMap<String, Double> costs;
    private double daily_costs;

    /**
     * Initialize the CardHolder class by giving the name and email.
     *
     * @param name  The name of this CardHolder.
     * @param email The email of this CardHolder.
     */
    public CardHolder(String name, String email) {
        super(name, email);
        this.active_cards = new ArrayList<>();
        this.suspended_cards = new ArrayList<>();
        this.trips = new ArrayList<>();
        this.daily_trips = new ArrayList<>();
        this.costs = new HashMap<>();
        this.daily_costs = 0.0;
    }

    /**
     * Register a Card to this CardHolder, given the Card wasn't added to this CardHolder previously.
     *
     * @param card The Card to be registered.
     * @return True if the registration is successful, false otherwise.
     */
    public boolean registerCard(Card card) {
        if (this.isCardExist(card)) return false;
        this.active_cards.add(card);
        return true;
    }

    /**
     * Suspend the given Card given this CardHolder owns the Card and Card status is normal.
     *
     * @param card the Card to be suspended.
     * @return True if the suspension was successful, false otherwise.
     */
    public boolean suspendCard(Card card) {
        if (!this.isCardExist(card)) return false;
        boolean success = card.suspend();
        if (success) {
            this.active_cards.remove(card);
            this.suspended_cards.add(card);
        }
        return success;
    }

    /**
     * Return all active (not suspended) Cards.
     *
     * @return ArrayList of all active Cards.
     */
    public ArrayList<Card> getCardBag() {
        return this.active_cards;
    }

    /**
     * Top up the Card by given amount, given that the CardHolder owns the card,
     * and the card isn't been suspended.
     *
     * @param card   The Card to be topped up.
     * @param amount The amount to be topped up.
     * @return True if the top up was successful, false otherwise.
     */
    public boolean topup(Card card, Double amount) {
        if (!this.isCardExist(card)) return false;
        return card.topup(amount);
    }

    /**
     * Updates the daily cost of the user, including month and personal consumption.
     *
     * @param cost how much the trip costs.
     * @param date when does the trip take place.
     */
    public void updateDailyCost(double cost, String date) {
        String month = date.substring(0, 2).replaceFirst("^0*", "");
        double old_cost = this.costs.getOrDefault(month, 0.0);
        this.costs.put(month, old_cost + cost);
        this.daily_costs += cost;
    }

    /**
     * Get the daily cost of this CardHolder.
     *
     * @return The daily cost.
     */
    public double getDailyCost() {
        return this.daily_costs;
    }

    /**
     * Return an ArrayList of recent three trips.
     *
     * @return Recent 3 trips, or all trips if the CardHolder only has less than 3 trips.
     */
    public ArrayList<Trip> getRecentTrips() {
        int number_of_trips = this.trips.size();
        if (number_of_trips <= 3) return this.trips;
        return new ArrayList<>(this.trips.subList(number_of_trips - 3, number_of_trips));
    }

    /**
     * Return the average monthly cost of this CardHolder (total cost / number of month that has at least on Trip).
     *
     * @return Average monthly cost.
     */
    public double averageMonthlyCost() {
        int number_month = 0;
        double total_cost = 0.0;
        for (Double value : this.costs.values()) {
            if (value > 0) ++number_month;
            total_cost += value;
        }
        if (number_month == 0) return 0.0;
        return total_cost / number_month;
    }

    /**
     * Return a HashMap of all costs of this CardHolder,
     * where key is the month and value is the cost of that month.
     *
     * @return All cost of this CardHolder
     */
    public HashMap<String, Double> getCosts() {
        return this.costs;
    }

    /**
     * Initialization purposes only! Set cost to this CardHolder.
     *
     * @param cost HashMap of cost to be set as cost of this CardHolder.
     */
    public void setCosts(HashMap<String, Double> cost) {
        this.costs = cost;
    }

    /**
     * Determine whether the card is a active card.
     *
     * @param card a card.
     * @return true if the card is inside active_cards, false otherwise.
     */
    private boolean isCardExist(Card card) {
        return this.active_cards.contains(card);
    }

    /**
     * Initialization purposes only! Set active_cards to this CardHolder.
     *
     * @param cards Cards to be set as active_cards of this CardHolder.
     */
    public void setActive_cards(ArrayList<Card> cards) {
        this.active_cards = cards;
    }

    /**
     * Initialization purposes only! Set suspended_cards to this CardHolder.
     *
     * @param cards Cards to be set as suspended_cards of this CardHolder.
     */
    public void setSuspended_cards(ArrayList<Card> cards) {
        this.suspended_cards = cards;
    }

    /**
     * Initialization purposes only! Set trips to this CardHolder.
     *
     * @param trips Trips to be set as trips of this CardHolder.
     */
    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Return all Trips that belong to this CardHolder.
     *
     * @return All trips of this CardHolder.
     */
    public ArrayList<Trip> getTotalTrip() {
        return this.trips;
    }

    /**
     * Return daily trips of this CardHolder.
     *
     * @return Daily trips of this CardHolder.
     */
    public ArrayList<Trip> getDailyTrip() {
        return this.daily_trips;
    }

    /**
     * Override toString method to output all information about this CardHolder.
     *
     * @return All information about this CardHolder.
     */
    @Override
    public String toString() {
        String info = this.getName() + "_" + this.getEmail();
        String active = "";
        for (Card card : this.active_cards) {
            active += card.toString() + ";";
        }
        String removed = "";
        for (Card card : this.suspended_cards) {
            removed += card.toString() + ";";
        }
        String trip = "";
        for (Trip t : this.trips) {
            trip += t.toString() + ";";
        }
        return info + "_" + active + "_" + removed + "_" + trip + "_" + costs.toString();
    }
}