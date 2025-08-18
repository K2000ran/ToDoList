import java.time.LocalDate;
import java.util.*;

public class ToDoListApp {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== TO-DO LIST MENU =====");
            System.out.println("1. Add Task");
            System.out.println("2. Delete Task");
            System.out.println("3. Mark Task Completed");
            System.out.println("4. See All Tasks");
            System.out.println("5. Search Task");
            System.out.println("6. Update Task");

            System.out.println("7. Show Statistics");
            System.out.println("8. Exit");
            System.out.println("9. Show Pending Tasks");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter description: ");
                    String desc = sc.nextLine();
                    System.out.print("Enter creation date (yyyy-mm-dd): ");
                    LocalDate date = LocalDate.parse(sc.nextLine());
                    System.out.print("Enter due date (yyyy-mm-dd): ");
                    LocalDate dueDate = LocalDate.parse(sc.nextLine());
                    System.out.print("Enter priority (LOW, MEDIUM, HIGH): ");
                    Task.Priority priority = Task.Priority.valueOf(sc.nextLine().toUpperCase());
                    System.out.print("Task type (event/work): ");
                    String type = sc.nextLine();

                    Task task = type.equalsIgnoreCase("event")
                            ? new EventTask(desc, date, dueDate, priority)
                            : new WorkTask(desc, date, dueDate, priority);

                    manager.addTask(task);
                    System.out.println("Task added with ID: " + task.getId());
                    break;

                case 2:
                    System.out.print("Enter task ID to delete: ");
                    int delId = sc.nextInt();
                    System.out.println(manager.deleteTask(delId) ? "Task deleted." : "Task not found.");
                    break;

                case 3:
                    System.out.print("Enter task ID to mark completed: ");
                    int compId = sc.nextInt();
                    System.out.println(manager.markTaskCompleted(compId) ? "Task completed." : "Task not found.");
                    break;

                case 4:
                    manager.showAllTasks();
                    break;

                case 5:
                    System.out.print("Search by keyword (or leave blank): ");
                    String keyword = sc.nextLine();
                    System.out.print("Search by date (yyyy-mm-dd or blank): ");
                    String dateStr = sc.nextLine();
                    LocalDate searchDate = dateStr.isEmpty() ? null : LocalDate.parse(dateStr);
                    System.out.print("Search by ID (or 0): ");
                    int sid = sc.nextInt();
                    sc.nextLine();

                    List<Task> results = manager.searchTasks(
                            keyword.isEmpty() ? null : keyword,
                            searchDate,
                            sid == 0 ? null : sid
                    );
                    results.forEach(System.out::println);
                    break;

                case 6:
                    System.out.print("Enter task ID to update: ");
                    int uid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("New description (or leave blank): ");
                    String newDesc = sc.nextLine();
                    if (newDesc.isEmpty()) newDesc = null;

                    System.out.print("New due date (yyyy-mm-dd or blank): ");
                    String newDueStr = sc.nextLine();
                    LocalDate newDue = newDueStr.isEmpty() ? null : LocalDate.parse(newDueStr);

                    System.out.print("New priority (LOW, MEDIUM, HIGH or blank): ");
                    String newPrioStr = sc.nextLine();
                    Task.Priority newPrio = newPrioStr.isEmpty() ? null : Task.Priority.valueOf(newPrioStr.toUpperCase());

                    System.out.println(manager.updateTask(uid, newDesc, newDue, newPrio)
                            ? "Task updated." : "Task not found.");
                    break;

                case 7:
                    manager.showStatistics();
                    break;
                case 9:
                        manager.showPendingTasks();  
                        break;

                case 8:
                    System.out.println("Exiting...");
                    return;
            }
        }
    }
}
