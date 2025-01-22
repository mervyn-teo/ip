public class Task {
    private String taskName;
    private boolean isDone;
    protected String taskType;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
        this.taskType = "this is not suppose to happen";
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
        String ret =  getTaskType() + getDone() + " " + getTask();
        return ret;
    }
}
