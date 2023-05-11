package transitmodel;

import java.util.ArrayList;

/**
 * A transit Card that can be used at bus/subway stations.
 */
public class Card {
    private final String card_number;
    private final CardHolder card_owner;
    private final ArrayList<Trip> trips;
    private boolean suspended;
    private boolean trip_active;
    private double balance;

    /**
     * Creat a Card with the given user, card number, the initial balance is default at 19.0.
     *
     * @param card_owner  The CardHolder of this Card.
     * @param card_number The card number of this Card.
     */
    public Card(CardHolder card_owner, String card_number) {
        this(card_owner, card_number, 19.0);
    }

    /**
     * Creat a Card with the given user, card number, and initial balance
     *
     * @param card_owner  The CardHolder of this Card.
     * @param card_number The card number of this Card.
     * @param balance     The initial balance of this Card.
     */
    public Card(CardHolder card_owner, String card_number, Double balance) {
        this.card_owner = card_owner;
        this.card_number = card_number;
        this.suspended = false;
        this.trip_active = false;
        this.balance = balance;
        this.trips = new ArrayList<>();
    }

    /**
     * Return the card number of this Card.
     *
     * @return The card number of this Card.
     */
    public String getCardNumber() {
        return this.card_number;
    }

    /**
     * Return the CardHolder of this Card.
     *
     * @return the CardHolder of this Card.
     */
    public CardHolder getOwner() {
        return this.card_owner;
    }

    /**
     * Return the status of this Card.
     *
     * @return True if the card has not been suspended, false otherwise.
     */
    public boolean isNormal() {
        return !this.suspended;
    }

    /**
     * Return the current balance of this Card.
     *
     * @return The current balance of this Card.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Top up this Card by given amount, given that the card is in normal status.
     *
     * @param amount Amount to be topped up.
     * @return True if the top up is successful, false otherwise.
     */
    public boolean topup(double amount) {
        if (!this.isNormal()) return false;
        this.balance += amount;
        return true;
    }

    /**
     * Charge this Card by given amount, given that the current balance is positive.
     *
     * @param amount Amount to be debited to this Card.
     */
    public void charge(double amount, String date) {
        this.balance -= amount;
        this.card_owner.updateDailyCost(amount, date);
    }

    /**
     * Suspend this Card, given that the Card is not on trip.
     *
     * @return True if the suspension is successful, false otherwise.
     */
    public boolean suspend() {
        if (this.trip_active) return false;
        this.suspended = true;
        return true;
    }

    /**
     * Return whether this Card is on trip.
     *
     * @return True if this Card is on trip, false otherwise.
     */
    public boolean isOnTrip() {
        return this.trip_active;
    }

    /**
     * Return whether this Card can start a Trip, true iif this Card is not been suspended,
     * has a positive balance and is not already on a Trip.
     *
     * @return true if the Card can be used to start a Trip, false otherwise.
     */
    public boolean canUse() {
        return !this.suspended;
    }

    /**
     * Add a Trip to this Card and start the Trip.
     *
     * @param trip the Trip to be added.
     */
    public void addTrip(Trip trip) {
        this.trips.add(trip);
        this.trip_active = true;
        this.card_owner.getDailyTrip().add(trip);
        this.card_owner.getTotalTrip().add(trip);
    }

    /**
     * Tap out of the Trip, charge the Card, and record cost.
     *
     * @param trip The completed trip.
     */
    public void TapOut(Trip trip) {
        this.trip_active = false;
    }


    /**
     * Initialization purposes only! Set suspension status to this Card.
     *
     * @param b The suspension status.
     */
    public void setSuspended(boolean b) {
        this.suspended = b;
    }

    /**
     * Initialization purposes only! Set trip_active status to this Card.
     *
     * @param b The trip_active status.
     */
    public void setTrip_active(boolean b) {
        this.trip_active = b;
    }

    /**
     * Override equals method to compare the card number.
     *
     * @param obj The other object to be compared.
     * @return True if obj is a Card object and has the same card number, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card)
            return this.card_number.equals(((Card) obj).card_number);
        return false;
    }

    /**
     * Determine whether the trip is in trips.
     *
     * @param t a trip.
     * @return true if the trip exits, false otherwise.
     */
    public boolean containTrip(Trip t) {
        return this.trips.contains(t);
    }

    /**
     * Override toString method to out output all the information about this Card.
     *
     * @return All Card information.
     */
    @Override
    public String toString() {
        return this.card_number + "," + this.suspended + "," +
                this.trip_active + "," + this.balance;
    }
}