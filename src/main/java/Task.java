public abstract class Task {
    private static int taskCount = 0;
    private final String description;

    private boolean isDone = false;

    public static int getTaskCount() {
        return Task.taskCount;
    }

    public static void addTaskCount() {
        Task.taskCount++;
    }

    public static void reduceTaskCount() { Task.taskCo
    unt--; }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public Task(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        if (this.isDone) {
            return "[X] " + this.description;
        } else {
            return "[ ] " + this.description;
        }
    }

}
