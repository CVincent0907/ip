import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Echo {
    private static final ArrayList<Task> taskArray = new ArrayList<>();

    //This function receives TearIt.ending as String input
    //This function prints out echo and ending message when user enter "bye"
    //input1: the ending messaged passed from tearIT
    public static void echo(String ending) {
        Scanner sc = new Scanner(System.in);

        // ErrorCode added in to handle the command which is NOT TODO, DEADLINE, EVENT
        while (true) {
            String input = sc.nextLine();
            String[] parts = input.split(" ");
            String part = parts[0];
            int len = parts.length;

            // SHOULD ADD AN ERROR HANDLING FOR THIS (currently: list 1/ list 2 also OK... future extension maybe what if list 2: the first two are displayed)
            if (Objects.equals(part.toLowerCase(), "list")) {
                Echo.list();
            } else if (Objects.equals(part.toLowerCase(), "mark")) {

                if (len == 1){
                    System.out.println("    There must be an integer after mark !");
                } else {
                    try {
                        Echo.markRemark(Integer.parseInt(parts[1]));
                    } catch (NumberFormatException e) {
                        System.out.println("    -------------------------------------------------");
                        System.out.println("    " + e.getMessage());
                        System.out.println("    The argument should be an integer!");
                        System.out.println("    -------------------------------------------------");
                    } finally {

                    }
                }

            } else if (Objects.equals(part.toLowerCase(), "unmark")) {

                if (len == 1){
                    System.out.println("    There must be an integer after unmark !");
                } else {
                    try {
                        Echo.unmarkRemark(Integer.parseInt(parts[1]));
                    } catch (NumberFormatException e) {
                        System.out.println("    -------------------------------------------------");
                        System.out.println("    " + e.getMessage());
                        System.out.println("    The argument should be an integer!");
                        System.out.println("    -------------------------------------------------");
                    } finally {

                    }
                }

            } else if (Objects.equals(part.toLowerCase(), "bye")) {
                break;
            } else if (Objects.equals(part.toLowerCase(), "delete")) {
                if (len == 1){
                    System.out.println("    There must be an integer after delete !");
                } else {
                    try {
                        Echo.delete(Integer.parseInt(parts[1]));
                    } catch (NumberFormatException e) {
                        System.out.println("    -------------------------------------------------");
                        System.out.println("    " + e.getMessage());
                        System.out.println("    The argument should be an integer!");
                        System.out.println("    -------------------------------------------------");
                    } finally {

                    }
                }
            } else {
                int errorCode = Echo.add(input);
                // should also specify input format to user also like from ... /to ....
                switch (errorCode) {
                    case 0:
                        System.out.println("    System does not support such command. Only todo ..., deadline ..., event..., mark..., unmark..., delete... and list and bye only !");
                        break;
                    case -1:
                        System.out.println("    There must be something after todo !");
                        break;
                    case -2:
                        System.out.println("    There must be something after deadline !");
                        break;
                    case -3:
                        System.out.println("    There must be something after event !");
                        break;
                    default:
                }
            }
        }

        System.out.println("-------------------------------------------------");
        System.out.println(ending);

    }


    // This is a list function that display all task in taskArray upon input list/LIST or its variant
    // input:None
    public static void list() {
        System.out.println("    -------------------------------------------------");
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < Task.getTaskCount(); i++) {
            System.out.println("    " + (i+1) + ". " + Echo.taskArray.get(i));
        }
        System.out.println("    -------------------------------------------------");
    }

    // This is an overloaded list function that use to mark and display task by receiving input
    // input1: task order input2: mark or unmark(true or false) input3: String msg (congratz or humiliating msg based on completion)
    public static void list(Integer i, boolean mark, String msg) {
        if (i <= Task.getTaskCount() && i!=0) {
            if (mark) {
                Echo.taskArray.get(i-1).mark();
            } else {
                Echo.taskArray.get(i-1).unmark();
            }
            System.out.println("    -------------------------------------------------");
            System.out.println(msg + i + " .");
            for (int j = 0; j < Task.getTaskCount(); j++) {
                System.out.println("    " + (j+1) + ". " + Echo.taskArray.get(j));
            }
            System.out.println("    -------------------------------------------------");
        } else {
            System.out.println("    -------------------------------------------------");
            System.out.println("You do not have task " + i + "!");
            for (int j = 0; j < Task.getTaskCount(); j++) {
                System.out.println("    " + (j+1) + ". " + Echo.taskArray.get(j));
            }
            System.out.println("    -------------------------------------------------");
        }

    }

    // This function add every user input  except mark/list/bye as task to taskArray
    // If none of them match.... REMEMBER to add in handle (we only handle for TODO, DEADLINE and EVENT)
    // Receive input 1: task description input 2: task type (event, deadline, todo)
    public static int add(String input) {
        System.out.println("    -------------------------------------------------");
        String[] parts = input.split(" ");
        int len = parts.length;

        if (input.toLowerCase().startsWith("todo")) {
            if (len <= 1) {
                return -1;
            }
           Echo.extractAndCreate(input, Todo.regex, 1);
        } else if (input.toLowerCase().startsWith("deadline")) {
            if (len <= 1) {
                return -2;
            }
            Echo.extractAndCreate(input, Deadline.regex, 2);
        } else if (input.toLowerCase().startsWith("event")) {
            if (len <= 1) {
                return -3;
            }
            Echo.extractAndCreate(input, Event.regex, 3);
        } else {
            return 0;
        }

        System.out.println("    Got it. I've added this task:");
        String message1 = String.format("       %s", Echo.taskArray.get(Task.getTaskCount()-1));
        System.out.println(message1);

        String message2 = String.format("    Now you have %d tasks in the list.", Task.getTaskCount());
        System.out.println(message2);

        System.out.println("    -------------------------------------------------");
        return 1;
    }

    // This is a delete function where user can use it to delete task
    // input1: An integer

    public static void delete(Integer i) throws NumberFormatException {
        System.out.println("    -------------------------------------------------");
        if (i <= Task.getTaskCount() && i!=0) {
            String message = String.format("    %s", Echo.taskArray.get(i-1).toString());
            Echo.taskArray.remove(i-1);
            Task.reduceTaskCount();
            System.out.println("    Hey bro! I have removed task " + i + ".");
            System.out.println(message);
        } else {
            System.out.println("    Hey bro! You do not have task " + i + ".");
        }
        String message = String.format("    Now you have %d tasks in the list.", Task.getTaskCount());
        System.out.println(message);
        System.out.println("    -------------------------------------------------");
    }

    // This function marks the ith numbered task done and display it
    // input1 : the order of task
    public static void markRemark(Integer i) {
        Echo.list(i, true, "    Well Done! You finished task ");
    }

    // This function unmarks the ith numbered task done and display it
    // input1 : the order of task
    public static void unmarkRemark(Integer i) {
        Echo.list(i, false, "    Oh No! You haven't completed task ");
    }

    // This function extract the info from user input via regex and create the corresponding task object
    // This function also adds taskCount for all objects created here [ie: only Deadline, Event, Todo]
    // input1:regex input2:userinput input3: number of needed group
    public static void extractAndCreate(String userInput, String regex, int groups ) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userInput);
        if (matcher.find()) {
            if (groups == 1) {
                Echo.taskArray.add(new Todo(matcher.group(1)));
            } else if (groups == 2) {
                Echo.taskArray.add(new Deadline(matcher.group(1), matcher.group(2)));
            } else {
                Echo.taskArray.add(new Event(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        Task.addTaskCount();
    }

}
