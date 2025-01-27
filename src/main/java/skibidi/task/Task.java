package skibidi.task;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,      // Use the 'type' property to identify the subtype
        include = JsonTypeInfo.As.PROPERTY,
        property = "taskType"              // The JSON property that indicates the subtype
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = toDo.class, name = "T"),
        @JsonSubTypes.Type(value = Event.class, name = "E"),
        @JsonSubTypes.Type(value = Deadline.class, name = "D")
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Task {
    private String taskName;
    private boolean isDone;
    String taskType;

    public Task() {}

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    protected String getTask() {
        return this.taskName;
    }

    protected String getDone() {
        if (this.isDone) {
            return "[X]";
        }
        return "[ ]";
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    protected String getTaskType() {
        return "[" + this.taskType + "]";
    }

    public String toString() {
        return getTaskType() + getDone() + " " + getTask();
    }
}
