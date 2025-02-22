package task;


/**
 * Todo class is one of the task type class extended from abstract class <code>Task.java</code>.
 */
public class Todo extends Task {
    public static final String REGEX_1 = "(?i)^todo\\s+(.+)$";
    public static final String REGEX_2 = "\\[T\\]\\[(X| )\\] (.*)";

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return String.format("[T]%s ", super.toString());
    }
}
