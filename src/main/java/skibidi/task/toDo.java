package skibidi.task;

/**
 * Represents a simple task with no additional fields or properties.
 * Extends the {@link Task} class and sets the task type to "T".
 */
public class toDo extends Task{

    /**
     * Default constructor. Initializes a new {@code toDo} object with a task type of "T".
     */
    public toDo() {
        super.taskType = "T";
    }

    /**
     * Constructor that creates a {@code toDo} task with the specified task name.
     *
     * @param taskName The name or description of the task.
     */
    public toDo(String taskName) {
        super(taskName);
        super.taskType = "T";
    }
}
