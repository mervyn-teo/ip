import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;
    public Event() {
        super.taskType = "E";
    }

    public Event(String taskName, LocalDate from, LocalDate to) {
        super(taskName);
        this.from = from;
        this.to = to;
        super.taskType = "E";
    }

    public String toString() {
        DateTimeFormatter df = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter();
        return super.getTaskType() + super.getDone() + " " + super.getTask() + "(from: " + this.from.format(df) + " to: " + this.to.format(df)+ ")";
    }
}
