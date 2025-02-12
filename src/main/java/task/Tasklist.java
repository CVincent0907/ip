package task;

import java.util.ArrayList;

import application.Parser;

/**
 * Tasklist class contains methods to deal with the tasklist such as add, delete, list and etc.
 */
public class Tasklist {
    private static final ArrayList<Task> TASK_LIST = new ArrayList<>();

    /**
     * Adds a new task based on the provided user input.
     * <p>This method splits the input string into parts, determines the number of fragments,
     *      and passes the input and its length to the {@link Tasklist#getAddTaskMessage(String, int)}
     *              method to generate a corresponding message for adding the task.</p>
     *
     * <p>The method ensures that the input is properly processed and that the correct message is
     *      returned based on the task creation status.</p>
     *
     * @param input The full user input string containing the task description and possible
     *              details (e.g., due date, time).
     * @return A message string indicating the result of the task addition,
     *         including any relevant details or error messages.
     */
    public static String add(String input) {
        String[] parts = input.split(" ");
        int len = parts.length;

        return Tasklist.getAddTaskMessage(input, len);
    }

    /**
     * Generates a message for adding a new task based on the user input.
     * <p>This method processes the input to determine the type of task (todo, deadline, or event)
     *      and calls the appropriate parsing method. It then returns a message indicating
     *              the success or failure of task creation.</p>
     *
     * @param input The user input string containing the task details.
     * @param len The number of parts in the input, used to check if additional arguments are provided.
     * @return A message string indicating the result of adding the task or an error reminder.
     */
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

    /**
     * Generates a message indicating the result of a task creation attempt.
     * <p>If the task creation is successful, it returns a message confirming the addition of the task
     *      and displaying the updated task list count. If the task creation fails, it returns an error message
     *              with the correct input formats for todo, deadline, and event tasks.</p>
     *
     * @param taskCreationStatusFlag A flag indicating the success or failure of task creation.
     *        If false, an error message with input format guidelines is returned;
     *        if true, a success message is returned.
     * @return A message indicating the result of the task creation process.
     */
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
     *      to the task list without any additional processing or validation.
     *
     * @param task The {@link Task} object to be added to the task list. It should be a valid task instance such as
     *             {@link Todo}, {@link Deadline}, or {@link Event}.
     */
    public static void add(Task task) {
        Tasklist.TASK_LIST.add(task);
    }

    /**
     * Deletes a task from the task list based on the given task number.
     * <p>If the task number is valid, the task is removed from the list,
     *      and a confirmation message is returned along with the updated task count.
     *              If the task number is invalid, an error message is returned.</p>
     *
     * @param taskNumber The index of the task to be deleted (1-based).
     * @return A message confirming the deletion or indicating an invalid task number,
     *         along with the updated task count.
     */
    public static String delete(Integer taskNumber) {

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
     *      in the task list and returns it as a single formatted string.
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
     *      followed by the updated task list. If the specified task does not exist, it returns an
     *          error message and still includes the current task list.
     * </p>
     *
     * @param taskNumber The 1-based position of the task to isTaskMarked or unmark. It must be greater than 0
     *                   and less than or equal to the total number of tasks in the task list.
     * @param isTaskMarked {@code true} to isTaskMarked the task as completed, or {@code false} to unmark it.
     * @param msg  A custom message to display after marking/unmarking the task. This can include
     *             congratulatory or humorous remarks based on the task's status.
     * @return A formatted string containing the message and the updated list of tasks.
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
     *                 ArrayList of Task objects.
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
     *                  than or equal to the total number of tasks in the task list.
     */
    public static String markRemark(Integer taskIndex) {
        return Tasklist.list(taskIndex, true, "    Well Done! You finished task ");
    }

    /**
     * Unmarks the specified task as done and displays it along with a message of disappointment.
     *
     * @param taskIndex The 1-based position of the task to unmark. It must be greater than 0 and less
     *                  than or equal to the total number of tasks in the task list.
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
     *                  the task list.
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
     *                  within the task list.
     */
    public static void unmark(int taskIndex) {
        Tasklist.TASK_LIST.get(taskIndex).unmark();
    }

    /**
     * Searches for tasks that contain the given keyword in their descriptions.
     * <p>
     * This function iterates over all tasks in the task list and adds the tasks
     *      whose description contains the provided keyword (case-insensitive) to a new list.
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
     *                  task list.
     * @return A string representation of the specified task.
     */
    public static String getTaskString(int taskIndex) {
        return Tasklist.TASK_LIST.get(taskIndex).toString();
    }

}
