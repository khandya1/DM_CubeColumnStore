
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
    class RollUp {

        public void rollUpFunction(Map<String, String> dimensionID, Map<String, String> factV, Set<Set<String>> powerSet,String dbname) throws IOException {
            System.out.println("Enter number of dimensions for Roll Up operations");
            Scanner sc = new Scanner(System.in);
            int numberOfDim= sc.nextInt();
            int numberOfDimForFurtherUse=numberOfDim;
            LinkedHashMap<String, String> dimAttr = new LinkedHashMap<>();
            LinkedHashMap<String,Integer> columnN = new LinkedHashMap<>();
            String dimName = "";
            String attrName = "";
            int intt=0;
            while (numberOfDim != 0) {
                System.out.println("Enter dimension name");
                dimName = sc.next();
                System.out.println("Enter attribute name");
                attrName = sc.next();
                dimAttr.putIfAbsent(dimName, attrName);
                columnN.putIfAbsent(dimensionID.get(dimName),intt);//column name and index
                intt++;
                numberOfDim--;
            }
            System.out.println(columnN);
            String alreadyCreatedLattice = "";
            List<List<String>> records = new ArrayList<>();/////for non factid columns
            HashMap<String,Integer> recordsMapping = new HashMap<>();
            int i=0;
            for (Map.Entry<String, String> dim : dimAttr.entrySet()) {
                List<String> record = new ArrayList<>();
                System.out.println(dim.getValue());
                Path pathToCheck = Paths.get("src/main/resources/"+dbname+"/Fact" + "/" + dim.getValue() + ".csv");
                boolean check = Files.exists(pathToCheck);
                if (check) {
                    alreadyCreatedLattice = alreadyCreatedLattice + dim.getValue() + ", ";
                } else {
                    String x = dimensionID.get(dim.getKey());
                    alreadyCreatedLattice = alreadyCreatedLattice + x + ", ";
                    pathToCheck = Paths.get("src/main/resources/"+dbname+"/" + dim.getKey() + "/" + dim.getValue() + ".csv");
                    record = readFile(pathToCheck);
                    records.add(record);
                    recordsMapping.put(dim.getValue(),i);
                    i++;
                }
            }
            System.out.println(records);
            List<List<String>> finalOp = new ArrayList<>();
            if (alreadyCreatedLattice.length() > 0) {
                alreadyCreatedLattice = alreadyCreatedLattice.substring(0, alreadyCreatedLattice.length() - 2);
                String fpath = "src/main/resources/"+dbname+"/lattice/lattice[" + alreadyCreatedLattice + "]";
                for (Map.Entry<String, String> dim : dimAttr.entrySet()) {
                    String y = dimensionID.get(dim.getKey());
                    List<String> columnValue = listFinalOP(y, fpath);
                    finalOp.add(columnValue);
                }
                for (Map.Entry<String, String> fact : factV.entrySet()) {
                    String y = fact.getKey();
                    String z= fact.getValue();
                    String m=y+"_"+z;
                    List<String> columnValue = listFinalOP(m, fpath);
                    finalOp.add(columnValue);
                }
            }
            System.out.println(finalOp);
            for(int j=0;j<numberOfDimForFurtherUse;j++)
            {
                //System.out.println("Inside for");
                String columnName="";
                String dimenName="";
                String requiredAttr="";
                int index=0;
                for(Map.Entry<String, Integer> entry: columnN.entrySet()) {
                    if(entry.getValue() == j) {
                        columnName= entry.getKey();
                        break;
                    }
                }
                for(Map.Entry<String, String> entry1: dimensionID.entrySet()) {
                    if(entry1.getValue().equalsIgnoreCase(columnName)) {
                        dimenName= entry1.getKey();
                        break;
                    }
                }
                for (Map.Entry<String, String> dim1 : dimAttr.entrySet()) {
                    if(dim1.getKey().equalsIgnoreCase(dimenName)) {
                        requiredAttr= dim1.getValue();
                        break;
                    }
                }
                //System.out.println("columnName is "+ columnName);
                //System.out.println("required attr is "+ requiredAttr);
                if(columnName.equalsIgnoreCase(requiredAttr))
                {
                    continue;
                }
                else {
                    //System.out.println("Inside else\n");
                    for (Map.Entry<String, Integer> rec : recordsMapping.entrySet()) {
                        if(rec.getKey().equalsIgnoreCase(requiredAttr)) {
                            index= rec.getValue();
                            break;
                        }
                    }
                    //System.out.println("index is "+index);
                    for (int p = 0; p < finalOp.get(j).size(); p++) {
                        Integer x = Integer.parseInt(finalOp.get(j).get(p));
                        x = x - 1;
                        finalOp.get(j).set(p, records.get(index).get(x));
                    }
                }
            }
            for (List<String> stt : finalOp) {
                System.out.println(stt);
            }
        }
        private static List<String> listFinalOP(String y, String fpath) throws IOException {
            BufferedReader br = new BufferedReader(new FileReader(fpath+ "\\"+y+".csv"));
            String line = "";
            ArrayList<String> columnValue = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                columnValue.add(arr[0]);
            }
            br.close();
            return columnValue;
        }
        private static List<String> readFile(Path pathToCheck) throws IOException {
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(pathToCheck)));
            List<String> records = new ArrayList<>();
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(",");
                for (int i = 0; i < lineSplit.length; i++) {
                    records.add(lineSplit[i]);
                }
            }
            br.close();
            return records;
        }
    }

