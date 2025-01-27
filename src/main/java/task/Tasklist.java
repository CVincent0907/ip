package task;

import java.util.ArrayList;

import application.Parser;

public class Tasklist {
    private static final ArrayList<Task> TASK_LIST = new ArrayList<>();

    // This function add every user input  except mark/list/bye as task to TASK_ARRAY
    // If none of them match.... REMEMBER to add in handle (we only handle for TODO, DEADLINE and EVENT)
    // Receive input 1: task description input 2: task type (event, deadline, todo)
    public static int add(String input) {
        System.out.println("    -------------------------------------------------");
        String[] parts = input.split(" ");
        int len = parts.length;
        boolean flag;

        if (input.toLowerCase().startsWith("todo")) {
            if (len <= 1) {
                return -1;
            }
            flag = Parser.extractAndCreateTask(input, Todo.REGEX_1, 1);
        } else if (input.toLowerCase().startsWith("deadline")) {
            if (len <= 1) {
                return -2;
            }
            flag = Parser.extractAndCreateTask(input, Deadline.DATE_TIME_REGEX_1, 2);
        } else if (input.toLowerCase().startsWith("event")) {
            if (len <= 1) {
                return -3;
            }
            flag = Parser.extractAndCreateTask(input, Event.DATE_TIME_REGEX_1, 3);
        } else {
            return 0;
        }

        if (flag) {
            System.out.println("    Got it. I've added this task:");
            String message1 = String.format("       %s", Tasklist.TASK_LIST.get(Task.getTaskCount() - 1));
            System.out.println(message1);
        } else {
            System.out.println("    task.Task creation unsuccessful !");
        }

        String message2 = String.format("    Now you have %d tasks in the list.", Task.getTaskCount());
        System.out.println(message2);

        System.out.println("    -------------------------------------------------");
        return 1;
    }

    // This is not an overloading function
    // This function add task object directly to the tasklist
    // input1: task object (todo, deadline, event)
    public static void add(Task task) {
        Tasklist.TASK_LIST.add(task);
    }

    // This is a delete function where user can use it to delete task
    // input1: An integer
    public static void delete(Integer i) throws NumberFormatException {
        System.out.println("    -------------------------------------------------");
        if (i <= Task.getTaskCount() && i != 0) {
            String message = String.format("    %s", Tasklist.TASK_LIST.get(i - 1).toString());
            Tasklist.TASK_LIST.remove(i - 1);
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

    // This is a list function that display all task in TASK_ARRAY upon input list/LIST or its variant
    // input:None
    public static void list() {
        System.out.println("    -------------------------------------------------");
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < Task.getTaskCount(); i++) {
            System.out.println("    " + (i + 1) + ". " + Tasklist.TASK_LIST.get(i));
        }
        System.out.println("    -------------------------------------------------");
    }

    // This is an overloaded list function that use to mark and display task by receiving input
    // input1: task order input2: mark or unmark(true or false) input3: String msg (congratz or humiliating msg based on completion)
    public static void list(Integer i, boolean mark, String msg) {
        if (i <= Task.getTaskCount() && i != 0) {
            if (mark) {
                Tasklist.TASK_LIST.get(i - 1).mark();
            } else {
                Tasklist.TASK_LIST.get(i - 1).unmark();
            }
            System.out.println("    -------------------------------------------------");
            System.out.println(msg + i + " .");
            for (int j = 0; j < Task.getTaskCount(); j++) {
                System.out.println("    " + (j + 1) + ". " + Tasklist.TASK_LIST.get(j));
            }
            System.out.println("    -------------------------------------------------");
        } else {
            System.out.println("    -------------------------------------------------");
            System.out.println("You do not have task " + i + "!");
            for (int j = 0; j < Task.getTaskCount(); j++) {
                System.out.println("    " + (j + 1) + ". " + Tasklist.TASK_LIST.get(j));
            }
            System.out.println("    -------------------------------------------------");
        }
    }

    // This function marks the ith numbered task done and display it
    // input1 : the order of task
    public static void markRemark(Integer i) {
        Tasklist.list(i, true, "    Well Done! You finished task ");
    }

    // This function unmarks the ith numbered task done and display it
    // input1 : the order of task
    public static void unmarkRemark(Integer i) {
        Tasklist.list(i, false, "    Oh No! You haven't completed task ");
    }

    // This function mark the task without remark (called when loading in file)
    // input1: taskorder
    public static void mark(int i) {
        Tasklist.TASK_LIST.get(i).mark();
    }

    // This function unmark the task without remark (called when loading in file)
    // input1: taskorder
    public static void unmark(int i) {
        Tasklist.TASK_LIST.get(i).unmark();
    }

    public static String getTaskString(int i) {
        return Tasklist.TASK_LIST.get(i).toString();
    }

}
