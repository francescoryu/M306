import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Test {
    public Test() throws ParserConfigurationException, IOException, SAXException {

        File folder = new File("src/main/resources/ESL-Files");
        File[] fileList = folder.listFiles();

        assert fileList != null;
        for (File file : fileList) {
            if (file.isFile()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(file);
                document.getDocumentElement().normalize();

                NodeList timePeriodList = document.getElementsByTagName("TimePeriod");
                String endDateTime = timePeriodList.item(0).getAttributes().item(0).getTextContent();
                System.out.println("-----------------------------------");
                System.out.println(endDateTime);
                System.out.println(file.getName());
                System.out.println("-----------------------------------");
            }
        }
    }
}
