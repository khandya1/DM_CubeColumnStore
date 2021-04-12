package SchemaCreate;

import Schema.Dimension;
import Schema.StarSchema;

import java.util.Scanner;

public class DimensionTables {

    StarSchema globalSchema;
    public int number_attri, numberDimension;
    Dimension d;
    java.util.List<String> list = new java.util.ArrayList<String>();
    String name;
    int x, y;
    String named;

    public DimensionTables(StarSchema s, int numberDimension) {
        globalSchema = s;
        this.numberDimension = numberDimension;

        d = new Dimension();

        System.out.println("Dimensional Tables");

        System.out.println("Enter Name of Dimensional Table");
        Scanner sc = new Scanner(System.in);
        String NameDimensionalTable = sc.next();
        System.out.println("Enter no. of Attribute");
        int totalAttribute = sc.nextInt();

        System.out.println("Please enter your primary key as the first attribute.");

        for(int i=0;i<totalAttribute;i++){
            String s2 = sc.next();
            System.out.println("************************");
            list.add(s2);
            System.out.println("/////////////////////////");
        }

        System.out.println("------------------------");

        SchemaCreationService schemaCreationService = new SchemaCreationService();

        System.out.println("$$$$$$$$$$$$$$$$$$$$");
        System.out.println("2nd \n " + globalSchema);
        System.out.println("name :- "+NameDimensionalTable);
        schemaCreationService.insertDimensionService(globalSchema, NameDimensionalTable, list);
        System.out.println("3rd \n " + globalSchema);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@");
        if (numberDimension != 1) {

            DimensionTables obj = new DimensionTables(this.globalSchema, this.numberDimension - 1);
            System.out.println("inside dimension table new ...");
            //obj.pack();
        } else {
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^");
            FactVariables f = new FactVariables(globalSchema);
        }
    }
}
