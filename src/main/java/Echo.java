import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Echo {
    private static final ArrayList<Task> TASK_ARRAY = new ArrayList<>();
    private static final String PATH = "src/main/data/TearIT.txt";

    //This function receives TearIt.ending as String input
    //This function prints out echo and ending message when user enter "bye"
    //input1: the ending messaged passed from tearIT
    public static void echo(String ending) {

        try {
            Echo.readFromFile(Echo.PATH);
        } catch (FileNotFoundException e) {
            System.out.println("The file does not exist !");
        } finally {

        }
        
        Scanner sc = new Scanner(System.in);

        // ErrorCode added in to handle the command which is NOT TODO, DEADLINE, EVENT
        label:
        while (true) {
            String input = sc.nextLine();

            // SHOULD ADD AN ERROR HANDLING FOR THIS (currently: list 1/ list 2 also OK... future extension maybe what if list 2: the first two are displayed) TODO
            // This deals with empty command or mutliple spaces without command
            if (input.trim().isEmpty()) {
                System.out.println("    -------------------------------------------------");
                System.out.println("    System does not support such command. Only todo ..., deadline ..., event..., mark..., unmark..., delete... and list and bye only !");
            } else {
                String[] parts = input.split(" ");
                String part = parts[0];
                int len = parts.length;

                switch (part.toLowerCase()) {
                case "list":
                    Echo.list();
                    break;
                case "mark":

                    if (len == 1) {
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

                    break;
                case "unmark":

                    if (len == 1) {
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

                    break;
                case "bye":

                    try {
                            Echo.writeToFile(Echo.PATH);
                    } catch (IOException e) {
                        System.out.println("    Something went wrong: " + e.getMessage());
                        System.out.println("    The data is not saved !");
                    } finally {

                    }

                    break label;
                case "delete":
                    if (len == 1) {
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
                    break;
                default:
                    int errorCode = Echo.add(input);
                    final int ERROR_CODE_UNKNOWN_COMMAND = 0;
                    final int ERROR_CODE_TODO_COMMAND = -1;
                    final int ERROR_CODE_DEADLINE_COMMAND = -2;
                    final int ERROR_CODE_EVENT_COMMAND = -3;

                    // should also specify input format to user also like from ... /to .... TODO
                    switch (errorCode) {
                    case ERROR_CODE_UNKNOWN_COMMAND:
                        System.out.println("    System does not support such command. Only todo ..., deadline ..., event..., mark..., unmark..., delete... and list and bye only !");
                        break;
                    case ERROR_CODE_TODO_COMMAND:
                        System.out.println("    There must be something after todo !");
                        break;
                    case ERROR_CODE_DEADLINE_COMMAND:
                        System.out.println("    There must be something after deadline !");
                        break;
                    case ERROR_CODE_EVENT_COMMAND:
                        System.out.println("    There must be something after event !");
                        break;
                    default:
                    }
                    break;
                }
            }
        }


        System.out.println("-------------------------------------------------");
        System.out.println(ending);

    }


    // This is a list function that display all task in TASK_ARRAY upon input list/LIST or its variant
    // input:None
    private static void list() {
        System.out.println("    -------------------------------------------------");
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < Task.getTaskCount(); i++) {
            System.out.println("    " + (i + 1) + ". " + Echo.TASK_ARRAY.get(i));
        }
        System.out.println("    -------------------------------------------------");
    }

    // This is an overloaded list function that use to mark and display task by receiving input
    // input1: task order input2: mark or unmark(true or false) input3: String msg (congratz or humiliating msg based on completion)
    private static void list(Integer i, boolean mark, String msg) {
        if (i <= Task.getTaskCount() && i != 0) {
            if (mark) {
                Echo.TASK_ARRAY.get(i - 1).mark();
            } else {
                Echo.TASK_ARRAY.get(i - 1).unmark();
            }
            System.out.println("    -------------------------------------------------");
            System.out.println(msg + i + " .");
            for (int j = 0; j < Task.getTaskCount(); j++) {
                System.out.println("    " + (j + 1) + ". " + Echo.TASK_ARRAY.get(j));
            }
            System.out.println("    -------------------------------------------------");
        } else {
            System.out.println("    -------------------------------------------------");
            System.out.println("You do not have task " + i + "!");
            for (int j = 0; j < Task.getTaskCount(); j++) {
                System.out.println("    " + (j + 1) + ". " + Echo.TASK_ARRAY.get(j));
            }
            System.out.println("    -------------------------------------------------");
        }

    }

    // This function add every user input  except mark/list/bye as task to TASK_ARRAY
    // If none of them match.... REMEMBER to add in handle (we only handle for TODO, DEADLINE and EVENT)
    // Receive input 1: task description input 2: task type (event, deadline, todo)
    private static int add(String input) {
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
        String message1 = String.format("       %s", Echo.TASK_ARRAY.get(Task.getTaskCount()-1));
        System.out.println(message1);

        String message2 = String.format("    Now you have %d tasks in the list.", Task.getTaskCount());
        System.out.println(message2);

        System.out.println("    -------------------------------------------------");
        return 1;
    }

    // This is a delete function where user can use it to delete task
    // input1: An integer

    private static void delete(Integer i)
            throws NumberFormatException {
        System.out.println("    -------------------------------------------------");
        if (i <= Task.getTaskCount() && i != 0) {
            String message = String.format("    %s", Echo.TASK_ARRAY.get(i - 1).toString());
            Echo.TASK_ARRAY.remove(i - 1);
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
    private static void markRemark(Integer i) {
        Echo.list(i, true, "    Well Done! You finished task ");
    }

    // This function unmarks the ith numbered task done and display it
    // input1 : the order of task
    private static void unmarkRemark(Integer i) {
        Echo.list(i, false, "    Oh No! You haven't completed task ");
    }

    // This function extract the info from user input via regex and create the corresponding task object
    // This function also adds taskCount for all objects created here [ie: only Deadline, Event, Todo]
    // input1:regex input2:userinput input3: number of needed group
    private static void extractAndCreate(String userInput, String regex, int groups ) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.find()) {
            if (groups == 1) {
                Echo.TASK_ARRAY.add(new Todo(matcher.group(1)));
            } else if (groups == 2) {
                Echo.TASK_ARRAY.add(new Deadline(matcher.group(1), matcher.group(2)));
            } else {
                Echo.TASK_ARRAY.add(new Event(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        Task.addTaskCount(); // TODO this can be done when new task created in respective task class
    }


    // This function is used to extract test file string line by line into a task object
    // input1: task string from text file
    public static void extractTask(String task) {

        if (!Objects.equals(task, "")) {
            // Regex for each task type
            Pattern todoPattern = Pattern.compile("\\[T\\]\\[(X| )\\] (.*)");
            Pattern deadlinePattern = Pattern.compile("\\[D\\]\\[(X| )\\] (.*) \\(by: (.*)\\)");
            Pattern eventPattern = Pattern.compile("\\[E\\]\\[(X| )\\] (.*) \\(from: (.*) to: (.*)\\)");

            Matcher todoMatcher = todoPattern.matcher(task);
            Matcher deadlineMatcher = deadlinePattern.matcher(task);
            Matcher eventMatcher = eventPattern.matcher(task);

            // Match Todo task
            if (todoMatcher.matches()) {
                boolean isDone = todoMatcher.group(1).equals("X");
                TASK_ARRAY.add(new Todo(todoMatcher.group(2)));
                if (isDone) {
                    TASK_ARRAY.get(Task.getTaskCount()).mark();
                }

            }

            // Match Deadline task
            if (deadlineMatcher.matches()) {
                boolean isDone = deadlineMatcher.group(1).equals("X");
                TASK_ARRAY.add(new Deadline(deadlineMatcher.group(2), deadlineMatcher.group(3)));
                if (isDone) {
                    TASK_ARRAY.get(Task.getTaskCount()).mark();
                }
            }

            // Match Event task
            if (eventMatcher.matches()) {
                boolean isDone = eventMatcher.group(1).equals("X");
                TASK_ARRAY.add(new Event(eventMatcher.group(2), eventMatcher.group(3), eventMatcher.group(4)));
                if (isDone) {
                    TASK_ARRAY.get(Task.getTaskCount()).mark();
                }
            }

            Task.addTaskCount();
        }

    }

    private static void writeToFile(String filePath) throws IOException {
        FileWriter fw = new FileWriter(filePath, false); //overwrite them
        for (int i = 0; i < Task.getTaskCount(); i++) {
            fw.write(TASK_ARRAY.get(i).toString() + System.lineSeparator());
        }
        fw.close();
    }

    private static void readFromFile(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            Echo.extractTask(s.nextLine());
        }
    }

}
