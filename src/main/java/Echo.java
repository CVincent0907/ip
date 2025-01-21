import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Echo {
    private static final Task[] taskArray = new Task[100];

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
        System.out.println("    Here are the tasks in your list:");
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
    // Receive input 1: task description input 2: task type (event, deadline, todo)
    // If none of them match.... REMEMBER to add in handle (we only handle for TODO, DEADLINE and EVENT)
    public static void add(String input) {
        System.out.println("    -------------------------------------------------");

        System.out.println("    Got it. I've added this task:");
        if (input.toLowerCase().startsWith("todo")) {
           Echo.extractAndCreate(input, Todo.regex, 1);
        } else if (input.toLowerCase().startsWith("deadline")) {
            Echo.extractAndCreate(input, Deadline.regex, 2);
        } else if (input.toLowerCase().startsWith("event")) {
            Echo.extractAndCreate(input, Event.regex, 3);
        }


        String message1 = String.format("       %s", Echo.taskArray[Task.getTaskCount()-1]);
        System.out.println(message1);

        String message2 = String.format("    Now you have %d tasks in the list.", Task.getTaskCount());
        System.out.println(message2);

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

    // This function extract the info from user input via regex and create the corresponding task object
    // This function also adds taskCount for all objects created here [ie: only Deadline, Event, Todo]
    // input1:regex input2:userinput input3: number of needed group
    public static void extractAndCreate(String userInput, String regex, int groups ) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userInput);
        if (matcher.find()) {
            if (groups == 1) {
                Echo.taskArray[Task.getTaskCount()] = new Todo(matcher.group(1));
            } else if (groups == 2) {
                Echo.taskArray[Task.getTaskCount()] = new Deadline(matcher.group(1), matcher.group(2));
            } else {
                Echo.taskArray[Task.getTaskCount()] = new Event(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        Task.addTaskCount();
    }

}
