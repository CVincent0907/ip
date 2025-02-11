package task;

import java.util.ArrayList;

import application.Parser;

/**
 * Tasklist class contains methods to deal with the tasklist such as add, delete, list and etc.
 */
public class Tasklist {
    private static final ArrayList<Task> TASK_LIST = new ArrayList<>();

    /**
     * Adds a task to the task list based on the user input.
     * <p>
     * This method processes the input to determine whether it is a "todo", "deadline", or "event" task.
     * If the input format is invalid, it returns an appropriate error message.
     * </p>
     *
     * @param input The full command entered by the user.
     * @return A string message indicating the result of the operation.
     *
     *      <p>Example Outputs:</p>
     *      <pre>
     *      Got it. I've added this task:
     *          [T] Read book
     *      Now you have 3 tasks in the list.
     *      </pre>
     *      <pre>
     *      There must be something after "todo"!
     *      </pre>
     *      <pre>
     *      System does not support such command. Only todo ..., deadline ..., event...,
     *              mark..., unmark..., delete..., find..., list and bye!
     *      </pre>
     */
    public static String add(String input) {
        String[] parts = input.split(" ");
        int len = parts.length;

        return Tasklist.getAddTaskMessage(input, len);
    }

    private static String getAddTaskMessage(String input, int len) {
        boolean taskCreationStatusFlag;
        if (input.toLowerCase().startsWith("todo")) {
            if (len <= 1) {
                final String TODO_EMPTY_ARGUMENT_REMINDER = "There must be something after \"todo\"!";
                return TODO_EMPTY_ARGUMENT_REMINDER;
            }

            taskCreationStatusFlag = Parser.extractAndCreateTask(input, Todo.REGEX_1, 1);
        } else if (input.toLowerCase().startsWith("deadline")) {
            if (len <= 1) {
                final String DEADLINE_EMPTY_ARGUMENT_REMINDER = "There must be something after \"deadline\"!";
                return DEADLINE_EMPTY_ARGUMENT_REMINDER;
            }

            taskCreationStatusFlag = Parser.extractAndCreateTask(input, Deadline.DATE_TIME_REGEX_1, 2);
        } else if (input.toLowerCase().startsWith("event")) {
            if (len <= 1) {
                final String EVENT_EMPTY_ARGUMENT_REMINDER  = "There must be something after \"event\"!";
                return EVENT_EMPTY_ARGUMENT_REMINDER;
            }

            taskCreationStatusFlag = Parser.extractAndCreateTask(input, Event.DATE_TIME_REGEX_1, 3);
        } else {
            final String OUT_OF_SERVICE_REMINDER = "System does not support such command. Only todo ..., deadline ...," 
                    + " event..., mark..., unmark..., delete..., find..., list and bye!";
            return OUT_OF_SERVICE_REMINDER;
        }

        return Tasklist.getTaskCreationMessage(taskCreationStatusFlag);
    }

