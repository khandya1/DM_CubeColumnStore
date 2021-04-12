package SchemaCreate;

import Schema.*;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchemaCreation {
    public boolean doesSchemaExist(String name) throws IOException {
        name=generateName(name);
        String arr[]= getSchemaList();
        if(arr==null)
            return false;
        for(String s: arr)
            if(s.equals(name))
                return true;
        return false;
    }

    private String[] getSchemaList() throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String fName = currentDirectory + "/storage/Schema_List.txt";
        File file= new File(fName);
        if(!file.exists())
            return null;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        st = br.readLine();
        String[] words = st.split("\\s+");
        System.out.println(Arrays.toString(words));
        return words;
    }

    private String generateName(String s) {
        System.out.println("1_");
        System.out.println(s);
        s.toLowerCase();
        System.out.println("2_");
        s=s.trim();
        System.out.println("3_");
        if(s.contains(" "))
            s= s.replaceAll(" ", "_");
        System.out.println("4_");
        return s;
    }

    public StarSchema newSchema(String name) {
        StarSchema starSchema = new StarSchema();
        starSchema.setName(generateName(name));
        return starSchema;
    }

    public void insertDimension(StarSchema starSchema, String name, List<String> attributeNames) {
        Dimension dimension = new Dimension();
        System.out.println("1");
        ArrayList<Attribute> attributeList= new ArrayList<Attribute>();
        System.out.println("2");
        for(String x: attributeNames){
            System.out.println("3");
            Attribute attribute = new Attribute();
            attribute.setName(generateName(x));
            attributeList.add(attribute);
            System.out.println("4");
        }
        System.out.println("5");
        System.out.println(name);
        dimension.setName(generateName(name));
        System.out.println("6");
        dimension.setAttributes(attributeList);
        System.out.println("7");
        starSchema.getDimension().add(dimension);

    }

    public void insertFact(StarSchema starSchema, String name, Type type, ArrayList<AggregateFunc> aggregateFunc) {
        Fact fact = new Fact();
        fact.setName(generateName(name));
        fact.setType(type);
        fact.setAggregateFuncs(aggregateFunc);
        starSchema.getFact().add(fact);
    }

    public String writeSchemaOuter(StarSchema starSchema) throws IOException, JAXBException {
        String returnString;
        String s= addNameToFile(starSchema.getName());
        if(!s.equals("true"))
            return s;
        else
        {
            generateAttributeCode(starSchema);
            ReadWriteXmlFile writeXmlFile= new ReadWriteXmlFile();
            s= writeXmlFile.createSchemaIfNotExist(starSchema.getName())+ " ";
            if(writeXmlFile.writeStarSchema(starSchema))
                return "Schema created successfully";
            else
                return "Error occurred in schema creation";
        }
    }

    private void generateAttributeCode(StarSchema starSchema) {
        int cnt=1;
        for(Dimension dimension: starSchema.getDimension())
            for(Attribute attribute: dimension.getAttributes()){
                attribute.setCode(cnt++);
            }
    }

    private String addNameToFile(String name) throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String fName = currentDirectory + "/storage/Schema_List.txt";
        File file= new File(fName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return "Error creating Schema_list.";
            }
        }
        FileWriter writer = new FileWriter(file, true);
        writer.write(name+ " ");
//        writer.flush();
        writer.close();
        return "true";
    }
}
