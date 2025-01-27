package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    public static final String DATE_TIME_REGEX_1 =
            "event\\s+(.*?)\\s+/from\\s+(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})\\s+" +
                    "(00|01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|20|21|22|23)([0-5][0-9])\\s" +
                    "+/to\\s+(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})\\s+(00|01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|20|21|22|23)([0-5][0-9])";
    public static final String DATE_TIME_REGEX_2 = "\\[E\\]\\[(X| )\\] (.*?) \\(from: (\\w+ \\d{1,2} \\d{4} \\d{1,2}:\\d{2} [APMapm]{2}) to: (\\w+ \\d{1,2} \\d{4} \\d{1,2}:\\d{2} [APMapm]{2})\\)";
    private final LocalDateTime fromTime;
    private final LocalDateTime toTime;


    public Event(String description, LocalDateTime fromTime, LocalDateTime toTime) {
        super(description);
        this.fromTime = fromTime;
        this.toTime = toTime;

    }


    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.fromTime.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a")), this.toTime.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a")));
    }
}
