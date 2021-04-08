import java.io.File;
import java.util.Scanner;

public class FolderCreation {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the database name");
        String dbname = sc.next();
        File file = new File("src/main/resources/"+dbname);
        boolean bool = file.mkdir();

        System.out.println("Enter number of dimensions");
        int count_dimensional_table = sc.nextInt();
        while(count_dimensional_table--)
        {
            System.out.println("Enter Dimensional table name");
            String table = sc.next();
            file = new File("src/main/resources/"+dbname+"/"+table);
            bool = file.mkdir();

            System.out.println("Enter number of attributes");
            int attributes = sc.nextInt();
            while(attributes--)
            {
                FileConversion fc = new FileConversion();
                String attribute_name = sc.next();
                fc.uploadfile(attribute_name);

            }
       }
    }
}
