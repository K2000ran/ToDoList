import java.time.LocalDate;

public class EventTask extends Task {
    public EventTask(String description, LocalDate date, LocalDate dueDate, Priority priority) {
        super(description, date, dueDate, priority);
    }
}
