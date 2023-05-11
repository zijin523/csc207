package transitmodel;

/**
 * An abstract class of Vehicle in the transit system.
 */
public abstract class Vehicle {
    private final String vehicles_id; // Vehicle ID

    /**
     * Initialize the Vehicle with its ID.
     *
     * @param id ID of the Vehicle.
     */
    public Vehicle(String id) {
        this.vehicles_id = id;
    }
}
