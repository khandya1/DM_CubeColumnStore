package SchemaCreate;

import UploadFile.UploadFile;

import java.util.Scanner;

public class SchemaCreate {



    public SchemaCreate(){
        System.out.println("Schema Creation");

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Select your choice");
        System.out.println("1. Create Star Schema");
        System.out.println("2. Upload data file");
        System.out.println("3. Exit");
        Scanner sc = new Scanner(System.in);

        int choice = sc.nextInt();
        System.out.println(choice);
        while (choice!=3){

            if(choice==1){
                NewSchema ns = new NewSchema();
            }else if (choice==2) {
                UploadFile uf = new UploadFile();
            }
        }
    }

    public static void main(String[] args) {
        SchemaCreate schemaCreation = new SchemaCreate();
    }
}
