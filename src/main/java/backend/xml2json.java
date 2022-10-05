package backend;
import java.io.File;
import com.google.gson.Gson;

/**
 * @author Chris Hunziker
 * @version 6.9
 * @since 2022-10-04
 */

public class xml2json {
    public static void main(String[] args) {
        //xml2json program = new xml2json(mw);
        //program.run();
    }

    private void run() {
        Gson gson = new Gson();
        String xmlPath = "src/main/resources/";
        File SDATFolder = new File(xmlPath + "SDAT-Files");
        File[] SDATFileList = SDATFolder.listFiles();

        assert SDATFileList != null;

        //for (File file : SDATFileList) {
        //    if (file.isFile()) {
                // EXAMPLE DATA
                xmlObj obj = new xmlObj("id", 0, 0);

                obj.addData(198234091, 3.324f);

                String json = gson.toJson(obj);

                System.out.println(json);
            //}
        //}
    }

    public static class xmlObj {
        private float[][] data;
        private String sensorId;
        private int startDate,
                    endDate,
                    index = 0;

        public xmlObj(String sensorId, int startDate, int endDate) {
            this.sensorId = sensorId;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public void addData(int ts, float value) {
            this.data[index] = new float[]{ts, value};
            index++;
        }
    }
}
