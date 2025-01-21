public class Deadline extends Task {
    private final String deadline;
    public static final String regex = "(?i)^deadline\\s+([^/]+)\\s+/by\\s+(.+)$";
    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.deadline);
    }
}
