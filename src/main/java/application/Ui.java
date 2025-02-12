package application;

import java.io.FileNotFoundException;
import java.io.IOException;

import task.Tasklist;

/**
 * Ui class contains function to simulate the interactions between system and user in the application.
 */
public class Ui {
    private static int readFromFileCount = 0;

    /**
     * Processes and handles user input for echo command. It validates the input, handles error cases,
     *      and generates an appropriate system response. This method interacts with the storage system
     *              to fetch necessary data and constructs the response based on user input.
     *
     * <p>If the input is invalid, a specific error message is returned. If the input is valid,
     * the system will return the appropriate response based on the processed input.</p>
     *
     * @param args A varargs parameter that allows multiple arguments to be passed. The second argument
     *             (args[1]) represents the actual user input.
     *
     * @return A string containing the system response, which could either be an error message or
     *         a valid system response based on the input. If the user input is empty or invalid,
     *         an error message is returned.
     */
    public static String echo(String... args) {

        StringBuilder systemResponse = new StringBuilder();

        String readFromStorageErrorMsg = readFromStorage(systemResponse);
        if (readFromStorageErrorMsg != null) {
            return readFromStorageErrorMsg;
        }

        String userInput = args[1];
        if (userInput.trim().isEmpty()) {
            systemResponse.append("System does not support such command. Only todo ..., ")
                    .append("deadline ..., event..., mark..., unmark..., delete..., find ... list")
                    .append("... lookup and bye only !\n");
            return systemResponse.toString();
        }

        String[] userInputFragments = userInput.split(" ");
        int userInputLen = userInputFragments.length;
        String errorMsg = handleUserInput(args, userInputFragments, systemResponse, userInputLen, userInput);
        if (errorMsg != null) {
            return errorMsg;
        }
        return systemResponse.toString();
    }


    /**
     * Handles the processing of user input and generates the appropriate system response
     *      based on the command specified in the user input. This method supports various commands
     *              such as "list", "mark", "unmark", "bye", "delete", "find", "lookup" and delegates the
     *                      handling of specific commands to corresponding helper methods.
     * <p>
     * If the command is not recognized, it defaults to adding a task to the task list.
     * </p>
     *
     * @param args An array of arguments passed to the method. The first element (args[0])
     *             is used for handling the "bye" command.
     * @param userInputFragments An array of fragments obtained by splitting the user input.
     *                           The first element of this array is used to identify the command.
     * @param systemResponse A StringBuilder object where the system response will be appended.
     *                       It is updated throughout the method depending on the command processed.
     * @param userInputLen The length of the user input, used for validating commands that require arguments.
     * @param userInput The complete user input as a single string, used for processing the command.
     *
     * @return A string containing the error message (if any) for unsupported commands,
     *         or null if the command is processed successfully.
     */
    private static String handleUserInput(String[] args, String[] userInputFragments,
                                          StringBuilder systemResponse, int userInputLen, String userInput) {
        switch (userInputFragments[0].toLowerCase()) {
        case "list":
            Command.handleListCommand(userInputLen,systemResponse);
            break;

        case "mark":
            Command.handleMarkCommand(userInputLen, systemResponse, userInputFragments);
            break;

        case "unmark":
            Command.handleUnmarkCommand(userInputLen, systemResponse, userInputFragments);
            break;

        case "bye":
            String systemByeMsg = args[0];
            return Command.handleByeCommand(userInputLen, systemResponse, systemByeMsg);

        case "delete":
            Command.handleDeleteCommand(userInputLen, systemResponse, userInputFragments);
            break;

        case "find":
            Command.handleFindCommand(userInputLen, systemResponse, userInputFragments);
            break;

        case "lookup":
            Command.handleLookUpCommand(userInputLen, systemResponse, userInputFragments);
            break;

        default:
            systemResponse.append(Tasklist.add(userInput)).append("\n");
            break;

        }
        return null;
    }


    /**
     * Attempts to read data from storage and handle any errors that might occur during
     *      the reading process. If the data cannot be read because the file does not exist,
     *              it attempts to create the file and then proceed with reading. This method is only
     *                      executed the first time the program runs, as it checks the count of file reads.
     *
     * <p>If an error occurs during file creation, the error message is appended to the
     *      system response. If the reading or file creation process is successful, the method
     *              proceeds without returning any errors.</p>
     *
     * @param systemResponse A StringBuilder where error or success messages are appended.
     *                       It will contain an error message if file creation fails,
     *                       or null if the operation succeeds.
     *
     * @return A string containing an error message if any issues occur during file reading
     *         or creation, or null if there are no errors.
     */
    private static String readFromStorage(StringBuilder systemResponse) {
        Ui.readFromFileCount++;
        if (Ui.readFromFileCount == 1) {
            try {
                Storage.readFromFile();
            } catch (FileNotFoundException e) {
                try {
                    Storage.createFileIfNotExists();
                } catch (IOException e1) {
                    systemResponse.append("Error creating the file: ").append(e1.getMessage()).append("\n");
                    return systemResponse.toString();
                }
            }
        }
        return null;
    }

}


