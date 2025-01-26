import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class Deadline extends Task
{
    private LocalDate deadline;

    public Deadline() {
        super.taskType = "D";
    }

    public Deadline(String taskName, LocalDate deadline) {
        super(taskName);
        super.taskType = "D";
        this.deadline = deadline;
    }

    public String toString() {
        DateTimeFormatter df = new DateTimeFormatterBuilder().appendPattern("dd/MMM/yyyy").toFormatter();
        return super.getTaskType() + super.getDone() + " " + super.getTask() + "(by: " + this.deadline.format(df) + ")";
    }
}
