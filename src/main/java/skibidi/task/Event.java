package skibidi.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Represents an event task with a specific start and end date.
 * Extends the {@link Task} class and provides additional fields and functionality to handle
 * the time period (`from` and `to`) of the task.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    /**
     * Default constructor. Initializes a new {@code Event} object with a task type of "E".
     * Created for jackson library
     */
    public Event() {
        super.taskType = "E";
    }


    /**
     * Constructor for creating an event with a given task name and date range.
     *
     * @param taskName The name or description of the event.
     * @param from The starting date of the event.
     * @param to The ending date of the event.
     */
    public Event(String taskName, LocalDate from, LocalDate to) {
        super(taskName);
        this.from = from;
        this.to = to;
        super.taskType = "E";
    }

    /**
     * Returns a string representation of the event.
     * The representation includes the task type, its completion status, task name, and the date range.
     *
     * @return A string in the format: {@code [TaskType][CompletionStatus] taskName (from: dd/MM/yyyy to: dd/MM/yyyy)}
     */
    @Override
    public String toString() {
        DateTimeFormatter df = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter();
        return super.getTaskType() + super.getIsDone() + " " + super.getTask()
                + "(from: " + this.from.format(df) + " to: " + this.to.format(df) + ")";
    }

    /**
     * Gets the start date of the event.
     *
     * @return The starting date of the event.
     */
    @JsonIgnore
    public LocalDate getStartDate() {
        return this.from;
    }

    /**
     * Gets the end date of the event.
     *
     * @return The ending date of the event.
     */
    @JsonIgnore
    public LocalDate getEndDate() {
        return this.to;
    }
}
