public class Task {
    private String taskName;
    private boolean isDone;
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public String getTask() {
        return this.taskName;
    }

    public String getDone() {
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
}
