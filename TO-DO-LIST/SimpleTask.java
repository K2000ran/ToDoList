import java.time.LocalDate;

public class SimpleTask extends Task {
    public SimpleTask(String description, LocalDate date, LocalDate dueDate, Priority priority) {
        super(description, date, dueDate, priority);
    }

    public SimpleTask(int id, String description, LocalDate date, LocalDate dueDate, Priority priority, boolean completed) {
        super(id, description, date, dueDate, priority, completed);
    }
}
