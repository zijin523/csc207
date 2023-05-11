package transitutility;

import transitmodel.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class that reads the stored user data in the given file and made
 * relevant modifications on the user information each time they use
 * the system.
 */
public class ReadData {

    /**
     * A static method that reads the given file, which contains info in
     * the specified format. If there are cards already registered,
     * trips and cost info, add the info to the card holder.
     *
     * @return a ArrayList of card holders that has the updated info.
     */
    public static ArrayList<CardHolder> read() {
        ArrayList<CardHolder> users = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("src/data.txt");
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while (!((s = br.readLine()) == null)) {
                String[] data = s.split("_");
                CardHolder user = new CardHolder(data[0], data[1]);

                //Cards in used
                ArrayList<Card> cardBag = new ArrayList<>();
                if (!data[2].equals("")) {
                    String[] bag = data[2].split(";");
                    for (int i = 0; i < bag.length; i++) {
                        String[] cardInfo = bag[i].split(",");
                        Card card = new Card(user, cardInfo[0], new Double(cardInfo[3]));
                        card.setSuspended(new Boolean(cardInfo[1]));
                        card.setTrip_active(new Boolean(cardInfo[2]));
                        cardBag.add(card);
                    }
                }
                user.setActive_cards(cardBag);

                //Cards removed
                ArrayList<Card> cardRemove = new ArrayList<>();
                if (!data[3].equals("")) {
                    String[] remove = data[3].split(";");
                    for (int i = 0; i < remove.length; i++) {
                        String[] cardInfo = remove[i].split(",");
                        Card card = new Card(user, cardInfo[0], new Double(cardInfo[3]));
                        card.setSuspended(new Boolean(cardInfo[1]));
                        card.setTrip_active(new Boolean(cardInfo[2]));
                        cardRemove.add(card);
                    }
                }
                user.setSuspended_cards(cardRemove);

                //All Trips
                ArrayList<Trip> recentTrip = new ArrayList<>();
                if (!data[4].equals("")) {
                    String[] trips = data[4].split(";");
                    for (int i = 0; i < trips.length; i++) {
                        String[] trip = trips[i].split(",");
                        String[] routes = trip[trip.length - 1].split("->");
                        Trip t = null;
                        for (int j = 0; j <= routes.length - 1; j++) { //last element is ""
                            if (t == null) {
                                t = routes[j].contains(":b") ?
                                        new Trip(new Stop(routes[j], "Bus"), trip[0]) : new Trip(new Station(routes[j], "Subway"), trip[0]);
                                t.setHours(new Double(trip[1]));
                                t.setCosts(new Double(trip[2]));
                                t.setCompleted(new Boolean(trip[3]));
                            } else {
                                t.addRoute(routes[j].contains(":b") ? new Stop(routes[j], "Bus") : new Station(routes[j], "Subway"));
                            }
                        }
                        recentTrip.add(t);
                    }
                }
                user.setTrips(recentTrip);

                //costs
                HashMap<String, Double> costs = new HashMap<>();
                if (!data[5].equals("{}")) {
                    String[] cost = data[5].substring(1, data[5].length() - 1).split(",");
                    for (int i = 0; i < cost.length; i++) {
                        String[] monthlyCost = cost[i].split("=");
                        costs.put(monthlyCost[0], new Double(monthlyCost[1]));
                    }
                }
                user.setCosts(costs);
                users.add(user);

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
