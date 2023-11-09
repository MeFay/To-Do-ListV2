import java.sql.Timestamp;

public class TaskOperations {
    private String operation;

    private Task task;

    private Timestamp timestamp;


    public TaskOperations(String operation, Task task, Timestamp timestamp) {
        this.operation = operation;
        this.task = task;
        this.timestamp = timestamp;
    }

@Override public String toString(){
        return  "| Modification: " + operation +  "Task: " + task.getTaskContent() + "| Timestamp: " + timestamp;
}
}
