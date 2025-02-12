package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import task.Task;
import task.Tasklist;

/**
 * Command class contains a series of handle to manage user input.
 */
public class Command {

    /**
     * Handles the "find" command by checking if an argument is provided, then searching
     *      for tasks that match the provided argument. If matching tasks are found, it appends
     *              a message listing the matching tasks; otherwise, it notifies the user that no matches
     *                      were found.
     *
     * <p>If the user does not provide an argument after the "find" command, an error message
     *      is appended to the system response.</p>
     *
     * @param userInputLen The length of the user's input (number of fragments).
     * @param systemResponse A StringBuilder where system messages are appended, including
     *                       success or error messages related to the "find" command.
     * @param userInputFragments An array of strings that represents the fragments of the
     *                           user's input, where the second fragment contains the search query.
     */
    public static void handleFindCommand(int userInputLen, StringBuilder systemResponse, String[] userInputFragments) {
        if (userInputLen == 1) {
            systemResponse.append("There must be an argument after find !\n");
        } else {
            ArrayList<Task> t = Tasklist.find(userInputFragments[1]);
            if (!t.isEmpty()) {
                systemResponse.append("Here is/are the matching task(s) in your list:\n").append(Tasklist.list(t))
                        .append("\n");
            } else {
                systemResponse.append("There is not any matching tasks in your list.\n");
            }
        }
    }

    /**
     * Handles the "delete" command by checking if an integer argument (task number)
     *      is provided. If the argument is not an integer, an error message is appended to
     *              the system response. If an integer is provided, it attempts to delete the task
     *                      at the specified index and appends the result.
     *
     * <p>If the user does not provide an argument after the "delete" command, an error
     *      message is appended to the system response. If the argument cannot be parsed as an
     *              integer, an error message specifying that the argument should be an integer is appended.</p>
     *
     * @param userInputLen The length of the user's input (number of fragments).
     * @param systemResponse A StringBuilder where system messages are appended, including
     *                       success or error messages related to the "delete" command.
     * @param userInputFragments An array of strings that represents the fragments of the
     *                           user's input, where the second fragment contains the task number to delete.
     */
    public static void handleDeleteCommand(int userInputLen, StringBuilder systemResponse, String[] userInputFragments) {
        if (userInputLen == 1) {
            systemResponse.append("There must be an integer after delete !\n");
        } else {
            try {
                systemResponse.append(Tasklist.delete(Integer.parseInt(userInputFragments[1]))).append("\n");
            } catch (NumberFormatException e) {
                systemResponse.append(e.getMessage()).append("\n").append("The argument should be an integer!\n");
            }
        }
    }


    /**
     * Handles the "bye" command, which involves saving the current data to a file and
     *      displaying a farewell message. If there is an issue while saving the data, an
     *              error message is appended to the system response. The method then pauses for a
     *                      brief moment before closing the application.
     *
     * <p>This method attempts to write data to the storage file, and if successful,
     *      appends a farewell message to the system response. If an error occurs during
     *              the writing process, an error message is included in the system response. After
     *                      the operation, the application waits briefly before exiting.</p>
     *
     * @param systemResponse A StringBuilder used to append system messages, including
     *                       error messages and the farewell message.
     * @param systemByeMsg A string containing the farewell message to be displayed
     *                     before exiting the program.
     * @return A string representing the final system response after handling the "bye" command.
     */
    public static String handleByeRelatedCommand(StringBuilder systemResponse, String systemByeMsg) {
        try {
            Storage.writeToFile();
        } catch (IOException e) {
            systemResponse.append("Something went wrong: ").append(e.getMessage()).append("\n")
                    .append("The data is not saved !\n");
        }
        systemResponse.append(systemByeMsg).append("\n");
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> Platform.exit());
        pause.play();
        return systemResponse.toString();
    }

    /**
     * Handles the "unmark" command, which is used to unmark a task as completed.
     * If the user input is invalid (missing or incorrect argument), an appropriate
     *      error message is appended to the system response. The method attempts to parse
     *              the task number from the user input and calls the <code>Tasklist.unmarkRemark</code> method.
     * If the argument is not an integer, a {@link NumberFormatException} is caught and
     *       an error message is added to the response.
     *
     * <p>This method ensures that the user provides a valid task number (integer) after
     *      the "unmark" command. If the number is missing or invalid, it notifies the user
     *              with the appropriate error message. If successful, it unmarks the specified task.</p>
     *
     * @param userInputLen The length of the user input array, used to check for missing
     *                     arguments.
     * @param systemResponse A `StringBuilder` that accumulates and returns the system
     *                       responses, including error messages and confirmation messages.
     * @param userInputFragments The user input broken into fragments (commands and arguments),
     *                           used to identify the specific task number to unmark.
     */
    public static void handleUnmarkCommand(int userInputLen, StringBuilder systemResponse, String[] userInputFragments) {
        if (userInputLen == 1) {
            systemResponse.append("There must be an integer after unmark !\n");
        } else {
            try {
                systemResponse.append(Tasklist.unmarkRemark(Integer.parseInt(userInputFragments[1]))).append("\n");
            } catch (NumberFormatException e) {
                systemResponse.append(e.getMessage()).append("\n").append("The argument should be an integer!\n");
            }
        }
    }

    /**
     * Handles the "mark" command, which is used to mark a task as completed.
     * If the user input is invalid (missing or incorrect argument), an appropriate
     *      error message is appended to the system response. The method attempts to parse
     *              the task number from the user input and calls the <code>Tasklist.markRemark</code> method.
     * If the argument is not an integer, a {@link NumberFormatException} is caught and
     *      an error message is added to the response.
     *
     * <p>This method ensures that the user provides a valid task number (integer) after
     *      the "mark" command. If the number is missing or invalid, it notifies the user
     *              with the appropriate error message. If successful, it marks the specified task as completed.</p>
     *
     * @param userInputLen The length of the user input array, used to check for missing
     *                     arguments.
     * @param systemResponse A `StringBuilder` that accumulates and returns the system
     *                       responses, including error messages and confirmation messages.
     * @param userInputFragments The user input broken into fragments (commands and arguments),
     *                           used to identify the specific task number to mark.
     */
    public static void handleMarkCommand(int userInputLen, StringBuilder systemResponse, String[] userInputFragments) {
        if (userInputLen == 1) {
            systemResponse.append("There must be an integer after mark !\n");
        } else {
            try {
                systemResponse.append(Tasklist.markRemark(Integer.parseInt(userInputFragments[1]))).append("\n");
            } catch (NumberFormatException e) {
                systemResponse.append(e.getMessage()).append("\n").append("The argument should be an integer!\n");
            }
        }
    }
}
