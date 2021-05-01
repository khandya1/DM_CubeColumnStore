import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FolderCreation {

    public static void foldercreation() throws IOException {
        Scanner sc = new Scanner(System.in);
        try {

            System.out.println("Enter the database name");
            String dbname = sc.next();///////Database name

            while(true){
                File dbFile = new File("src/main/resources/dbname.txt");
                BufferedReader br = new BufferedReader(new FileReader(dbFile));
                String line = "";
                boolean fileExist = false;
                while((line = br.readLine())!=null){
                    if(line.equals(dbname)){
                        fileExist = true;
                    }
                }
                if(fileExist){
                    System.out.println("Please Enter different DB Name as this name already exist...");
                    dbname = sc.next();
                }else{
                    File file1 = new File("src/main/resources/"+dbname);
                    boolean bool = file1.mkdir();//creating database folder

                    PrintWriter out = null;
                    BufferedWriter bw = null;
                    FileWriter fw = null;

                    fw = new FileWriter("src/main/resources/dbname.txt", true);
                    bw = new BufferedWriter(fw);
                    out = new PrintWriter(bw);
                    out.println(dbname+"\n");
                    out.close();
                    bw.close();
                    fw.close();

                    break;
                }
            }




            File savedData = new File("src/main/resources/"+dbname+"/savedData");
            boolean bool = savedData.mkdir();



            Map<String , Map<String,String>> dimensionAttributeMap= new HashMap<>();//dimensionName and Attribute and type
            Set<String> setOfDimensionName = new HashSet<>();
            System.out.println("Enter number of dimensions");
            int count_dimensional_table = sc.nextInt();
            int i = 1;
            FileConversion fileConversion = new FileConversion();
            LinkedHashMap<String,String> dimensionID = new LinkedHashMap<>();
            while (count_dimensional_table != 0) {

                System.out.println("Enter Dimensional table name" + i);
                String dimension = sc.next();
                setOfDimensionName.add(dimension);
                String path = "src/main/resources/" + dbname + "/" + dimension;
                File file = new File(path);//creating folder for dimension
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
                    String x ="ID";
                    if (attributeName.matches("(.*)" + x))
                        dimensionID.put(dimension,attributeName);


                }
                count_dimensional_table--;
                i++;

                System.out.println("Enter the path for the file -->");
                String dimPath = sc.next();

                fileConversion.createFile(attributeMap, dimension, dbname,dimPath);
                dimensionAttributeMap.put(dimension , attributeMap);
            }



            FileOutputStream saveDimension = new FileOutputStream(new File("src/main/resources/"+dbname+"/savedData/dimension"));
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(saveDimension);
            objectOutputStream.writeObject(dimensionID);

            FileOutputStream saveDimensionAttributeMap = new FileOutputStream(new File("src/main/resources/"+dbname+"/savedData/dimensionAttributeMap"));
            ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(saveDimensionAttributeMap);
            objectOutputStream1.writeObject(dimensionAttributeMap);



            Map<String, String> factVariables = new HashMap<>();// fact variable and aggregate function
            Map<String, String> factColumns = new HashMap<>();//all the columns of fact table
            Map<String, String> factAttributesXml = new HashMap<>();//fact variables and the type
            String path = "src/main/resources/" + dbname + "/Fact";
            File file = new File(path);
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


            FileOutputStream savefactvaribale = new FileOutputStream(new File("src/main/resources/"+dbname+"/savedData/factvaribale"));
            ObjectOutputStream savefactvaribaleO= new ObjectOutputStream(savefactvaribale);
            savefactvaribaleO.writeObject(factVariables);

            //Schema Creation

            SchemaCreation schemaCreation = new SchemaCreation();
            schemaCreation.instanceCreation(dimensionAttributeMap,"Fact",factAttributesXml , dbname);
            schemaCreation.constraintsCreation(factVariables , dbname);

            //lattice creation

            LatticeCreation latticeCreation = new LatticeCreation();
            ArrayList<String> factIDColumns = latticeCreation.findFactIDColumns(factPath,setOfDimensionName.size());
            Set<Set<String>> powerSet =  latticeCreation.createLattice(factPath,factIDColumns,factVariables, setOfDimensionName.size(), dbname);

            FileOutputStream saveLattice = new FileOutputStream(new File("src/main/resources/"+dbname+"/savedData/lattice"));
            ObjectOutputStream objectOutputStream2= new ObjectOutputStream(saveLattice);
            objectOutputStream2.writeObject(powerSet);

            DeleteFile deleteFile = new DeleteFile();
            deleteFile.testFile(dbname);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