    private static String getTaskCreationMessage(boolean taskCreationStatusFlag) {
        // Happy path refactoring
        StringBuilder message = new StringBuilder();
        if (!taskCreationStatusFlag) {
            message.append("    Input format is incorrect.\n")
                    .append("    todo input format :todo XX\n")
                    .append("    deadline input format :deadline XX /by dd-mm-yyyy hhmm\n")
                    .append("    event input format :event XX /from dd-mm-yyyy hhmm /to dd-mm-yyyy hhmm");
            return message.toString();
        }
        message.append("Got it. I've added this task:\n");
        message.append("   ").append(Tasklist.TASK_LIST.get(Task.getTaskCount() - 1)).append("\n");
        message.append("Now you have ").append(Task.getTaskCount()).append(" tasks in the list.");
        return message.toString();
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
     * Deletes a task from the task list based on its position and returns the result as a string.
     * <p>
     * This method removes the task at the specified position if the position is valid.
     * It updates the task count and provides feedback about the operation.
     * </p>
     *
     * @param taskNumber The 1-based position of the task to be deleted. It must be greater than 0 and less than
     *          or equal to the total number of tasks in the task list.
     * @return A formatted string containing feedback about the deletion and the updated task count.
     * @throws NumberFormatException If the input parameter is not a valid integer.
     *
     *      <p>Example Output:</p>
     *      <pre>
     *      Hey bro! I have removed task 2.
     *      [T] Read book
     *      Now you have 2 tasks in the list.
     *      </pre>
     *      <p>
     *      If the task number is invalid:
     *      </p>
     *      <pre>
     *      Hey bro! You do not have task 5.
     *      Now you have 3 tasks in the list.
     *      </pre>
     */
    public static String delete(Integer taskNumber) throws NumberFormatException {

        StringBuilder output = new StringBuilder();
        boolean isValidTaskNumber = taskNumber > 0 && taskNumber <= Task.getTaskCount();
        if (isValidTaskNumber) {
            int taskIndex = taskNumber - 1;
            String taskInfo = Tasklist.TASK_LIST.get(taskIndex).toString();
            Tasklist.TASK_LIST.remove(taskIndex);
            Task.reduceTaskCount();

            output.append("Hey bro! I have removed task ").append(taskNumber).append(".\n");
            output.append(taskInfo).append("\n");
        } else {
            output.append("Hey bro! You do not have task ").append(taskNumber).append(".\n");
        }

        output.append("Now you have ").append(Task.getTaskCount()).append(" tasks in the list.\n");
        return output.toString();
    }

    /**
     * Returns all tasks in the task list as a formatted string.
     * <p>
     * This method constructs a numbered list of all tasks currently stored
     * in the task list and returns it as a single formatted string.
     *
     * @return A formatted string representing the task list.
     */
    public static String list() {

        StringBuilder taskListBuilder = new StringBuilder();
        taskListBuilder.append("Here are the tasks in your list:\n");

        for (int i = 0; i < Task.getTaskCount(); i++) {
            taskListBuilder.append((i + 1)).append(". ")
                    .append(Tasklist.TASK_LIST.get(i))
                    .append("\n");
        }

        return taskListBuilder.toString();
    }

    /**
     * Marks or unmarks a task in the task list and returns the updated list as a string.
     * <p>
     * This method updates the completion status of a specified task and returns a message,
     * followed by the updated task list. If the specified task does not exist, it returns an
     * error message and still includes the current task list.
     * </p>
     *
     * @param taskNumber    The 1-based position of the task to isTaskMarked or unmark. It must be greater than 0
     *             and less than or equal to the total number of tasks in the task list.
     * @param isTaskMarked {@code true} to isTaskMarked the task as completed, or {@code false} to unmark it.
     * @param msg  A custom message to display after marking/unmarking the task. This can include
     *             congratulatory or humorous remarks based on the task's status.
     * @return A formatted string containing the message and the updated list of tasks.
     *
     *      <p>Example Output:</p>
     *      <pre>
     *      Task marked as done: 2 .
     *      1. [T] Read book
     *      2. [X] Finish assignment
     *      3. [D] Submit project (by: 20-10-2024 1800)
     *      </pre>
     *      <p>
     *      If the task number is invalid, the output will include an error message:
     *      </p>
     *      <pre>
     *      You do not have task 5!
     *      1. [T] Read book
     *      2. [X] Finish assignment
     *      3. [D] Submit project (by: 20-10-2024 1800)
     *      </pre>
     */
    public static String list(Integer taskNumber, boolean isTaskMarked, String msg) {
        StringBuilder output = new StringBuilder();

        boolean isValidTaskNumber = taskNumber > 0 && taskNumber <= Task.getTaskCount();
        if (isValidTaskNumber) {
            int taskIndex = taskNumber - 1;
            if (isTaskMarked) {
                Tasklist.mark(taskIndex);
            } else {
                Tasklist.unmark(taskIndex);
            }
            output.append(msg).append(taskNumber).append(" .\n");
        } else {
            output.append("You do not have task ").append(taskNumber).append("!\n");
        }

        for (int j = 0; j < Task.getTaskCount(); j++) {
            output.append(j + 1).append(". ").append(Tasklist.TASK_LIST.get(j)).append("\n");
        }

        return output.toString();
    }

    /**
     * This method generates a string representation of the given list of tasks.
     * It formats the list by numbering each task and appending the string
     * representation of each task.
     *
     * @param taskList The list of tasks to be listed. This must be a non-null
     *            ArrayList of Task objects.
     * @return A string that contains all tasks in the list, formatted with their
     *         respective indices.
     */
    public static String list(ArrayList<Task> taskList) {
        StringBuilder output = new StringBuilder();

        int taskIndex = 1;
        for (Task t : taskList) {
            output.append("    ").append(taskIndex).append(". ").append(t.toString()).append("\n");
            taskIndex++;
        }
        return output.toString();
    }


    /**
     * Marks the specified task as done and displays it along with a congratulatory remark.
     *
     * @param taskIndex The 1-based position of the task to mark as done. It must be greater than 0 and less
     *          than or equal to the total number of tasks in the task list.
     *
     *          <p>Console Output:</p>
     *          <ul>
     *              <li>Marks the specified task as done.</li>
     *              <li>Prints a congratulatory message, "Well Done! You finished task {taskIndex}".</li>
     *              <li>Displays the updated list of tasks.</li>
     *          </ul>
     */
    public static String markRemark(Integer taskIndex) {
        return Tasklist.list(taskIndex, true, "    Well Done! You finished task ");
    }

    /**
     * Unmarks the specified task as done and displays it along with a message of disappointment.
     *
     * @param taskIndex The 1-based position of the task to unmark. It must be greater than 0 and less
     *          than or equal to the total number of tasks in the task list.
     *
     *          <p>Console Output:</p>
     *          <ul>
     *              <li>Unmarks the specified task as not done.</li>
     *              <li>Prints a message, "Oh No! You haven't completed task {taskIndex}".</li>
     *              <li>Displays the updated list of tasks.</li>
     *          </ul>
     */
    public static String unmarkRemark(Integer taskIndex) {
        return Tasklist.list(taskIndex, false, "    Oh No! You haven't completed task ");
    }


    /**
     * Marks the specified task as done without displaying any message or list.
     * <p>
     * This method is used when tasks are being loaded from a file.
     *
     * @param taskIndex The 0-based index of the task to mark as done. It must be a valid index within
     *          the task list.
     */
    public static void mark(int taskIndex) {
        Tasklist.TASK_LIST.get(taskIndex).mark();
    }

    /**
     * Unmarks the specified task as done without displaying any message or list.
     * <p>
     * This method is used when tasks are being loaded from a file.
     *
     * @param taskIndex The 0-based index of the task to unmark as not done. It must be a valid index
     *          within the task list.
     */
    public static void unmark(int taskIndex) {
        Tasklist.TASK_LIST.get(taskIndex).unmark();
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
     * @param taskIndex The 0-based index of the task to retrieve. It must be a valid index within the
     *          task list.
     * @return A string representation of the specified task.
     */
    public static String getTaskString(int taskIndex) {
        return Tasklist.TASK_LIST.get(taskIndex).toString();
    }

}
