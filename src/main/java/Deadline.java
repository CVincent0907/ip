import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDateTime deadline;
    public static final String DATE_TIME_REGEX_1 = "deadline (.*?) /by (\\d{1,2}-\\d{1,2}-\\d{4} \\d{4})"; // regex to recognize full date time 12-08-2027 1800 2027 and 1800 must be 4 digits
    public static final String DATE_TIME_REGEX_2= "\\[D\\]\\[(X| )\\] (.*?) \\(by: (\\w+ \\d{1,2} \\d{4} \\d{1,2} [APMapm]{2})\\)";
    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.deadline.format(DateTimeFormatter.ofPattern("MMM d yyyy hh a")));
    }
}
