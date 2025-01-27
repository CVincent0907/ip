package application;

import task.Task;
import task.Tasklist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private static final String PATH = "src/main/data/TearIT.txt";
    public static void writeToFile(String filePath) throws IOException {
        FileWriter fw = new FileWriter(filePath, false); //overwrite them
        for (int i = 0; i < Task.getTaskCount(); i++) {
            fw.write(Tasklist.getTaskString(i) + System.lineSeparator());
        }
        fw.close();
    }

    public static void readFromFile(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            Parser.extractTaskFromFile(s.nextLine());
        }
    }

    public static String getPath() {
        return Storage.PATH;
    }

}
