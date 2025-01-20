import java.util.Objects;
import java.util.Scanner;
public class Echo {
    private static Task[] taskArray = new Task[100];

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
        for (int i = 0; i < Task.getTaskCount(); i++) {
            System.out.println("    " + (i+1) + ". " + Echo.taskArray[i]);
        }
        System.out.println("    -------------------------------------------------");
    }

    // This function add every user input  except mark/list/bye as task to taskArray
    // Receive user input as String
    public static void add(String input) {
        System.out.println("    -------------------------------------------------");
        Echo.taskArray[Task.getTaskCount()] = new Task(input);
        Task.addTaskCount();
        System.out.println("      added: " + input);
        System.out.println("    -------------------------------------------------");
    }


//    public static void mark(int num) {
//
//    }

}
