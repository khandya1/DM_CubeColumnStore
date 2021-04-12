import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class FileConversion
{
    public void uploadfile(String attribute_name) {
    }
    public void createFile() throws IOException {
        String csvSplitter = ",";
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Test_Columns_Separate.csv"));
        PrintWriter pw;
        String line;
        line=br.readLine();
        System.out.println("**********************");
        String[] header=line.split(csvSplitter);

        System.out.println(Arrays.toString(header));
        int n = header.length;
        System.out.println("Total headers is "+n);
        int i = 0;
        while(i++<n){
            System.out.println(i);
            String csv_name=header[i];////message to anshul 1 is index of column, while loop for rest of the columns
            System.out.println(csv_name);
            pw = new PrintWriter(new File("src/main/resources/abc/in/"+csv_name+".csv"));
            System.out.println("*************");
            //StringBuffer csvHeader = new StringBuffer("");
            StringBuffer csvData = new StringBuffer("");
            System.out.println("/////////////////////");
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(csvSplitter);
                System.out.println(cols[i]);
                csvData.append(cols[i]);
                csvData.append('\n');
            }
            //pw.write(csvHeader.toString());
            System.out.println(csvData);
            pw.write(csvData.toString());
            pw.close();
        }

    }
}



