package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import task.Deadline;
import task.Event;
import task.Task;
import task.Tasklist;
import task.Todo;


/**
 * Parser class contains methods to parse user's inputs from the command prompt or
 * to parse inputs from text file and convert the inputs into respective task objects
 * when it matches the specified regex.
 */
public class Parser {

    /**
     * @param userInput Input from the command prompt.
     * @param regex     Regex expression to detect the group of possible userInput.
     * @param groups    Group number of possible userInput [1:3] inclusive.
     * @return <code>true</code> if task is successfully created and <code>false</code> otherwise.
     *      Status of task creation depends on the userInput and regex.
     */
    public static boolean extractAndCreateTask(String userInput, String regex, int groups) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.find()) {
            if (groups == 1) {
                Tasklist.add(new Todo(matcher.group(1)));
            } else if (groups == 2) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                String deadline = matcher.group(2) + "-" + matcher.group(3)
                        + "-" + matcher.group(4) + " " + matcher.group(5)
                        + matcher.group(6);
                Tasklist.add(new Deadline(matcher.group(1), LocalDateTime.parse(deadline, formatter)));
            } else {
                String fromDate = matcher.group(2) + "-" + matcher.group(3)
                        + "-" + matcher.group(4) + " " + matcher.group(5)
                        + matcher.group(6); // "18-09-2024 1600"
                String toDate = matcher.group(7) + "-" + matcher.group(8)
                        + "-" + matcher.group(9) + " " + matcher.group(10)
                        + matcher.group(11); // "18-09-2024 1900"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                Tasklist.add(new Event(matcher.group(1), LocalDateTime.parse(fromDate, formatter),
                        LocalDateTime.parse(toDate, formatter)));
            }
            Task.addTaskCount();
            return true;

        } else {
            return false;
        }

    }

    /**
     * @param task Task information retrieved from text file.
     * @return <code>true</code> if task is successfully extracted from file and <code>false</code> otherwise.
     *      Status of extraction will be <code>false</code> whenever none of the 3 specified regex expressions
     *      match the task string format.
     */
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a",
                        Locale.ENGLISH);
                Tasklist.add(new Deadline(deadlineMatcher.group(2),
                        LocalDateTime.parse(deadlineMatcher.group(3), formatter)));
                if (isDone) {
                    Tasklist.mark(Task.getTaskCount());
                }
            } else if (eventMatcher.matches()) {
                boolean isDone = eventMatcher.group(1).equals("X");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a",
                        Locale.ENGLISH);
                Tasklist.add(new Event(eventMatcher.group(2),
                        LocalDateTime.parse(eventMatcher.group(3), formatter),
                        LocalDateTime.parse(eventMatcher.group(4), formatter)));
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
