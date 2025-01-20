import java.util.Objects;
import java.util.Scanner;
public class Echo {
    private static String[] taskArray = new String[100];
    private static int taskCount = 0;

    //This function receives TearIt.ending as String input
    //This function prints out echo and ending message when user enter "bye"
    public static void echo(String ending) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!Objects.equals(input.toLowerCase(), "bye")) {
            if (Objects.equals(input.toLowerCase(), "list")) {
                Echo.list();
                input = sc.nextLine();
            } else {
                Echo.add(input);
                input = sc.nextLine();
            }

        }
        System.out.println("-------------------------------------------------");
        System.out.println(ending);
    }


    // This is a list function that display all task in taskArray upon input list/LIST or its variant
    public static void list() {
        System.out.println("    -------------------------------------------------");
        for (int i = 0; i < Echo.taskCount; i++) {
            System.out.println("    " + (i+1) + ". " + Echo.taskArray[i]);
        }
        System.out.println("    -------------------------------------------------");
    }

    public static void add(String input) {
        System.out.println("    -------------------------------------------------");
        Echo.taskArray[Echo.taskCount] = input;
        Echo.taskCount++;
        System.out.println("      added: " + input);
        System.out.println("    -------------------------------------------------");
    }

}
