package skibidi.task;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

/**
 * Represents a Deadline task, which is a type of {@link Task}
 * that has an associated deadline date.
 * <p>
 * This class extends {@code Task} and adds functionality to store
 * and manage the deadline date.
 * </p>
 */
public class Deadline extends Task
{
    private LocalDate deadline;

    /**
     * Default constructor for creating a {@code Deadline} object.
     * <p>
     * The {@code taskType} in the superclass is automatically set to "D".
     * Created as a requirement for jackson library
     * </p>
     */
    public Deadline() {
        super.taskType = "D";
    }

    /**
     * Constructor for creating a {@code Deadline} object with a specified task name and deadline.
     *
     * @param taskName the name of the task
     * @param deadline the deadline for the task, as a {@code LocalDate}
     */
    public Deadline(String taskName, LocalDate deadline) {
        super(taskName);
        super.taskType = "D";
        this.deadline = deadline;
    }

    /**
     * Returns a string representation of the {@code Deadline} task.
     * <p>
     * The format includes the task type ("D"), its completion status,
     * the task name, and the formatted deadline date.
     * </p>
     * @return A string in the format: {@code [TaskType][CompletionStatus] taskName (by: dd/MM/yyyy)}
     */
    @Override
    public String toString() {
        DateTimeFormatter df = new DateTimeFormatterBuilder().appendPattern("dd/MMM/yyyy").toFormatter();
        return super.getTaskType() + super.getIsDone() + " " + super.getTask() + "(by: " + this.deadline.format(df) + ")";
    }


    /**
     * Returns the {@code LocalDate} representing the deadline of this task.
     *
     * @return the deadline of the task
     */
    public LocalDate getDeadline() {
        return this.deadline;
    }
}
