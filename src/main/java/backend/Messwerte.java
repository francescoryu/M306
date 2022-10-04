package backend;

import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Marko Joksimovic
 * @version 1.0
 * @since 2022-10-03
 */

public class Messwerte {

    TreeMap<Long, Double> bezug = new TreeMap<Long, Double>();
    TreeMap<Long, Double> einspeisung = new TreeMap<Long, Double>();

    TreeMap<Date, Double> bezugD = new TreeMap<Date, Double>();
    TreeMap<Date, Double> einspeisungD = new TreeMap<Date, Double>();

    public Messwerte(){
    }

    public void addBezug(long timestamp, double messwert) {
        bezug.put(timestamp, messwert);
    }

    public void addEinspeisung(long timestamp, double messwert) {
        einspeisung.put(timestamp, messwert);
    }

    /*
    Test-Methode (nicht wichtig)
     */
    public void printBezuege() {
        for (Map.Entry<Long, Double> entry : bezug.entrySet()) {
            System.out.println(entry.toString());
        }
    }

    /*
    Diese Methode nimmt das Treemap mit den Bezuegen und wandelt die Keys von long zu Date.
     */
    public void toDateB() {
        for (Map.Entry<Long, Double> entry : bezug.entrySet()) {
            //RegularTimePeriod stamp = new Day(14,6,2000);
            Date stamp = new Date(entry.getKey());
            bezugD.put(stamp, entry.getValue());
        }
    }

    /*
    Diese Methode nimmet das Treemap mit der Einspeisung und wandelt die Keys von long zu Date.
     */
    public void toDateE() {
        for (Map.Entry<Long, Double> entry : einspeisung.entrySet()) {
            Date stamp = new Date(entry.getKey());
            einspeisungD.put(stamp, entry.getValue());
        }
    }

    /*
    Getter-Methode der Bezuege-Treemap
     */
    public TreeMap<Long, Double> getMapB() {
        return bezug;
    }

    /*
    Getter-Methode der Einspeisung-Treemap
     */
    public TreeMap<Long, Double> getMapE() {
        return einspeisung;
    }

    public void printBezuegeD() {
        for (Map.Entry<Date, Double> entry : bezugD.entrySet()) {
            System.out.println(entry.toString());
        }
    }
}
