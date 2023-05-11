package transitmodel;

import java.util.ArrayList;

/**
 * An abstract class of Route in the transit system.
 */
public abstract class Route {
    public final String name;
    private final String type;
    private final ArrayList<Route> next;
    private String lines;

    /**
     * Initialize the name and type of this route.
     *
     * @param name route name
     * @param type route type
     * @param line vehicle_name
     */
    public Route(String name, String type) {
        this.name = name;
        this.type = type;
        this.next = new ArrayList<>();
        this.lines = "";
    }

    /**
     * Return the type of this Route.
     *
     * @return type of Route
     */
    public String get_type() {
        return this.type;
    }

    /**
     * Add next Route of this Route.
     *
     * @param r The next Route.
     */
    public void add_next(Route r) {
        this.next.add(r);
    }

    /**
     * Set the line.
     *
     * @param lineName the name of a line.
     */
    public void set_Lines(String lineName) {
        this.lines = lineName;
    }

    /**
     * Get the name of the line.
     *
     * @return a string represents the line.
     */
    public String getLineName() {
        return this.lines;
    }

    /**
     * Override the toString method to output the Route information.
     *
     * @return Route information
     */
    @Override
    public String toString() {
        return (type.equals("Bus")) ? this.name : this.name;
    }

    /**
     * Return the next Route
     *
     * @return The next Route.
     */
    public ArrayList<Route> get_next() {
        return this.next;
    }
}