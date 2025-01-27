package task;

import java.util.ArrayList;

import application.Parser;

/**
 * Tasklist class contains methods to deal with the tasklist such as add, delete, list and etc.
 */

public class Tasklist {
    private static final ArrayList<Task> TASK_LIST = new ArrayList<>();

    /**
     * Processes the given input string to add a task to the task list.
     * <p>
     * Depending on the input, this method handles different types of tasks:
     * "todo", "deadline", or "event". It validates the input, extracts task
     * details using regex, and adds the task to the task list if successful.
     * It also prints feedback messages to the console about the operation's outcome.
     *
     * @param input The command string specifying the task to be added. It should
     *              start with one of the keywords: "todo", "deadline", or "event",
     *              followed by the relevant task details.
     *              <ul>
     *                  <li><code>1</code>: Task creation was successful.</li>
     *                  <li><code>0</code>: The input did not start with a recognized keyword.</li>
     *                  <li><code>-1</code>: The "todo" task type was specified but lacked details.</li>
     *                  <li><code>-2</code>: The "deadline" task type was specified but lacked details.</li>
     *                  <li><code>-3</code>: The "event" task type was specified but lacked details.</li>
     *              </ul>
     * @return An integer status code representing the result of the operation:
     */
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

    /**
     * Adds a task object directly to the task list.
     * <p>
     * This method appends the provided {@link Task} object (e.g., {@link Todo}, {@link Deadline}, or {@link Event})
     * to the task list without any additional processing or validation.
     *
     * @param task The {@link Task} object to be added to the task list. It should be a valid task instance such as
     *             {@link Todo}, {@link Deadline}, or {@link Event}.
     */
    public static void add(Task task) {
        Tasklist.TASK_LIST.add(task);
    }

    /**
     * Deletes a task from the task list based on its position.
     * <p>
     * This method removes the task at the specified position in the task list if the position is valid.
     * It also updates the task count and provides console feedback about the operation.
     *
     * @param i The 1-based position of the task to be deleted. It must be greater than 0 and less than
     *          or equal to the total number of tasks in the task list.
     * @throws NumberFormatException If the input parameter is not a valid integer.
     *
     *                               <p>Console Output:</p>
     *                               <ul>
     *                                   <li>If the task is successfully deleted, the method prints the removed task and the updated task count.</li>
     *                                   <li>If the specified position is invalid, it prints an error message indicating the task does not exist.</li>
     *                               </ul>
     */
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

