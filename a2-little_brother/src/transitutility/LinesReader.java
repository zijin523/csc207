package transitutility;

import transitmodel.Route;
import transitmodel.Station;
import transitmodel.Stop;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class that reads the transit lines in the given file.
 */
public class LinesReader {

    /**
     * A static method that reads the given file with a specified format
     * then gets the stops/stations and put them in an ArrayList.
     *
     * @return a HashMap of a String represents the line name and
     * an ArrayList of routes on that line.
     */
    public static HashMap<String, ArrayList<Route>> readLines() {
        HashMap<String, ArrayList<Route>> lines = new HashMap<String, ArrayList<Route>>();
        try {
            File f = new File("routes.txt");
            FileInputStream fis = new FileInputStream(f.getAbsolutePath());
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String lineName = null;
            String pointsStr = "";
            List<Route> pointsList = null;
            String s;
            while (true) {
                s = br.readLine();
                if (s == null || s.contains("subway") || s.contains("bus")) {
                    if (pointsList != null) {
                        String[] p = pointsStr.split("-");
                        for (int i = 0; i < p.length; i++) {
                            pointsList.add(p[i].endsWith(":s") ?
                                    new Station(p[i].substring(0, p[i].length() - 2), "Subway") :
                                    new Stop(p[i].substring(0, p[i].length() - 2), "Bus"));
                        }
                        lines.put(lineName, (ArrayList<Route>) pointsList);
                    }
                    if (s == null) {
                        break;
                    }
                    lineName = s;
                    pointsStr = "";
                    pointsList = new ArrayList<>();
                } else {
                    pointsStr += s;
                }
            }
            fis.close();
            isr.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String i : lines.keySet()) {
            for (Route j : lines.get(i)) {
                j.set_Lines(i);
            }
        }
        return lines;
    }

}
