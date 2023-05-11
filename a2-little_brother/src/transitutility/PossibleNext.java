package transitutility;

import transitmodel.Route;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class to get the possible next station/stop on the line
 * of the trip.
 */
public class PossibleNext {

    /**
     * A static method that takes in the lines of the transit and based
     * on the current station/stop, returns all the possible next ones.
     *
     * @param lines a HashMap of String and ArrayList that represents
     *              a line and its stops/stations.
     */
    public static void possibleLines(HashMap<String, ArrayList<Route>> lines) {
        HashMap<String, ArrayList<Route>> l = lines;
        ArrayList<Route> routes = new ArrayList<Route>();
        for (String i : l.keySet()) {
            for (Route j : l.get(i)) {
                routes.add(j);
            }
        }

        for (Route r : routes) {
            for (String i : l.keySet()) {
                for (int j = 0; j < l.get(i).size(); j++) {
                    if (l.get(i).get(j).toString().equals(r.toString()) && j + 1 < l.get(i).size()) {
                        if (l.get(i).get(j).get_type() != r.get_type()) {
                            r.add_next(l.get(i).get(j));
                        } else if (r.get_type().equals("Bus")) {
                            String curr = r.getLineName().substring(0, 3);
                            String comp = l.get(i).get(j).getLineName().substring(0, 3);
                            if (!curr.equals(comp)) {
                                r.add_next(l.get(i).get(j));
                            } else if (!r.getLineName().substring(0, 4).equals(l.get(i).get(j).getLineName().substring(0, 4))) {
                                r.add_next(l.get(i).get(j + 1));
                            } else if (r.getLineName().substring(4).equals(l.get(i).get(j).getLineName().substring(4))) {
                                r.add_next(l.get(i).get(j + 1));
                            }
                        } else {
                            r.add_next(l.get(i).get(j + 1));
                        }
                    }
                }
            }
        }
    }

    /**
     * A static method that returns the collection of the next stops/stations.
     *
     * @param l an ArrayList of routes.
     * @return an ArrayList of String names of stops/stations.
     */
    public static ArrayList<String> next_infor(ArrayList<Route> l) {
        ArrayList<String> result = new ArrayList<String>();
        for (Route i : l) {
            result.add(i.name + "-" + i.getLineName());
        }
        return result;

    }

}
