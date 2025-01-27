package application;

import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;
import task.Tasklist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    // This function extract the info from user input via regex and create the corresponding task object
    // This function also adds taskCount for all objects created here [ie: only task.Deadline, task.Event, task.Todo]
    // input1:regex input2:userinput input3: number of needed group
    public static boolean extractAndCreateTask(String userInput, String regex, int groups) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.find()) {
            if (groups == 1) {
                Tasklist.add(new Todo(matcher.group(1)));
            } else if (groups == 2) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                String deadline = matcher.group(2) + "-" + matcher.group(3) + "-" + matcher.group(4) + " " + matcher.group(5) + matcher.group(6);
                Tasklist.add(new Deadline(matcher.group(1), LocalDateTime.parse(deadline, formatter)));
            } else {
                String fromDate = matcher.group(2) + "-" + matcher.group(3) + "-" + matcher.group(4) + " " + matcher.group(5) + matcher.group(6);  // "18-09-2024 1600"
                String toDate = matcher.group(7) + "-" + matcher.group(8) + "-" + matcher.group(9) + " " + matcher.group(10) + matcher.group(11);  // "18-09-2024 1900"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                Tasklist.add(new Event(matcher.group(1), LocalDateTime.parse(fromDate, formatter), LocalDateTime.parse(toDate, formatter)));
            }
            Task.addTaskCount();
            return true;

        } else {
            System.out.println("    Input format is incorrect.");
            System.out.println("    todo input format :todo XX");
            System.out.println("    deadline input format :deadline XX /by dd-mm-yyyy hhmm");
            System.out.println("    deadline input format :deadline XX /from dd-mm-yyyy hhmm /to dd-mm-yyyy hhmm");
            return false;

        }

    }

    // This function is used to extract test file string line by line into a task object
    // input1: task string from text file
    public static boolean extractTaskFromFile(String task) {

            // Regex for each task type
            Pattern todoPattern = Pattern.compile(Todo.REGEX_2);
            Pattern deadlinePattern = Pattern.compile(Deadline.DATE_TIME_REGEX_2);
            Pattern eventPattern = Pattern.compile(Event.DATE_TIME_REGEX_2);


            Matcher todoMatcher = todoPattern.matcher(task);
            Matcher deadlineMatcher = deadlinePattern.matcher(task);
            Matcher eventMatcher = eventPattern.matcher(task);

            if (todoMatcher.matches() || deadlineMatcher.matches() || eventMatcher.matches()) {
                if (todoMatcher.matches()) {
                    boolean isDone = todoMatcher.group(1).equals("X");
                    Tasklist.add(new Todo(todoMatcher.group(2)));
                    if (isDone) {
                        Tasklist.mark(Task.getTaskCount());
                    }
                } else if (deadlineMatcher.matches()) {
                    boolean isDone = deadlineMatcher.group(1).equals("X");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a", Locale.ENGLISH);
                    Tasklist.add(new Deadline(deadlineMatcher.group(2), LocalDateTime.parse(deadlineMatcher.group(3), formatter)));
                    if (isDone) {
                        Tasklist.mark(Task.getTaskCount());
                    }
                } else if (eventMatcher.matches()) {
                    boolean isDone = eventMatcher.group(1).equals("X");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a", Locale.ENGLISH);
                    Tasklist.add(new Event(eventMatcher.group(2), LocalDateTime.parse(eventMatcher.group(3), formatter), LocalDateTime.parse(eventMatcher.group(4), formatter)));
                    if (isDone) {
                        Tasklist.mark(Task.getTaskCount());
                    }
                }
                Task.addTaskCount();
                return true;
            } else {
                return false;
            }
    }

}
