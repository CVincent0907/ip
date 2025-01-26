import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private final LocalDateTime fromTime;
    private final LocalDateTime toTime;
    public static final String DATE_TIME_REGEX_1 =
            "event\\s+(.*?)\\s+/from\\s+(\\d{1,2}-\\d{1,2}-\\d{4}\\s+\\d{4})\\s+/to\\s+(\\d{1,2}-\\d{1,2}-\\d{4})";
    public static final String DATE_TIME_REGEX_2 = "\\[E\\]\\[(X| )\\] (.*?) \\(from: (\\w+ \\d{1,2} \\d{4} \\d{1,2} " +
            "[APMapm]{2}) to: (\\w+ \\d{1,2} \\d{4} \\d{1,2} [APMapm]{2})\\)";


    public Event(String description, LocalDateTime fromTime, LocalDateTime toTime) {
        super(description);
        this.fromTime = fromTime;
        this.toTime = toTime;

    }


    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.fromTime.format(DateTimeFormatter.ofPattern("MMM d yyyy hh a")), this.toTime.format(DateTimeFormatter.ofPattern("MMM d yyyy hh a")));
    }
}
