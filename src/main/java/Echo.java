import java.util.Objects;
import java.util.Scanner;
public class Echo {
    private static Task[] taskArray = new Task[100];

    //This function receives TearIt.ending as String input
    //This function prints out echo and ending message when user enter "bye"
    public static void echo(String ending) {
        Scanner sc = new Scanner(System.in);

        // might need to add in error handling for mark but without number or number that is out of bound
        while (true) {
            String input = sc.nextLine();
            String[] parts = input.split(" ");
            String part = parts[0];

            if (Objects.equals(part.toLowerCase(), "list")) {
                Echo.list();
            } else if (Objects.equals(part.toLowerCase(), "mark") && parts.length == 2) {
                Echo.markRemark(Integer.parseInt(parts[1]));
            } else if (Objects.equals(part.toLowerCase(), "unmark") && parts.length == 2) {
                Echo.unmarkRemark(Integer.parseInt(parts[1]));
            } else if (Objects.equals(part.toLowerCase(), "bye")) {
                break;
            } else {
                Echo.add(input);
            }
        }

        System.out.println("-------------------------------------------------");
        System.out.println(ending);
    }


    // This is a list function that display all task in taskArray upon input list/LIST or its variant
    public static void list() {
        System.out.println("    -------------------------------------------------");
        System.out.println("      Here are the tasks in your list:");
        for (int i = 0; i < Task.getTaskCount(); i++) {
            System.out.println("    " + (i+1) + ". " + Echo.taskArray[i]);
        }
        System.out.println("    -------------------------------------------------");
    }

    // This is an overloaded list function that use to mark and display task by receiving input
    // input: task number and boolean mark and String msg
    public static void list(int i, boolean mark, String msg) {
        if (i <= Task.getTaskCount()) {
            if (mark) {
                Echo.taskArray[i-1].mark();
            } else {
                Echo.taskArray[i-1].unmark();
            }
            System.out.println("    -------------------------------------------------");
            System.out.println(msg + i + " .");
            for (int j = 0; j < Task.getTaskCount(); j++) {
                System.out.println("    " + (j+1) + ". " + Echo.taskArray[j]);
            }
            System.out.println("    -------------------------------------------------");
        } else {
            System.out.println("    -------------------------------------------------");
            System.out.println("You do not have task " + i + "!");
            for (int j = 0; j < Task.getTaskCount(); j++) {
                System.out.println("    " + (j+1) + ". " + Echo.taskArray[j]);
            }
            System.out.println("    -------------------------------------------------");
        }

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

    // This function marks the ith numbered task done and display it
    // Receive int i as input
    public static void markRemark(int i) {
        Echo.list(i, true, "    Well Done! You finished task ");
    }

    // This function unmarks the ith numbered task done and display it
    // Receive int i as input
    public static void unmarkRemark(int i) {
        Echo.list(i, false, "   Oh No! You haven't completed task ");
    }


}
