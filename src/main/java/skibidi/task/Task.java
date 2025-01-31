package skibidi.task;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,      // Use the 'type' property to identify the subtype
        include = JsonTypeInfo.As.PROPERTY,
        property = "taskType"              // The JSON property that indicates the subtype
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ToDo.class, name = "T"),
        @JsonSubTypes.Type(value = Event.class, name = "E"),
        @JsonSubTypes.Type(value = Deadline.class, name = "D")
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

/**
 * An abstract base class for representing a general task.
 * <p>
 * A {@code Task} includes a name, a completion status, and a task type designation.
 * Subclasses of {@code Task} define specific types of tasks with additional fields
 * and functionality.
 * </p>
 */
public abstract class Task {
    private String taskName;
    private boolean isDone;
    String taskType;

    /**
     * Default constructor.
     * For jackson library
     */
    public Task() {
    }

    /**
     * Constructor to initialize a task with a specified name.
     * By default, the task is marked as not done.
     *
     * @param taskName The name or description of the task.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    /**
     * Retrieves the task name or description.
     *
     * @return The name of the task.
     */
    protected String getTask() {
        return this.taskName;
    }

    /**
     * Retrieves the completion status of the task as a string.
     *
     * @return {@code "[X]"} if the task is done, {@code "[ ]"} otherwise.
     */
    @JsonIgnore
    protected String getIsDone() {
        if (this.isDone) {
            return "[X]";
        }
        return "[ ]";
    }

    /**
     * Marks the task as completed.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Retrieves the type of the task, formatted with square brackets.
     *
     * @return The task type in the format {@code "[TaskType]"}.
     */
    protected String getTaskType() {
        return "[" + this.taskType + "]";
    }

    /**
     * Returns a string representation of the task.
     * The representation includes the task type, completion status, and the task name.
     *
     * @return A string in the format: {@code [TaskType][CompletionStatus] taskName}.
     */
    @Override
    public String toString() {
        return getTaskType() + getIsDone() + " " + getTask();
    }

    /**
     * Gets the completion status of the task.
     *
     * @return {@code true} if the task is done, {@code false} otherwise.
     */
    @JsonIgnore
    public boolean isDone() {
        return this.isDone;
    }
}
