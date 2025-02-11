package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import task.Task;
import task.Tasklist;

/**
 * Ui class contains function to simulate the interactions between system and user in the application.
 */
public class Ui {
    private static int readFromFileCount = 0;

    /**
     * Processes a user input command related to task management and returns the corresponding output as a string.
     *
     * <p>The method supports the following commands:
     * <ul>
     *     <li>"list" - Lists all tasks</li>
     *     <li>"mark" - Marks a task as done by its index</li>
     *     <li>"unmark" - Unmarks a task as undone by its index</li>
     *     <li>"delete" - Deletes a task by its index</li>
     *     <li>"find" - Finds tasks containing a specific keyword</li>
     *     <li>"bye" - Exits the application and saves data</li>
     * </ul>
     *
     * @param args The first argument represents the final message displayed when exiting the application.
     *             The second argument represents the user's input command to be processed.
     * @return A string containing the system's response, including success messages, error messages, or task listings.
     */
    public static String echo(String... args) {
        if (args.length < 2) {
            return "Error: Missing required arguments. Usage: echo(ending, input)";
        }

        String ending = args[0];
        String input = args[1];
        StringBuilder output = new StringBuilder();

        Ui.readFromFileCount++;
        if (Ui.readFromFileCount == 1) {
            try {
                Storage.readFromFile();
            } catch (FileNotFoundException e) {
                try {
                    Storage.createFileIfNotExists();
                } catch (IOException e1) {
                    output.append("Error creating the file: ").append(e1.getMessage()).append("\n");
                    return output.toString();
                }
            }
        }

        if (input.trim().isEmpty()) {
            output.append("System does not support such command. Only todo ..., ")
                    .append("deadline ..., event..., mark..., unmark..., delete..., find ... list")
                    .append(" and bye only !\n");
        } else {
            String[] parts = input.split(" ");
            String part = parts[0];
            int len = parts.length;

            switch (part.toLowerCase()) {
            case "list":
                output.append(Tasklist.list()).append("\n");
                break;

            case "mark":
                if (len == 1) {
                    output.append("There must be an integer after mark !\n");
                } else {
                    try {
                        output.append(Tasklist.markRemark(Integer.parseInt(parts[1]))).append("\n");
                    } catch (NumberFormatException e) {
                        output.append(e.getMessage()).append("\n").append("The argument should be an integer!\n");
                    }
                }
                break;

            case "unmark":
                if (len == 1) {
                    output.append("There must be an integer after unmark !\n");
                } else {
                    try {
                        output.append(Tasklist.unmarkRemark(Integer.parseInt(parts[1]))).append("\n");
                    } catch (NumberFormatException e) {
                        output.append(e.getMessage()).append("\n").append("The argument should be an integer!\n");
                    }
                }
                break;

            case "bye":
                try {
                    Storage.writeToFile();
                } catch (IOException e) {
                    output.append("Something went wrong: ").append(e.getMessage()).append("\n")
                                    .append("The data is not saved !\n");
                }
                output.append(ending).append("\n");
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> Platform.exit());
                pause.play();
                return output.toString();

            case "delete":
                if (len == 1) {
                    output.append("There must be an integer after delete !\n");
                } else {
                    try {
                        output.append(Tasklist.delete(Integer.parseInt(parts[1]))).append("\n");
                    } catch (NumberFormatException e) {
                        output.append(e.getMessage()).append("\n").append("The argument should be an integer!\n");
                    }
                }
                break;

            case "find":
                if (len == 1) {
                    output.append("There must be an argument after find !\n");
                } else {
                    ArrayList<Task> t = Tasklist.find(parts[1]);
                    if (!t.isEmpty()) {
                        output.append("Here is/are the matching task(s) in your list:\n").append(Tasklist.list(t))
                                .append("\n");
                    } else {
                        output.append("There is not any matching tasks in your list.\n");
                    }
                }
                break;

            default:
                output.append(Tasklist.add(input)).append("\n");
                break;
            }
        }

        return output.toString();
    }

}


