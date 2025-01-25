import com.fasterxml.jackson.annotation.JsonAutoDetect;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class Deadline extends Task
{
    private String deadline;

    public Deadline() {
        super.taskType = "D";
    }

    public Deadline(String taskName, String deadline) {
        super(taskName);
        super.taskType = "D";
        this.deadline = deadline;
    }

    public String toString() {
        return super.getTaskType() + super.getDone() + " " + super.getTask() + "(by: " + this.deadline + ")";
    }
}
