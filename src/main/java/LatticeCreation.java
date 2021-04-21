import java.io.*;
import java.util.*;

public class LatticeCreation {

    public void createLattice(String factPath, ArrayList<String> s, Map<String, String> factVariables, Integer columnNumber)
    {

        long N = (long) Math.pow(2, s.size());

        // Set to store subsets
        Set<Set<String>> result = new HashSet<>();

        // generate each subset one by one
        for (int i = 0; i < N; i++) {
            Set<String> set = new HashSet<>();

            // check every bit of `i`
            for (int j = 0; j < s.size(); j++) {
                // if j'th bit of `i` is set, add `S[j]` to the current set
                if ((i & (1 << j)) != 0) {
                    set.add(s.get(j));
                }
            }
            if(!set.isEmpty())
                result.add(set);
        }
        System.out.println(result);
        for(Set<String> stt : result)
            System.out.println(stt);

        System.out.println("Read Data Line by Line With Header \n");
        for (Map.Entry<String,String> entry : factVariables.entrySet())
        {
            readDataLineByLine(factPath , result , columnNumber++ , entry.getValue());
        }

        System.out.println("_______________________________________________");


    }

    public static void readDataLineByLine(String file, Set<Set<String>> powerSet , Integer factColumnNumber , String aggFunc)
    {
        try
        {

            ArrayList<Integer> index=new ArrayList<>();
            int choice = 5;
            if(aggFunc == "SUM")
                choice = 0;
            else if(aggFunc == "AVG")
                choice = 1;
            else if(aggFunc == "COUNT")
                choice = 2;
            else if(aggFunc == "MAX")
                choice = 3;
            else if(aggFunc == "MIN")
                choice = 4;
            else
            {
                System.out.println("Wrong input of aggregate function");
                System.exit(0);
            }



            for(Set<String> s : powerSet)
            {
                File csvFile = new File(file);
                BufferedReader br = new BufferedReader(new FileReader(csvFile));
                String line ="";
                index.clear();
                line = br.readLine();
                String[] header= line.split(",");
                for(int i=0;i< header.length;i++)
                {
                    if((s.contains(header[i])))
                    {
                        index.add(i);
                    }
                }
                System.out.println(s);
                HashMap<ArrayList<String>, Double> column = new HashMap();
                while ((line = br.readLine()) != null)
                {
                    String[] arr = line.split(",");
                    //for the first line it'll print
                    ArrayList<String> columnValue = new ArrayList<>();
                    for(Integer j:index)
                    {
                        columnValue.add(arr[j]);
                    }
                    Double fact_new = Double.parseDouble(arr[factColumnNumber]);
                    if(column.containsKey(columnValue))
                    {
                        Double fact_old=column.get(columnValue);
                        Double temp =0.0;
                        switch(choice)
                        {
                            case 0: temp=fact_new+fact_old;
                                break;
                            case 1:temp=(fact_new+fact_old)/2;
                                break;
                            case 2:temp=fact_old+1;
                                break;
                            case 3:if(fact_new>=fact_old)
                                temp=fact_new;
                            else
                                temp=fact_old;
                                break;
                            case 4:if(fact_new>=fact_old)
                                temp=fact_old;
                            else
                                temp=fact_new;
                                break;
                            default: break;
                        }
                        column.put(columnValue,temp);
                    }
                    else
                        column.put(columnValue,fact_new);

                }
                System.out.println(column);
                FileCreateHashMapToCsv(column,s);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void FileCreateHashMapToCsv(HashMap<ArrayList<String>, Double> column, Set<String> s) {

        try {
            String header ="";
            for(String h : s)
                header=header+h+",";

            PrintWriter pw = new PrintWriter(new File("src/main/resources/"+s+".csv"));
            StringBuffer csvData = new StringBuffer("");
            csvData.append(header);
            csvData.append("Fact\n");
            for (Map.Entry<ArrayList<String>, Double> entry : column.entrySet())
            {
                String x="";
                for(String v: entry.getKey())
                    x=x+v+",";
                csvData.append(x);
                csvData.append(entry.getValue());
                csvData.append("\n");
            }
            pw.write(csvData.toString());
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> findFactIDColumns(String factPath, Integer noOfColumns) throws IOException {

        File csvFile = new File(factPath);
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line="";
        ArrayList<String> factColumns= new ArrayList<>();
        line = br.readLine();
        String[] header= line.split(",");
        for(int i=noOfColumns-1;i>=0;i--)
        {
           factColumns.add(header[i]);
        }
        return factColumns;


    }
}
