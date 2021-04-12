import java.io.File;
import java.io.IOException;
import java.util.*;

public class FolderCreation {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        try {

            System.out.println("Enter the database name");
            String dbname = sc.next();
            File file = new File("src/main/resources/" + dbname);
            boolean bool = file.mkdir();
            if(bool)
                System.out.println("created");
            Set<String> setOfDimensionName = new HashSet<>();
            System.out.println("Enter number of dimensions");
            FileConversion fileConversion = new FileConversion();
            int count_dimensional_table = sc.nextInt();
            while (count_dimensional_table != 0) {
                int i = 1;
                System.out.println("Enter Dimensional table name" + i);
                String dimension = sc.next();
                System.out.println(dimension);
                setOfDimensionName.add(dimension);
                String path = "src/main/resources/" + dbname + "/" + dimension;
                file = new File(path);
                System.out.println(file);
                System.out.println(path);
                bool = file.mkdir();

                System.out.println("Enter number of attributes for dimension " + dimension);
                int attributes = sc.nextInt();
                Map<String, String> attributeMap = new HashMap<>();
                while (attributes != 0) {
                    int j = 1;
                    System.out.println("Enter the name of attribute " + j);
                    String attributeName = sc.next();
                    System.out.println("Enter the type of attribute " + j);
                    String attributeType = sc.next();
                    attributeMap.put(attributeName, attributeType);
                    attributes--;
                    j++;

                }
                count_dimensional_table--;
                i++;
                fileConversion.createFile(attributeMap, dimension, dbname);
            }

            System.out.println("Enter fact table details ");
            Map<String, String> factVariables = new HashMap<>();// fact variable and aggregate function
            Map<String, String> factColumns = new HashMap<>();//all the columns of fact table
            Map<String, String> factAttributesXml = new HashMap<>();//fact variables and the type
            String path = "src/main/resources/" + dbname + "/Fact";
            file = new File(path);
            System.out.println(file);
            bool = file.mkdir();
            System.out.println("Enter the number of Fact variables");
            int noOfFactVariables = sc.nextInt();
            while (noOfFactVariables != 0) {
                int i = 1;
                System.out.println("Enter Fact Variable " + 1);
                String factV = sc.next();
                System.out.println("Enter Aggregate Function on the fact variable " + factV);
                String aggF = sc.next();

                factVariables.put(factV, aggF);
                factAttributesXml.put(factV, "NUMERIC");
                i++;
                noOfFactVariables--;
            }
            setOfDimensionName.forEach(v -> factColumns.put(v + "_ID", "NUMERIC"));
            factAttributesXml.forEach((k, v) -> factColumns.put(k, v));
            fileConversion.createFile(factColumns, "Fact", dbname);

            LatticeCreation latticeCreation = new LatticeCreation();
            Set<Set<String>> dimensionPower = latticeCreation.generatePowerSet(setOfDimensionName);
            latticeCreation.generateLatticeNameFolder(dimensionPower);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
