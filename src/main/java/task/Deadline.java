package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deadline class is one of the task type class extended from abstract class <code>Task.java</code>.
 */
public class Deadline extends Task {
    public static final String DATE_TIME_REGEX_1 =
            "deadline\\s+(.*?)\\s+/by\\s+(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})\\s"
                    + "+(00|01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|20|21|22|23)([0-5][0-9])";
    // regex to recognize full date time 12-08-2027 1800 2027 and 1800 must be 4 digits
    public static final String DATE_TIME_REGEX_2 = "\\[D\\]\\[(X| )\\] (.*?) \\"
            + "(by: (\\w+ \\d{1,2} \\d{4} \\d{1,2}:\\d{2} [APMapm]{2})\\)";
    private final LocalDateTime deadline;

    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

    /**
     * Checks if the task's deadline matches the provided date in the "dd-MM-yyyy" format.
     *
     * @param date The date to compare against the task's deadline.
     * @return {@code true} if the task's deadline matches the provided date; {@code false} otherwise.
     */
    public boolean isDeadlineMatch(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return this.deadline.format(dateFormatter).equals(date);
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(),
                this.deadline.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a")));
    }
}
