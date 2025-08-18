import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class TaskManager {
    private List<Task> tasks;
    private static final String FILE_NAME = "tasks.txt";

    public TaskManager() {
        tasks = new ArrayList<>();
        loadTasksFromFile(); // Load old tasks at startup
    }

    // ✅ Add Task
    public void addTask(Task task) {
        tasks.add(task);
        saveTasksToFile();
    }

    // ✅ Delete Task (returns true/false)
    public boolean deleteTask(int id) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId() == id) {
                iterator.remove();
                saveTasksToFile();
                return true;
            }
        }
        return false;
    }

    // ✅ Mark Completed (returns true/false)
    public boolean markTaskCompleted(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setCompleted(true);
                saveTasksToFile();
                return true;
            }
        }
        return false;
    }

    // ✅ Show All Tasks
    public void showAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    // ✅ Show Pending Tasks
    public void showPendingTasks() {
        boolean found = false;
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No pending tasks found.");
        }
    }

    // ✅ Search Tasks
    public List<Task> searchTasks(String keyword, LocalDate date, Integer id) {
        List<Task> results = new ArrayList<>();
        for (Task task : tasks) {
            boolean matches = true;

            if (keyword != null && !task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matches = false;
            }
            if (date != null && !task.getDate().equals(date)) {
                matches = false;
            }
            if (id != null && task.getId() != id) {
                matches = false;
            }

            if (matches) results.add(task);
        }
        return results;
    }

    // ✅ Update Task
    public boolean updateTask(int id, String newDesc, LocalDate newDue, Task.Priority newPrio) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                if (newDesc != null) task.setDescription(newDesc);
                if (newDue != null) task.setDueDate(newDue);
                if (newPrio != null) task.setPriority(newPrio);
                saveTasksToFile();
                return true;
            }
        }
        return false;
    }

    // ✅ Show Statistics
    public void showStatistics() {
        long completed = tasks.stream().filter(Task::isCompleted).count();
        long pending = tasks.size() - completed;
        double completionRate = tasks.isEmpty() ? 0 : (completed * 100.0 / tasks.size());

        System.out.println("📊 Statistics:");
        System.out.println("Total tasks: " + tasks.size());
        System.out.println("Completed tasks: " + completed);
        System.out.println("Pending tasks: " + pending);
        System.out.printf("Completion Rate: %.2f%%\n", completionRate);
    }

    // ✅ Save tasks to file
    private void saveTasksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    // ✅ Load tasks from file
    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromFileString(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("No saved tasks found, starting fresh.");
        }
    }
}
