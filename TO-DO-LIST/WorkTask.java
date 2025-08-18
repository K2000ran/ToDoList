import java.time.LocalDate;

public class WorkTask extends Task {
    public WorkTask(String description, LocalDate date, LocalDate dueDate, Priority priority) {
        super(description, date, dueDate, priority);
    }
}
