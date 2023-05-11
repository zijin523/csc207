package transitmodel;

/**
 * A bus in the transit system.
 */
public class Bus extends Vehicle {
    public static final double price = 2.0; // price

    /**
     * Initialize this Bus with its id.
     *
     * @param id ID of this Bus.
     */
    public Bus(String id) {
        super(id);
    }
}