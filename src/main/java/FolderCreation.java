import java.io.File;
import java.util.Scanner;

public class FolderCreation {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the database name");
        String dbname = sc.next();
        File file = new File("src/main/resources/"+dbname);
        boolean bool = file.mkdir();


        int count_dimensional_table = 1;
        while(true){
            System.out.println("Enter"+ count_dimensional_table +"Dimensional table path");
            String path = sc.next();
            String[] stringarray = path.split("/");
            String s1 = stringarray[stringarray.length-1];
            System.out.println(s1);
            String s2 = s1.substring(0,s1.length()-4);
            System.out.println(s2);

            File file1 = new File("src/main/resources/"+dbname+"/"+s2);
            String s3 = "src/main/resources/"+dbname+"/"+s2;
            System.out.println(s3);
            bool = file1.mkdir();


            if(bool){
                System.out.println("Directory created successfully");
            }else{
                System.out.println("Sorry couldn’t create specified directory");
            }
            String exit = sc.next();
            if(exit.equals("yes")){
                break;
            }
        }
    }
}
