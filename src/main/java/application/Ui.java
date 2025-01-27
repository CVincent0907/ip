package application;

import task.Tasklist;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Ui {

    //  This function receives TearIt.ending as String input
    //  This function prints out echo and ending message when user enter "bye"
    //  input1: the ending messaged passed from tearIT
    public static void echo(String ending) {
        try {
            Storage.readFromFile();
        } catch (FileNotFoundException e) {

            try {
                Storage.createFileIfNotExists();
            } catch (IOException e1) {
                System.out.println("Error creating the file: " + e1.getMessage());
                System.exit(1);  // Exit if unable to create the file
            } finally {

            }

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
                    Tasklist.list();
                    break;
                case "mark":

                    if (len == 1) {
                        System.out.println("    There must be an integer after mark !");
                    } else {
                        try {
                            Tasklist.markRemark(Integer.parseInt(parts[1]));
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
                            Tasklist.unmarkRemark(Integer.parseInt(parts[1]));
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
                        Storage.writeToFile();
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
                            Tasklist.delete(Integer.parseInt(parts[1]));
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
                    int errorCode = Tasklist.add(input);
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

}
