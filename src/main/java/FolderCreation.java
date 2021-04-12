import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class FolderCreation {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the database name");
        String dbname = sc.next();
        File file = new File("src/main/resources/"+dbname);
        boolean bool = file.mkdir();
        Set<String> setofDimensionName = new LinkedHashSet<String>();
        System.out.println("Enter number of dimensions");
        int count_dimensional_table = sc.nextInt();
        while(count_dimensional_table-->0)
        {
            System.out.println("Enter Dimensional table name");
            String dimension = sc.next();
            System.out.println(dimension);
            assert false;
            setofDimensionName.add(dimension);
            System.out.println("********************");
            file = new File("src/main/resources/"+dbname+"/"+dimension);
            System.out.println(file);
            bool = file.mkdir();

            FileConversion fc = new FileConversion();
            fc.createFile();
//            System.out.println("Enter number of attributes");
//            int attributes = sc.nextInt();
//            while(attributes-->0)
//            {
//                FileConversion fc = new FileConversion();
//                String attribute_name = sc.next();
//                //attribute_name = "src/main/resources/"+dbname+"/"+dimension + "/"+attribute_name;
//                fc.createFile();
//            }
     }

        LatticeCreation latticeCreation = new LatticeCreation();
        Set<Set<String>> dimensionPower= latticeCreation.generatePowerSet(setofDimensionName);
        Set<String > dimensionNames=latticeCreation.generateLatticeName(dimensionPower);
    }
}
