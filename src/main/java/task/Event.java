package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Event class is one of the task type class extended from abstract class <code>Task.java</code>.
 */
public class Event extends Task {

    public static final String DATE_TIME_REGEX_1 =
            "event\\s+(.*?)\\s+/from\\s+(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})\\s+"
                    + "(00|01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|20|21|22|23)([0-5][0-9])\\s"
                    + "+/to\\s+(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})\\s"
                    + "+(00|01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|20|21|22|23)([0-5][0-9])";
    public static final String DATE_TIME_REGEX_2 = "\\[E\\]\\[(X| )\\] (.*?) \\"
            + "(from: (\\w+ \\d{1,2} \\d{4} \\d{1,2}:\\d{2} [APMapm]{2}) "
            + "to: (\\w+ \\d{1,2} \\d{4} \\d{1,2}:\\d{2} [APMapm]{2})\\)";
    private final LocalDateTime fromDate;
    private final LocalDateTime toDate;

    public Event(String description, LocalDateTime fromDate, LocalDateTime toDate) {
        super(description);
        this.fromDate = fromDate;
        this.toDate = toDate;

    }

    /**
     * Checks if either the task's from date or to date matches the provided date in the "dd-MM-yyyy" format.
     *
     * @param date The date to compare against the task's from and to dates.
     * @return {@code true} if either the from date or to date matches the provided date; {@code false} otherwise.
     */
    public boolean isFromDateOrByDateMatch(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return this.fromDate.format(dateFormatter).equals(date)
                || this.toDate.format(dateFormatter).equals(date);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(),
                this.fromDate.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a")),
                this.toDate.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a")));
    }
}
