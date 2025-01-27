package skibidi.task;

public class toDo extends Task{
    public toDo() {
        super.taskType = "T";
    }

    public toDo(String taskName) {
        super(taskName);
        super.taskType = "T";
    }
}
