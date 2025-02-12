package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import task.Task;
import task.Tasklist;

/**
 * Storage class contains methods to read from storage (e.g. files), write
 * to storage and also create storage when it does not exist.
 */
public class Storage {

    /**
     * Writes each task's info to text file one step before exiting the system upon "bye" command.
     *
     * @throws IOException Throws IOException whenever files written to is corrupted.
     */
    public static void writeToFile() throws IOException {
        FileWriter fw = new FileWriter(Storage.getPath(), false); //overwrite them
        for (int i = 0; i < Task.getTaskCount(); i++) {
            fw.write(Tasklist.getTaskString(i) + System.lineSeparator());
        }
        fw.close();
    }

    /**
     * Read the text from text file and convert each line of text into its corresponding task object.
     *
     * @throws FileNotFoundException Throws FileNotFoundException when files read from does not exist.
     */
    public static void readFromFile() throws FileNotFoundException {
        File f = new File(Storage.getPath());
        if (!f.exists()) {
            throw new FileNotFoundException();
        }

        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            boolean flag = Parser.extractTaskFromFile(s.nextLine());
        }
    }

    /**
     * Create a file named TearIT.txt under a created directory named data when TearIT.txt
     *      does not exist under current working directory.
     *
     * @throws IOException Throws IOException if file or directory could not be created when
     *                     TearIT.txt does not exist.
     */
    public static void createFileIfNotExists() throws IOException {
        File file = new File(Storage.getPath());

        // Get the directory from the file path
        File parentDirectory = file.getParentFile();

        // Ensure the directory exists (create it if it doesn't)
        if (parentDirectory != null && !parentDirectory.exists()) {
            boolean directoryCreated = parentDirectory.mkdirs(); // Creates the directory, if needed
            if (!directoryCreated) {
                throw new IOException("Failed to create the directory at " + parentDirectory.getPath());
            }
        }

        // Check if the file exists and create it if not
        if (!file.exists()) {
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) {
                throw new IOException("Failed to create the file at " + Storage.getPath());
            }
        }
    }

    private static String getPath() {
        return "data/TearIT.txt";
    }

}
