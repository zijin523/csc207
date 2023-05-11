package transitmodel;

import java.util.ArrayList;

/**
 * A Trip class that represents a completed or uncompleted trip.
 */
public class Trip {
    public static final double max_costs = 6.0;
    public static final double time_costs_subway = 0.2;
    public static final double time_costs_bus = 0.1;
    public static final double time_price = 0.1;

    private final String date;
    private final ArrayList<Route> lines;
    private double hours;
    private boolean completed;
    private double costs;

    /**
     * Initialize a Trip with a Route and date.
     *
     * @param r    Route of this trip.
     * @param date Date of this trip.
     */
    public Trip(Route r, String date) {
        this.date = date;
        this.hours = 0.0;
        this.completed = false;
        this.costs = r.get_type().equals("Bus") ? 2.0 : 0.0;
        this.lines = new ArrayList<Route>() {
        };
        this.lines.add(r);
    }

    /**
     * Return the current cost of this Trip.
     *
     * @return The current cost.
     */
    public double get_costs() {
        return this.costs;
    }

    /**
     * Return the date of this Trip.
     *
     * @return The date of this Trip.
     */
    public String get_date() {
        return this.date;
    }

    /**
     * Set this Trip as completed.
     */
    public void completed_trip() {
        this.completed = true;
    }

    /**
     * Return whether this Trip has completed.
     *
     * @return True if the Trip has completed, false otherwise.
     */
    public boolean is_completed() {
        return this.completed;
    }

    /**
     * Get the duration of this Trip in hours.
     *
     * @return The duration of this Trip in hours.
     */
    public double get_hours() {
        return this.hours;
    }

    /**
     * Set this Trip to go to next Route.
     *
     * @param r The Route to go.
     */
    public void next_route(Route r) {
        String last_type = this.getLastRoute().get_type();
        String last_name = this.getLastRoute().toString();
        if (r.get_type().equals("Bus") && r.get_type().equals(last_type)) {
            if (this.costs + (time_costs_bus * time_price) <= 6) {
                this.costs += (time_costs_bus * time_price);
            } else {
                this.costs = 6.0;
            }
            if (r.toString().equals(last_name)) {
                this.hours += 0.0;
            } else {
                this.hours += time_costs_bus;
                this.lines.add(r);
            }
        } else if (r.get_type().equals("Bus") && !r.get_type().equals(last_type)) {
            if (this.costs + (time_costs_bus * time_price) + 2 <= 6) {
                this.costs += (time_costs_bus * time_price) + 2;
            }
            this.hours += time_costs_bus;
            this.lines.add(r);
        } else {
            if (this.costs + (time_costs_subway * time_price) + 0.5 <= 6) {
                this.costs += (time_costs_subway * time_price);
                if (last_type.equals(r.get_type()) && r.toString().equals(last_name)) {
                    this.costs += 0.0;
                } else {
                    this.costs += 0.5;
                }
            } else {
                this.costs = 6.0;
            }
            if (r.toString().equals(last_name) && r.get_type().equals(last_type)) {
                this.hours += 0.0;
            } else {
                this.hours += time_costs_subway;
                this.lines.add(r);
            }
        }

    }

    public ArrayList<Route> getLines() {
        return this.lines;
    }

    /**
     * Set duration of this Trip in hours.
     *
     * @param hour The duration of this Trip in hours.
     */
    public void setHours(double hour) {
        this.hours = hour;
    }

    /**
     * Set the cost of this Trip.
     *
     * @param cost The cost of this Trip.
     */
    public void setCosts(double cost) {
        this.costs = cost;
    }

    /**
     * Get the last Route of this Trip (the latest location).
     *
     * @return The last Route of this Trip.
     */
    public Route getLastRoute() {
        return this.lines.get(this.lines.size() - 1);
    }

    /**
     * Add a Route (such as Station and Stop) to this Trip.
     *
     * @param r The Route to be added.
     */
    public void addRoute(Route r) {
        this.lines.add(r);
    }

    /**
     * Check if the cost of this Trip is at maximum.
     *
     * @return True if the cost is at maximum, false otherwise.
     */
    public boolean isMax() {
        return this.costs == max_costs;
    }

    /**
     * Get the number of Route (such as Station and Stop) of this Trip.
     *
     * @return number of Route
     */
    public int getNumberRoute() {
        return this.lines.size();
    }

    /**
     * Get the number of routes that is contained.
     *
     * @return the number of how many routes are in the line.
     */
    public int getTotalRoutes() {
        int result = 0;
        ArrayList<Route> dup = new ArrayList<Route>();
        for (Route r : this.lines) {
            if (!dup.contains(r)) {
                dup.add(r);
                result++;
            }
        }
        return result;
    }

    /**
     * Set the trip status either completed or not.
     *
     * @param b a boolean value
     */
    public void setCompleted(boolean b) {
        this.completed = b;
    }

    /**
     * Override the toString method to output the String representation of this Trip.
     *
     * @return String representation of this Trip
     */
    @Override
    public String toString() {
        String l = "";
        for (Route line : this.lines) {
            l += line.toString() + "->";
        }
        return this.date + "," + this.hours + "," + this.costs + "," + this.completed + "," + l;
    }

    /**
     * Return a String representation of Trip information to the frontend.
     *
     * @return Trip information
     */
    public String tripInfo() {
        return "Date: " + this.date + ", Route: " + this.lines +
                ", Cost: $" + String.format("%1.2f", this.costs) + ", Duration: " + String.format("%1.2f", this.hours) +
                " hour(s), Completed: " + this.completed;
    }
}
