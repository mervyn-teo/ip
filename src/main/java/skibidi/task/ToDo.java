package skibidi.task;

public class ToDo extends Task{
    public ToDo() {
        super.taskType = "T";
    }

    public ToDo(String taskName) {
        super(taskName);
        super.taskType = "T";
    }
}
