import backend.DataHandler;
import gui.DataGUI;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author Francesco Ryu
 * @version 1.0
 * @since 2022-10-03
 */

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, ParseException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        new DataHandler().loadESLFolder();
    }
}
