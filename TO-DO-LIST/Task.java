import java.time.LocalDate;

public abstract class Task {
    private static int idCounter = 1;
    protected int id;
    protected String description;
    protected LocalDate date;
    protected LocalDate dueDate;
    protected Priority priority;
    protected boolean completed;

    public enum Priority { LOW, MEDIUM, HIGH }

    // For new tasks
    public Task(String description, LocalDate date, LocalDate dueDate, Priority priority) {
        this.id = idCounter++;
        this.description = description;
        this.date = date;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false;
    }

    // For reloaded tasks from file
    public Task(int id, String description, LocalDate date, LocalDate dueDate, Priority priority, boolean completed) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = completed;
        if (id >= idCounter) idCounter = id + 1;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public LocalDate getDueDate() { return dueDate; }
    public Priority getPriority() { return priority; }
    public boolean isCompleted() { return completed; }

    public void setDescription(String description) { this.description = description; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public void markCompleted() { this.completed = true; }

    // ✅ Convert object to file string
    public String toFileString() {
        return id + "," + description + "," + date + "," + dueDate + "," + priority + "," + completed;
    }

    // ✅ Create Task object from file line
    public static Task fromFileString(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length != 6) return null;

            int id = Integer.parseInt(parts[0]);
            String description = parts[1];
            LocalDate date = LocalDate.parse(parts[2]);
            LocalDate dueDate = LocalDate.parse(parts[3]);
            Priority priority = Priority.valueOf(parts[4]);
            boolean completed = Boolean.parseBoolean(parts[5]);

            return new SimpleTask(id, description, date, dueDate, priority, completed);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | Priority: %s | Due: %s | Completed: %s",
                id, description, priority, dueDate, completed ? "Yes" : "No");
    }
}
