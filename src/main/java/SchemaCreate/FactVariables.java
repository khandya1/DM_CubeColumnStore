package SchemaCreate;

import Schema.AggregateFunc;
import Schema.StarSchema;
import Schema.Type;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class FactVariables {
    Scanner sc = new Scanner(System.in);
    StarSchema globalSchema = new StarSchema();
    String name;
    Schema.Type type;
    AggregateFunc aggregate;

    HashSet<AggregateFunc> fns =new HashSet<AggregateFunc>();
    SchemaCreationService schemaCreationService = new SchemaCreationService();
    ArrayList<AggregateFunc> fnList;
    public FactVariables(StarSchema s) {

        System.out.println("4th \n" + s);
        System.out.println("5th \n" + globalSchema);
        globalSchema = s;
        System.out.println("Fact Variable and Aggregate functions Details");

        System.out.println("These are the different types you can use on Fact Variable");
        System.out.println();
        System.out.println("1. Numeric");
        System.out.println("2. String");
        System.out.println();
        System.out.println("These are the Aggregate functions you can use on Fact Variables");
        System.out.println();
        System.out.println("1. Sum");
        System.out.println("2. Count");
        System.out.println("3. Average");

        System.out.println("Enter Fact Variable Name");
        String FactVariableName = sc.next();
        System.out.println("Enter Your Choice for Types");
        int typeChoice = sc.nextInt();

        if(typeChoice==1)
            type = Type.NUMERIC;
        else
            type = Type.STRING;
        System.out.println("Enter Your Choice for Aggregate Function");
        int aggregateFunction = sc.nextInt();

        if(aggregateFunction==1)
            fns.add(AggregateFunc.SUM);
        else if(aggregateFunction==2)
            fns.add(AggregateFunc.COUNT);
        else
            fns.add(AggregateFunc.AVG);

        fnList =new ArrayList<AggregateFunc>(fns);


        System.out.println("Want to add more Fact Variables yes or no");

        String yesno = sc.next();
        if(yesno=="yes"){
            new FactVariables(s);
        }else{
            schemaCreationService.insertFactService(globalSchema, FactVariableName, type, fnList);
            String ans = "";
            try{
                ans = schemaCreationService.writeSchemaService(globalSchema);
                System.out.println(ans);
                System.out.println(globalSchema);
                SchemaCreate schemaCreate = new SchemaCreate();
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(ans);
        }
    }

}
