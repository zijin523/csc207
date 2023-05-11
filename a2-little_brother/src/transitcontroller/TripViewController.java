package transitcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import transitmodel.Card;
import transitmodel.Route;
import transitmodel.Trip;
import transitutility.GetDate;
import transitutility.LinesReader;
import transitutility.PossibleNext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A TripViewController that controls the Trip class and is a EventHandler for the TripView class.
 */
public class TripViewController implements EventHandler<ActionEvent> {
    static final HashMap<String, ArrayList<Route>> lines = LinesReader.readLines();
    private static Card card;
    private static Trip t;

    static {
        PossibleNext.possibleLines(lines);
    }

    /**
     * Get the trip.
     *
     * @return the trip.
     */
    public static Trip getTrip() {
        return t;
    }

    /**
     * Get the card.
     *
     * @return the card.
     */
    public static Card getCard() {
        return card;
    }

    /**
     * Set the Card of this TripViewController.
     *
     * @param card the Card
     */
    public static void setCard(Card card) {
        TripViewController.card = card;
    }

    /**
     * Handle 3 different types of action by the CardHolder including Start/Stop the Trip, and go to next Route.
     *
     * @param event the event that triggers this TripViewController.
     */
    @Override
    public void handle(ActionEvent event) {
        String operation = ((Button) event.getSource()).getText();
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String date = GetDate.date();
        switch (operation) {
            case "Start Trip":
                System.out.println("------------------");
                System.out.println(card.getCardNumber());
                System.out.println("Today is : " + date);
                String line = "";
                Route route = null;
                boolean valid_line = false;
                boolean valid_route = false;
                if (t != null && !t.is_completed()) {
                    System.out.println("Automatic settlement of an unfinished trip.");
                    TripViewController.card.TapOut(t);
                    t.completed_trip();
                    t = null;
                }
                do {
                    System.out.println("--Choose your line--");
                    System.out.println(lines.keySet());
                    System.out.print("Please input your line (Enter 0 to exit): ");
                    try {
                        String input = br.readLine();
                        if (input.equals("0")) {
                            return;
                        }
                        if (lines.containsKey(input)) {
                            valid_line = true;
                            line = input;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (!valid_line);
                do {
                    System.out.println("--Choose start station/stop--");
                    System.out.println(lines.get(line));
                    System.out.print("Please input your station/stop (0 to exit): ");
                    try {
                        String input = br.readLine();
                        if (input.equals("0")) {
                            return;
                        }
                        for (Route r : lines.get(line)) {
                            if (input.equals(r.toString())) {
                                route = r;
                                valid_route = true;
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (!valid_route);
                if (route.get_type().equals("Bus") && TripViewController.card.getBalance() <= 2) {
                    System.out.println("Please recharge if the balance is insufficient");
                    break;
                }
                if (t != null && t.getLastRoute().toString().equals(route.toString()) && TripViewController.card.containTrip(t)) {
                    t.setCompleted(false);
                    if (t.get_hours() >= 2 &&
                            !route.get_type().equals(t.getLastRoute().get_type()) &&
                            t.isMax()) {
                        t.completed_trip();
                        String d2 = t.get_date();
                        t = new Trip(route, d2);
                        TripViewController.card.addTrip(t);
                        TripViewController.card.charge(t.get_costs(), d2);
                        System.out.println("You have reached largest time. You have begun a new Trip.");
                    } else if (route.get_type().equals("Bus") && t.getLastRoute().get_type().equals("Bus")) {
                        double old_costs = t.get_costs();
                        t.next_route(route);
                        if (t.get_costs() + 2.0 >= 6 && t.get_hours() <= 2) {
                            t.setCosts(6.0);
                        } else {
                            t.setCosts(t.get_costs() + 2.0);
                        }
                        TripViewController.card.charge(t.get_costs() - old_costs, date);
                    } else {
                        double old_costs = t.get_costs();
                        t.next_route(route);
                        TripViewController.card.charge(t.get_costs() - old_costs, date);
                    }
                    System.out.println("You have started at " + route.toString() + ". Enjoy your trip!");
                    break;
                } else {
                    t = new Trip(route, date);
                    TripViewController.card.setTrip_active(true);
                    TripViewController.card.addTrip(t);
                    TripViewController.card.charge(t.get_costs(), date);
                    System.out.println("You have started at " + route.toString() + ". Enjoy your trip!");
                    break;
                }
            case "Next Station/Stop":
                System.out.println("------------------");
                if (t == null) {
                    System.out.println("Please start a new trip.");
                    break;
                } else if (t.is_completed()) {
                    System.out.println("Please start a new trip.");
                    break;
                } else {
                    System.out.println("Today is : " + date);
                    Route next_r = null;
                    boolean valid_next = false;
                    do {
                        System.out.println("--Choose your next station/stop--");
                        System.out.println("You possible next station/stop: " + PossibleNext.next_infor(t.getLastRoute().get_next()));
                        System.out.print("Please enter your next station/stop (0 to exit): ");
                        try {
                            String input = br.readLine();
                            if (input.equals("0")) {
                                return;
                            }
                            String[] p = input.split("-");
                            if (p.length == 1) {
                                System.out.println("Please input routeName-lineName");
                            } else {
                                for (Route i : t.getLastRoute().get_next()) {
                                    if (i.toString().equals(p[0]) && i.getLineName().equals(p[1])) {
                                        next_r = i;
                                        valid_next = true;
                                        break;
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } while (!valid_next);
                    if (t.get_hours() >= 2 &&
                            !next_r.get_type().equals(t.getLastRoute().get_type()) &&
                            t.isMax()) {
                        t.completed_trip();
                        String d2 = t.get_date();
                        t = new Trip(next_r, d2);
                        if (t.get_costs() > TripViewController.card.getBalance()) {
                            System.out.println("Please recharge your card.");
                            break;
                        }
                        TripViewController.card.addTrip(t);
                        TripViewController.card.charge(t.get_costs(), date);
                        System.out.println("You have reached largest time. You have begun a new Trip.");

                    } else {
                        double cost = t.get_costs();
                        double hour = t.get_hours();
                        t.next_route(next_r);
                        if (t.get_costs() > TripViewController.card.getBalance()) {
                            System.out.println("Please recharge your card.");
                            t.setCosts(cost);
                            t.getLines().remove(t.getLines().size() - 1);
                            t.setHours(hour);
                            break;
                        } else {
                            System.out.println("You are now at: " + next_r.toString());
                            TripViewController.card.charge(t.get_costs() - cost, date);
                        }
                    }
                }
                break;
            case "End Trip":
                System.out.println("------------------");
                if (t == null) {
                    System.out.println("Please start a new trip.");
                    break;
                } else if (t.is_completed()) {
                    System.out.println("Please start a new trip.");
                    break;
                } else {
                    System.out.println("Today is : " + date);
                    TripViewController.card.setTrip_active(false);
                    t.completed_trip();
                    TripViewController.card.TapOut(t);
                    System.out.println("Trip completed! Thank you! Have a nice day!");
                }
                break;
        }
    }

}
