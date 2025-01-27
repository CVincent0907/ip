package task;

/**
 * Task class is an abstract class where task types class like <code>Todo.java</code>,
 * <code>Deadline.java</code>, <code>Event.java</code> extended from.
 */
public abstract class Task {
    private static int taskCount = 0;
    private final String description;

    private boolean isDone = false;

    /**
     * @return the total number of tasks that are successfully created.
     */
    public static int getTaskCount() {
        return Task.taskCount;
    }

    /**
     * Increase the total number of tasks upon successful creation by one.
     */
    public static void addTaskCount() {
        Task.taskCount++;
    }

    /**
     * Reduce the total number of tasks upon task deletion by one.
     */
    public static void reduceTaskCount() {
        Task.taskCount--;
    }

    /**
     * Set the task isDone status to <code>true</code>.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Set the task isDone status to <code>false</code>.
     */
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