package backend;
import java.io.File;

/**
 * @author Chris Hunziker
 * @version 1.0
 * @since 2022-10-04
 */

public class xml2json {
    public static void main(String[] args) {
        xml2json program = new xml2json();
        program.run();
    }

    private void run() {
        Gson gson = new Gson();
        String xmlPath = "src/main/resources/";
        File SDATFolder = new File(xmlPath + "SDAT-Files");
        File[] SDATFileList = SDATFolder.listFiles();
        String[] json = {
                "data": {}
        };



        assert SDATFileList != null;

        for (File file : SDATFileList) {
            if (file.isFile()) {
                // TODO
            }
        }
    }
}

public class xmlObj {
    private String sensorId;
    private int startDate, endDate;
    private String[] data;

    public xmlObj(String sensorId, int startDate, int endDate) {
        this.sensorId = sensorID;
        this.startDate = startDate;
        this.color = color;
    }

    public String getType() { return this.type; }
    public int getModel() { return this.model; }
    public String getColor() { return this.color; }
}
