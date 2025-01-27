package application;

import task.Task;
import task.Tasklist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    //private static final String PATH = "src/main/data/TearIT.txt";
    public static void writeToFile() throws IOException {
        FileWriter fw = new FileWriter(Storage.getPath(), false); //overwrite them
        for (int i = 0; i < Task.getTaskCount(); i++) {
            fw.write(Tasklist.getTaskString(i) + System.lineSeparator());
        }
        fw.close();
    }

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

    public static void createFileIfNotExists() throws IOException {
        File file = new File(Storage.getPath());

        // Get the directory from the file path
        File parentDirectory = file.getParentFile();

        // Ensure the directory exists (create it if it doesn't)
        if (parentDirectory != null && !parentDirectory.exists()) {
            boolean directoryCreated = parentDirectory.mkdirs();  // Creates the directory, if needed
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
        return "data/TearIT.text";
    }

}
