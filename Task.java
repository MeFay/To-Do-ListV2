import java.sql.Timestamp;

public class Task {
    private String taskContent;
    private String addNote;
    private boolean taskDone;
    private Timestamp timestamp;

    public Task(String content, String note, Timestamp timestamp) {
        this.taskContent = content;
        this.addNote = note;
        this.taskDone = false;
        this.timestamp = timestamp;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getAddNote() {
        return addNote;
    }

    public void setAddNote(String addNote) {
        this.addNote = addNote;
    }

    public boolean isTaskDone() {
        return taskDone;
    }

    public void setTaskDone(boolean taskIsDone) {
        this.taskDone = taskIsDone;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String info() {
        return "Task: " + this.taskContent + "| Note: " + this.addNote + "| Done: " + this.taskDone;
    }

}


