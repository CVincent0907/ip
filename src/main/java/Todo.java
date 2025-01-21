public class Todo extends Task {
    public static final String regex = "(?i)^todo\\s+(.+)$";
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
       return String.format("[T]%s ",super.toString());
    }
}
