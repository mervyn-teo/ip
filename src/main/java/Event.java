import com.fasterxml.jackson.annotation.JsonAutoDetect;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Event extends Task {
    private String from;
    private String to;
    public Event() {
        super.taskType = "E";
    }

    public Event(String taskName, String from, String to) {
        super(taskName);
        this.from = from;
        this.to = to;
        super.taskType = "E";
    }

    public String toString() {
        return super.getTaskType() + super.getDone() + " " + super.getTask() + "(from: " + this.from + " to: " + this.to + ")";
    }
}
