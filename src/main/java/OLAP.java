//import org.apache.commons.collections4.iterators.EntrySetMapIterator;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.*;
//public class OLAP {
//
//        public void applyOLAP(Set<Set<String>> powerSet, Map<String, Map<String, String>> dimensionAttributeMap, Map<String, String> factVariableName, ArrayList<String> factIDColumns) throws IOException {
//        Scanner sc = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("Select OLAP Operations:\n1. Roll-Up\n2. Slice\n3. Dice\n4. Exit");
//            int ip = sc.nextInt();
//            if (ip == 4) {
//                System.out.println("exit");
//                break;
//            }
//            switch (ip) {
//
//
//                case 1:
//                    System.out.println("Roll-Up");
//                    getRollUp(powerSet , dimensionAttributeMap , factIDColumns);
//                    break;
//                case 2:
//                    System.out.println("Slice");
//                    getSlice(factVariableName, dimensionAttributeMap);
//                    break;
//                case 3:
//                    System.out.println("Dice");
//                    getDice(factVariableName,dimensionAttributeMap);
//                    break;
//                case 4:
//                    System.out.println("Drill Down");
//                    getDrillDown(factVariableName, dimensionAttributeMap);
//                default:
//                    break;
//            }
//        }
//    }
//
//        public void getDice(Map<String, String>  result ,Map<String, Map<String, String>> dimensionAttributeMap ) throws IOException {
//            int n = result.size();
//            int i = 0;
//            for(Set<String> s: result){
//                if(i<n)
//                    System.out.println(i+". "+s);
//                i++;
//            }
//
//
//            System.out.println("Enter no on which lattice you want to use dice operation ");
//            int index = sc.nextInt();
//
//            Set<String> selectedlattice = new HashSet<>();
//            Iterator iterator = result.iterator();
//            int count = 0;
//            while(iterator.hasNext()){
//                selectedlattice = (Set<String>) iterator.next();
//                if(count==index)
//                    break;
//                count++;
//            }
//            System.out.println(selectedlattice);
//
//            for (String s: selectedlattice){
//                System.out.println(s);
//            }
//
//
//            File selectedcuboidfile = new File("src/main/resources/"+selectedlattice+".csv");
//            BufferedReader br = new BufferedReader(new FileReader(selectedcuboidfile));
//            ArrayList<Integer> indexlattice =new ArrayList<>();
//            String line="";
//            int lineNumber=1;
//            line = br.readLine();
//            String[] header= line.split(",");
//            int totalColumn = header.length;
//
//            System.out.println("Dimensions list :-");
//            for(int j=0;j<totalColumn-1;j++){
//                System.out.println(j +" "+header[j]);
//            }
//            System.out.println("On how many Dimension you want to perform Dice OLAP");
//            int totalDim = sc.nextInt();
//            int[] parameterIndex = new int[totalDim];
//            System.out.println("Enter column no which one you wanna use as a condition");
//            for(int t=0;t<totalDim;t++){
//                parameterIndex[t] = sc.nextInt();
//            }
//
//
//
//            LinkedHashMap<Integer,ArrayList<String>> column = new LinkedHashMap<>();
//
//            while ((line = br.readLine())!=null){
//                String[] arr = line.split(",");
//                ArrayList<String> columnValue = new ArrayList<>();
//                for(int j=0;j<totalColumn;j++){
//                    columnValue.add(arr[j]);
//                }
//                column.put(lineNumber, columnValue);
//                lineNumber++;
//            }
//
//            System.out.println("Want to see complete data yes/no");
//
//            System.out.println("/////////////////");
//            String yesno = sc.next();
//            System.out.println("/////////////////");
//            if(yesno.equals("yes")){
//
//                for (String s:header) {
//                    System.out.print(s);
//                    System.out.print(" ");
//                }
//                System.out.println();
//
//                for (Map.Entry<Integer, ArrayList<String>> k : column.entrySet()) {
//                    System.out.println(k.getValue());
//                }
//            }
//
//            String[] condition = new String[totalDim];
//            for(int t=0;t<totalDim;t++){
//                System.out.println("Enter condition for Dimension "+header[parameterIndex[t]]+" on which you want to filter");
//                condition[t] = sc.next();
//            }
//            System.out.println(Arrays.toString(condition));
//
//
//            LinkedHashMap<Integer, ArrayList<String>> column1 = new LinkedHashMap<>(column);
//
//            for(int t=0;t<totalDim;t++){
//
//                LinkedHashMap<Integer, ArrayList<String>> output = new LinkedHashMap<>();
//
//                double limitdouble = 0;
//                String limitString = "";
//                String operand;
//                if(condition[t].substring(0, 2).equals("==")){
//                    limitString = condition[t].substring(2);
//                    operand = condition[t].substring(0,2);
//                }else{
//                    if(condition[t].charAt(1)=='=') {
//                        limitdouble = Double.parseDouble(condition[t].substring(2));
//                        operand = condition[t].substring(0, 2);
//                    }else{
//                        limitdouble = Double.parseDouble(condition[t].substring(1));
//                        operand = condition[t].substring(0,1);
//                    }
//                }
//
//                System.out.println(limitdouble);
//                System.out.println(limitString);
//                System.out.println(operand);
//
//                if(limitString.equals("")){
//                    for(Map.Entry<Integer, ArrayList<String>> k:column1.entrySet()){
//                        ArrayList<String> list = new ArrayList<>(k.getValue());
//
//                        switch (operand){
//                            case ">=":
//                                if(Double.parseDouble(list.get(parameterIndex[t]))>= limitdouble){
//                                    output.put(k.getKey(),k.getValue());
//                                }
//                                break;
//                            case ">":
//                                if(Double.parseDouble(list.get(parameterIndex[t]))> limitdouble){
//                                    output.put(k.getKey(),k.getValue());
//                                }
//                                break;
//                            case "<=":
//                                if(Double.parseDouble(list.get(parameterIndex[t]))<= limitdouble){
//                                    output.put(k.getKey(),k.getValue());
//                                }
//                                break;
//                            case "<":
//                                if(Double.parseDouble(list.get(parameterIndex[t]))< limitdouble){
//                                    output.put(k.getKey(),k.getValue());
//                                }
//                                break;
//                            case "=":
//                                if(Double.parseDouble(list.get(parameterIndex[t]))== limitdouble){
//                                    output.put(k.getKey(),k.getValue());
//                                }
//                                break;
//                            case "!=":
//                                if(Double.parseDouble(list.get(parameterIndex[t]))!= limitdouble){
//                                    output.put(k.getKey(),k.getValue());
//                                }
//                                break;
//                        }
//                    }
//                }else{
//                    for(Map.Entry<Integer, ArrayList<String>> k:column1.entrySet()) {
//                        ArrayList<String> list = new ArrayList<>(k.getValue());
//                        if(list.get(parameterIndex[t]).equals(limitString)){
//                            output.put(k.getKey(),k.getValue());
//                        }
//                    }
//                }
//                column1 = output;
//                System.out.println("*********************");
//                System.out.println(t+" "+column1);
//                System.out.println(t+" "+output);
//                System.out.println("*********************");
//
//            }
//
//            for (String s:header) {
//                System.out.print(s);
//                System.out.print(" ");
//            }
//
//            System.out.println();
//
//
//            for (Map.Entry<Integer, ArrayList<String>> k : column1.entrySet()) {
//                System.out.println(k.getValue());
//            }
//        }
//
//        public void getSlice(Set<Set<String>> result) throws IOException {
//            int n = result.size();
//            int i = 0;
//            for(Set<String> s: result){
//                if(i<n)
//                    System.out.println(i+". "+s);
//                i++;
//            }
//            System.out.println("Enter no on which lattice you want to use slice operation ");
//
//            int index = sc.nextInt();
//
//            Set<String> selectedlattice = new HashSet<>();
//            Iterator iterator = result.iterator();
//            int count = 0;
//            while(iterator.hasNext()){
//                selectedlattice = (Set<String>) iterator.next();
//                if(count==index)
//                    break;
//                count++;
//            }
//            System.out.println(selectedlattice);
//            for (String s: selectedlattice){
//                System.out.println(s);
//            }
//
//
//            File selectedcuboidfile = new File("src/main/resources/"+selectedlattice+".csv");
//            BufferedReader br = new BufferedReader(new FileReader(selectedcuboidfile));
//            ArrayList<Integer> indexlattice =new ArrayList<>();
//            String line="";
//            int lineNumber=1;
//            line = br.readLine();
//            String[] header= line.split(",");
//            int totalColumn = header.length;
//            System.out.println("Enter column no which one you wanna use as a condition");
//            for(int j=0;j<totalColumn-1;j++){
//                System.out.println(j +" "+header[j]);
//            }
//            LinkedHashMap<Integer,ArrayList<String>> column = new LinkedHashMap<>();
//
//            while ((line = br.readLine())!=null){
//                String[] arr = line.split(",");
//                ArrayList<String> columnValue = new ArrayList<>();
//                for(int j=0;j<totalColumn;j++){
//                    columnValue.add(arr[j]);
//                }
//                column.put(lineNumber, columnValue);
//                lineNumber++;
//            }
//
//
//            int parameterindex = sc.nextInt();
//            System.out.println("Want to see complete data yes/no");
//
//            System.out.println("/////////////////");
//            String yesno = sc.next();
//            System.out.println("/////////////////");
//            if(yesno.equals("yes")){
//
//                for (String s:header) {
//                    System.out.print(s);
//                    System.out.print(" ");
//                }
//                System.out.println();
//
//                for (Map.Entry<Integer, ArrayList<String>> k : column.entrySet()) {
//                    System.out.println(k.getValue());
//                }
//            }
//
//            System.out.println("Enter condition on which you want to filter");
//
//            String condition = sc.next();
//
//
//            System.out.println(condition);
//
//            double limitdouble = 0;
//            String limitString = "";
//            String operand;
//            if(condition.substring(0, 2).equals("==")){
//                limitString = condition.substring(2);
//                operand = condition.substring(0,2);
//            }else{
//                if(condition.charAt(1)=='=') {
//                    limitdouble = Double.parseDouble(condition.substring(2));
//                    operand = condition.substring(0, 2);
//                }else{
//                    limitdouble = Double.parseDouble(condition.substring(1));
//                    operand = condition.substring(0,1);
//                }
//            }
//
//            System.out.println(limitdouble);
//            System.out.println(limitString);
//            System.out.println(operand);
//            LinkedHashMap<Integer, ArrayList<String>> output = new LinkedHashMap<>();
//            if(limitString.equals("")){
//                for(Map.Entry<Integer, ArrayList<String>> k:column.entrySet()){
//                    ArrayList<String> list = new ArrayList<>(k.getValue());
//
//                    switch (operand){
//                        case ">=":
//                            if(Double.parseDouble(list.get(parameterindex))>= limitdouble){
//                                output.put(k.getKey(),k.getValue());
//                            }
//                            break;
//                        case ">":
//                            if(Double.parseDouble(list.get(parameterindex))> limitdouble){
//                                output.put(k.getKey(),k.getValue());
//                            }
//                            break;
//                        case "<=":
//                            if(Double.parseDouble(list.get(parameterindex))<= limitdouble){
//                                output.put(k.getKey(),k.getValue());
//                            }
//                            break;
//                        case "<":
//                            if(Double.parseDouble(list.get(parameterindex))< limitdouble){
//                                output.put(k.getKey(),k.getValue());
//                            }
//                            break;
//                        case "=":
//                            if(Double.parseDouble(list.get(parameterindex))== limitdouble){
//                                output.put(k.getKey(),k.getValue());
//                            }
//                            break;
//                        case "!=":
//                            if(Double.parseDouble(list.get(parameterindex))!= limitdouble){
//                                output.put(k.getKey(),k.getValue());
//                            }
//                            break;
//                    }
//                }
//            }else{
//                for(Map.Entry<Integer, ArrayList<String>> k:column.entrySet()) {
//                    ArrayList<String> list = new ArrayList<>(k.getValue());
//                    if(list.get(parameterindex).equals(limitString)){
//                        output.put(k.getKey(),k.getValue());
//                    }
//                }
//            }
//
//
//            for (String s:header) {
//                System.out.print(s);
//                System.out.print(" ");
//            }
//            System.out.println();
//
//            for (Map.Entry<Integer, ArrayList<String>> k : output.entrySet()) {
//                System.out.println(k.getValue());
//            }
//
//        }
//
//        public void getRollUp(Set<Set<String>> powerSet, Map<String, Map<String, String>> dimensionAttributeMap, ArrayList<String> factIDColumns) throws IOException {
//
//            int n = powerSet.size();
//            int i = 0;
//            for(Map.Entry<String, Map<String, String>> dim : dimensionAttributeMap.entrySet()){
//                System.out.println("Select Dimension and Attribute");
//                System.out.println(dim.getKey());
//                for(Map.Entry<String,String> attr : dim.getValue().entrySet())
//                    System.out.println(attr.getKey());
//            }
//            System.out.println("Enter number of dimensions for Roll Up operations");
//            Scanner sc = new Scanner(System.in);
//            Integer numberOfDim = sc.nextInt();
//            HashMap<String,String> dimAttr = new HashMap<>();
//            String dimName="";
//            String attrName="";
//            while (numberOfDim!=0)
//            {
//                System.out.println("Enter dimension name");
//                dimName=sc.next();
//                System.out.println("Enter attribute name");
//                attrName = sc.next();
//                dimAttr.putIfAbsent(dimName,attrName);
//                numberOfDim--;
//            }
//            for(Map.Entry<String,String> dim : dimAttr.entrySet()) {
//                Path pathToCheck = Paths.get("src/main/resources/lattice/lattice/market/Fact"+ "/" + dim.getValue() + ".csv");
//                boolean check = Files.exists(pathToCheck);
//                if (check) {
//
//
//                }
//            }
//
//
//
//            int index = sc.nextInt();
//            Set<String> selectedlattice = new HashSet<>();
//            Iterator iterator = powerSet.iterator();
//            int count = 0;
//            while(iterator.hasNext()){
//                selectedlattice = (Set<String>) iterator.next();
//                if(count==index)
//                    break;
//                count++;
//            }
//            System.out.println(selectedlattice);
//            for (String s: selectedlattice){
//                System.out.println(s);
//            }
//
//            File selectedcuboidfile = new File("src/main/resources/"+selectedlattice+".csv");
//            BufferedReader br = new BufferedReader(new FileReader(selectedcuboidfile));
//            ArrayList<Integer> indexlattice =new ArrayList<>();
//            String line="";
//            int lineNumber=1;
//            line = br.readLine();
//            String[] header= line.split(",");
//            int totalColumn = header.length;
//            for (String s:header) {
//                System.out.print(s);
//                System.out.print(" ");
//            }
//            System.out.println();
//            HashMap<ArrayList<String>, Integer> column = new HashMap<>();
//
//            while ((line = br.readLine())!=null){
//                String[] arr = line.split(",");
//                ArrayList<String> columnValue = new ArrayList<>();
//                for(int j=0;j<totalColumn;j++){
//                    columnValue.add(arr[j]);
//                }
//                for (String s1:columnValue) {
//                    System.out.print(s1);
//                    System.out.print("\t");
//                }
//                System.out.println();
//            }
//
//        }
//
//        public void getDrillDown(){
//
//        }
//
//}
