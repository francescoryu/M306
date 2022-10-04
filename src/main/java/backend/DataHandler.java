package backend;

import gui.DataGUI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Francesco Ryu, Nimai Leuenberger, Chris Hunziker, Marko Joksimovic
 * @version 1.0
 * @since 2022-10-03
 */

public class DataHandler {

    String xmlPath = "src/main/resources/";

    public void loadESLFolder() throws ParserConfigurationException, IOException, SAXException, ParseException {
        File firstESLFile = new File(xmlPath + "ESL-Files/EdmRegisterWertExport_20190131_eslevu_20190322160349.xml");


        File SDATFolder = new File(xmlPath + "SDAT-Files");
        File[] SDATFileList = SDATFolder.listFiles();

        //ID742 = Consumption
        //ID735 = Production

        Messwerte messwerte = new Messwerte();

        assert SDATFileList != null;

        DocumentBuilderFactory factoryESL = DocumentBuilderFactory.newInstance();
        DocumentBuilder builderESL = factoryESL.newDocumentBuilder();
        Document firstESLDocument = builderESL.parse(firstESLFile);
        firstESLDocument.getDocumentElement().normalize();

        NodeList timePeriodList = firstESLDocument.getElementsByTagName("TimePeriod");
        String startDateString = timePeriodList.item(0).getAttributes().item(0).getTextContent();

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
        Timestamp timestamp = new Timestamp(startDate.getTime());

        long timeStartB = timestamp.getTime();
        long timeStartE = timestamp.getTime();

        int cntrB = 0;
        int cntrE = 0;

        for (File file : SDATFileList) {
            if (file.isFile()) {
                Document sdatDocument = buildDocument(file);
                sdatDocument.getDocumentElement().normalize();

                String idString = getDocumentID(sdatDocument);

                NodeList sequenceList = sdatDocument.getElementsByTagName("rsm:Sequence");
                NodeList volumeList = sdatDocument.getElementsByTagName("rsm:Volume");
                NodeList sdatStartDateList = sdatDocument.getElementsByTagName("rsm:StartDateTime");

                //------------------------------------------------------------------------------------------------------

                String sdatStartDateString = sdatStartDateList.item(0).getTextContent();
                Date sdatStart = new SimpleDateFormat("yyyy-MM-dd").parse(sdatStartDateString);
                Timestamp sdatStamp = new Timestamp(sdatStart.getTime());

                //------------------------------------------------------------------------------------------------------

                long fileTimeStart = sdatStamp.getTime();

                if (idString.matches(".+ID742")) {
                    cntrB++;

                    if (fileTimeStart >= timeStartB) {
                        for (int i = 0; i < sequenceList.getLength(); i++) {
                            Node sequenceNode = sequenceList.item(i);
                            String sequenceString = sequenceNode.getTextContent();
                            timeStartB += 900000;

                            Node volumeNode = volumeList.item(i);
                            double volume = Double.parseDouble(volumeNode.getTextContent());

                            messwerte.addBezug(timeStartB, volume);
                            //messwerte.printBezuege();
                            //push test
                        }
                    }

                } else if (idString.matches(".+ID735")) {
                    cntrE++;

                    if (fileTimeStart >= timeStartE) {
                        for (int i = 0; i < sequenceList.getLength(); i++) {
                            Node sequenceNode = sequenceList.item(i);
                            String sequenceString = sequenceNode.getTextContent();
                            timeStartE += 900000;

                            Node volumeNode = volumeList.item(i);
                            double volume = Double.parseDouble(volumeNode.getTextContent());

                            messwerte.addEinspeisung(timeStartE, volume);
                        }
                    }
                }
            }
        }
        //messwerte.printBezuege();
        //System.out.println(cntrB + cntrE);
        new DataGUI(messwerte);
    }

    public String getDocumentID(Document sdatDocument) {
        NodeList documentID = sdatDocument.getElementsByTagName("rsm:DocumentID");
        Node docIDNode = documentID.item(0);

        return docIDNode.getTextContent();
    }

    public Document buildDocument(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factorySDAT = DocumentBuilderFactory.newInstance();
        DocumentBuilder builderSDAT = factorySDAT.newDocumentBuilder();

        return builderSDAT.parse(file);
    }
}
