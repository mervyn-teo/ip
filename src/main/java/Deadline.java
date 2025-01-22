public class Deadline extends Task
{
    private String deadline;

    public Deadline(String taskName, String deadline) {
        super(taskName);
        super.taskType = "D";
        this.deadline = deadline;
    }

    public String getDeadline() {
        return this.deadline;
    }

    public String toString() {
        String ret =  super.getTaskType() + super.getDone() + " " + super.getTask() + "(by: " + this.deadline + ")";
        return ret;
    }
}
