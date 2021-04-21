import java.io.File;
import java.io.IOException;
import java.util.*;

public class FolderCreation {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        try {

            System.out.println("Enter the database name");
            String dbname = sc.next();///////Database name

            File file = new File("src/main/resources/" + dbname);
            boolean bool = file.mkdir();//creating database folder


            Map<String , Map<String,String>> dimensionAttributeMap= new HashMap<>();//dimensionName and Attribute and type
            Set<String> setOfDimensionName = new HashSet<>();
            System.out.println("Enter number of dimensions");
            int count_dimensional_table = sc.nextInt();
            int i = 1;
            FileConversion fileConversion = new FileConversion();
            while (count_dimensional_table != 0) {

                System.out.println("Enter Dimensional table name" + i);
                String dimension = sc.next();
                setOfDimensionName.add(dimension);
                String path = "src/main/resources/" + dbname + "/" + dimension;
                file = new File(path);//creating folder for dimension
                bool = file.mkdir();

                System.out.println("Enter number of attributes for dimension " + dimension);
                int attributes = sc.nextInt();
                Map<String, String> attributeMap = new HashMap<>();
                int j = 1;
                while (attributes != 0) {

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

                System.out.println("Enter the path for the file -->");
                String dimPath = sc.next();

                fileConversion.createFile(attributeMap, dimension, dbname,dimPath);
                dimensionAttributeMap.put(dimension , attributeMap);
            }

            //////////////////////////////////////////////////////////////////////////////////////////////////////////



            Map<String, String> factVariables = new HashMap<>();// fact variable and aggregate function
            Map<String, String> factColumns = new HashMap<>();//all the columns of fact table
            Map<String, String> factAttributesXml = new HashMap<>();//fact variables and the type
            String path = "src/main/resources/" + dbname + "/Fact";
            file = new File(path);
            bool = file.mkdir();//fact folder created
            System.out.println("Enter fact table details ");
            System.out.println("Enter the number of Fact variables");
            int noOfFactVariables = sc.nextInt();
            i = 1;
            while (noOfFactVariables != 0) {

                System.out.println("Enter Fact Variable " + i);
                String factV = sc.next();
                System.out.println("Enter Aggregate Function on the fact variable " + factV);
                String aggF = sc.next();

                factVariables.put(factV, aggF);
                factAttributesXml.put(factV, "NUMERIC");
                i++;
                noOfFactVariables--;
            }
            //finding all fact columns
            setOfDimensionName.forEach(v -> factColumns.put(v + "_ID", "NUMERIC"));//for each dimension name put dimension_id, integer
            factAttributesXml.forEach((k, v) -> factColumns.put(k, v));
            System.out.println("Enter the path for the fact file -->");
            String factPath = sc.next();
            fileConversion.createFile(factColumns, "Fact", dbname,factPath);

            ///folder creation for lattice
            LatticeFolderCreation latticeFolderCreation = new LatticeFolderCreation();
            Set<Set<String>> dimensionPower = latticeFolderCreation.generatePowerSet(setOfDimensionName);
            latticeFolderCreation.generateLatticeNameFolder(dimensionPower , dbname);

            //lattice creation

            LatticeCreation latticeCreation = new LatticeCreation();
            ArrayList<String> factIDColumns = latticeCreation.findFactIDColumns(factPath,setOfDimensionName.size());
            latticeCreation.createLattice(factPath,factIDColumns,factVariables, setOfDimensionName.size());


            SchemaCreation schemaCreation = new SchemaCreation();
            schemaCreation.instanceCreation(dimensionAttributeMap,"Fact",factAttributesXml);
            schemaCreation.constraintsCreation(factVariables);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