    /**
     * Displays all tasks in the task list.
     * <p>
     * This method prints a numbered list of all tasks currently stored in the task list to the console.
     * Each task is displayed on a new line, prefixed with its position in the list (1-based index).
     * The method also includes formatting lines for better readability.
     *
     * <p>Console Output:</p>
     * <ul>
     *     <li>A header indicating the start of the task list.</li>
     *     <li>A numbered list of tasks in the order they are stored.</li>
     *     <li>A footer indicating the end of the task list.</li>
     * </ul>
     */
    public static void list() {
        System.out.println("    -------------------------------------------------");
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < Task.getTaskCount(); i++) {
            System.out.println("    " + (i + 1) + ". " + Tasklist.TASK_LIST.get(i));
        }
        System.out.println("    -------------------------------------------------");
    }


    /**
     * Marks or unmarks a task in the task list and displays the updated list.
     * <p>
     * This overloaded version of the {@code list} method updates the completion status of a specified task
     * and prints a message, followed by the updated task list. If the specified task does not exist, it
     * displays an error message and still shows the current task list.
     *
     * @param i    The 1-based position of the task to mark or unmark. It must be greater than 0 and less
     *             than or equal to the total number of tasks in the task list.
     * @param mark {@code true} to mark the task as completed, or {@code false} to unmark it.
     * @param msg  A custom message to display after marking/unmarking the task. This can include
     *             congratulatory or humorous remarks based on the task's status.
     *
     *             <p>Console Output:</p>
     *             <ul>
     *                 <li>If the specified task is valid:
     *                     <ul>
     *                         <li>Marks/unmarks the task based on the {@code mark} parameter.</li>
     *                         <li>Prints the custom message {@code msg} along with the task's position.</li>
     *                         <li>Displays the updated list of tasks.</li>
     *                     </ul>
     *                 </li>
     *                 <li>If the specified task is invalid:
     *                     <ul>
     *                         <li>Prints an error message indicating the task does not exist.</li>
     *                         <li>Displays the current list of tasks.</li>
     *                     </ul>
     *                 </li>
     *             </ul>
     */
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


    /**
     * Lists the tasks that match the provided keyword.
     * <p>
     * This function iterates over the given list of tasks and displays each task
     * with its index in the list.
     *
     * @param lst the list of tasks that match the search keyword
     */
    public static void list(ArrayList<Task> lst) {
        int i = 1;
        for (Task t : lst) {
            System.out.println("    " + i + ". " + t.toString());
            i++;
        }
    }

    /**
     * Marks the specified task as done and displays it along with a congratulatory remark.
     *
     * @param i The 1-based position of the task to mark as done. It must be greater than 0 and less
     *          than or equal to the total number of tasks in the task list.
     *
     *          <p>Console Output:</p>
     *          <ul>
     *              <li>Marks the specified task as done.</li>
     *              <li>Prints a congratulatory message, "Well Done! You finished task {i}".</li>
     *              <li>Displays the updated list of tasks.</li>
     *          </ul>
     */
    public static void markRemark(Integer i) {
        Tasklist.list(i, true, "    Well Done! You finished task ");
    }

    /**
     * Unmarks the specified task as done and displays it along with a message of disappointment.
     *
     * @param i The 1-based position of the task to unmark. It must be greater than 0 and less
     *          than or equal to the total number of tasks in the task list.
     *
     *          <p>Console Output:</p>
     *          <ul>
     *              <li>Unmarks the specified task as not done.</li>
     *              <li>Prints a message, "Oh No! You haven't completed task {i}".</li>
     *              <li>Displays the updated list of tasks.</li>
     *          </ul>
     */
    public static void unmarkRemark(Integer i) {
        Tasklist.list(i, false, "    Oh No! You haven't completed task ");
    }


    /**
     * Marks the specified task as done without displaying any message or list.
     * <p>
     * This method is used when tasks are being loaded from a file.
     *
     * @param i The 0-based index of the task to mark as done. It must be a valid index within
     *          the task list.
     */
    public static void mark(int i) {
        Tasklist.TASK_LIST.get(i).mark();
    }

    /**
     * Unmarks the specified task as done without displaying any message or list.
     * <p>
     * This method is used when tasks are being loaded from a file.
     *
     * @param i The 0-based index of the task to unmark as not done. It must be a valid index
     *          within the task list.
     */
    public static void unmark(int i) {
        Tasklist.TASK_LIST.get(i).unmark();
    }

    /**
     * Searches for tasks that contain the given keyword in their descriptions.
     * <p>
     * This function iterates over all tasks in the task list and adds the tasks
     * whose description contains the provided keyword (case-insensitive) to a new list.
     *
     * @param input the keyword to search for in task descriptions
     * @return an ArrayList containing the tasks that match the keyword
     */
    public static ArrayList<Task> find(String input) {
        ArrayList<Task> lst = new ArrayList<>();

        for (Task t : TASK_LIST) {
            if (t.getLowerCaseDescription().contains(input.toLowerCase())) {
                lst.add(t);
            }
        }
        return lst;
    }

    /**
     * Retrieves the string representation of the specified task.
     *
     * @param i The 0-based index of the task to retrieve. It must be a valid index within the
     *          task list.
     * @return A string representation of the specified task.
     */
    public static String getTaskString(int i) {
        return Tasklist.TASK_LIST.get(i).toString();
    }

}
