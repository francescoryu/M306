package backend;

import java.util.Map;
import java.util.TreeMap;

public class Messwerte {

    TreeMap<Long, Double> bezug = new TreeMap<Long, Double>();
    TreeMap<Long, Double> einspeisung = new TreeMap<Long, Double>();

    public Messwerte(){
    }

    public void addBezug(long timestamp, double messwert) {
        bezug.put(timestamp, messwert);
    }

    public void addEinspeisung(long timestamp, double messwert) {
        einspeisung.put(timestamp, messwert);
    }

    public void printBezuege() {
        for (Map.Entry<Long, Double> entry : bezug.entrySet()) {
            System.out.println(entry.toString());
        }
    }
}
