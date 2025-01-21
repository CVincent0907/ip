public class Event extends Task{

    private final String fromTime;
    private final String toTime;
    public static final  String regex = "(?i)^event\\s+([^/]+)\\s+/from\\s+([^/]+)\\s+/to\\s+(.+)$";

    public Event(String description, String fromTime, String toTime) {
        super(description);
        this.fromTime = fromTime;
        this.toTime = toTime;

    }

    @Override
    public String toString() {
        String format = String.format("[E]%s (from: %s to: %s)", super.toString(), this.fromTime, this.toTime);
        return format;
    }
}
