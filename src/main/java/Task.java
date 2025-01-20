public class Task {
    private static int taskCount = 0;
    private final String description;

    public static int getTaskCount() {
        return Task.taskCount;
    }

    public static void addTaskCount() {
        Task.taskCount++;
    }

    public Task(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
