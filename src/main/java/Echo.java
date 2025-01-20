import java.util.Objects;
import java.util.Scanner;
public class Echo {

    //This function receives TearIt.ending as String input
    //This function prints out echo and ending message when user enter "bye"
    public static void echo(String ending) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!Objects.equals(input, "bye")) {
            System.out.println("-------------------------------------------------");
            System.out.println(input);
            System.out.println("-------------------------------------------------");
            input = sc.nextLine();
        }
        System.out.println("-------------------------------------------------");
        System.out.println(ending);
    }
    
}
