package SchemaCreate;

import Schema.StarSchema;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.SortedMap;

public class NewSchema {

    StarSchema globalSchema;
    public int number_Dimension;


    public NewSchema(){
        System.out.println("New Schema Creation");
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Schema Name");
        String schemaName = sc.next();
        System.out.println("Enter no. of Dimensional Table");
        int totalDimensionalTable = sc.nextInt();



        SchemaCreationService service = new SchemaCreationService();
        boolean doesSameFileExist = true;
        try {
            doesSameFileExist = service.doesSchemaExistService(schemaName);
        } catch (IOException ex) {
            System.out.println("Unknown occurred in checking for same file name");
        }


        if (!doesSameFileExist) {
            globalSchema = service.newSchemaService(schemaName);
            System.out.println("1st \n" + globalSchema);
            number_Dimension = totalDimensionalTable;
        } else {
            System.out.println("Schema already exists with the same file name");
            System.out.println("make it again");
            NewSchema ns = new NewSchema();
            System.exit(0);
        }


        System.out.println("Enter Dimension Tables Details");
        DimensionTables d = new DimensionTables(globalSchema, number_Dimension);
    }
}

