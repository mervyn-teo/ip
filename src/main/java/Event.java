public class Event extends Task {
    private String from;
    private String to;

    public Event(String taskName, String from, String to) {
        super(taskName);
        this.from = from;
        this.to = to;
        super.taskType = "E";
    }
    public String getFrom() {
        return this.from;
    }
    public String getTo() {
        return this.to;
    }

    public String toString() {
        String ret = super.getTaskType() + super.getDone() + " " + super.getTask() + "(from: " + this.from + " to: " + this.to + ")";
        return ret;
    }
}
