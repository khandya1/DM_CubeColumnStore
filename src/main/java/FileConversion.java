import java.io.*;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class FileConversion
{
    public void uploadfile(String attribute_name) {
    }
    public void createFile(String attribute_name) throws IOException {
        String csvSplitter = ",";
        BufferedReader br = new BufferedReader(new FileReader("Test_Columns_Seperate.csv"));
        PrintWriter pw;
        String line;
        line=br.readLine();
        String[] header=line.split(csvSplitter);
        String csv_name=header[1];////message to anshul 1 is index of column, while loop for rest of the columns
        pw = new PrintWriter(new File(csv_name+".csv"));
        //StringBuffer csvHeader = new StringBuffer("");
        StringBuffer csvData = new StringBuffer("");
        while ((line = br.readLine()) != null) {
            String[] cols = line.split(csvSplitter);
            csvData.append(cols[1]);
            csvData.append('\n');
        }
        //pw.write(csvHeader.toString());
        pw.write(csvData.toString());
        pw.close();
    }




}


