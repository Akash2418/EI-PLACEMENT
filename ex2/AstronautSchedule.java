import java.util.*;
class Task {
    String description;
    String startTime;
    String endTime;
    String priority;

    Task(String desc, String start, String end, String prio) {
        description = desc;
        startTime = start;
        endTime = end;
        priority = prio;
    }

    public String toString() {
        return startTime + " - " + endTime + ": " + description + " [" + priority + "]";
    }
}
class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks = new ArrayList<>();

    private ScheduleManager() {}

    public static ScheduleManager getInstance() {
        if (instance == null) instance = new ScheduleManager();
        return instance;
    }

    public boolean addTask(Task t) {
        for (Task task : tasks) {
            if (!(task.endTime.compareTo(t.startTime) <= 0 || t.endTime.compareTo(task.startTime) <= 0)) {
                System.out.println("Error: Task overlaps with \"" + task.description + "\"");
                return false;
            }
        }
        tasks.add(t);
        System.out.println("Task added successfully!");
        return true;
    }

    public boolean removeTask(String desc) {
        for (Task t : tasks) {
            if (t.description.equalsIgnoreCase(desc)) {
                tasks.remove(t);
                System.out.println("Task removed!");
                return true;
            }
        }
        System.out.println("Error: Task not found");
        return false;
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled.");
            return;
        }
        tasks.sort((a,b) -> a.startTime.compareTo(b.startTime));
        for (Task t : tasks) System.out.println(t);
    }
}
public class AstronautSchedule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ScheduleManager manager = ScheduleManager.getInstance();

        while (true) {
            System.out.println("\n1. Add Task  2. Remove Task  3. View Tasks  4. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Start (HH:mm): ");
                    String start = sc.nextLine();
                    System.out.print("End (HH:mm): ");
                    String end = sc.nextLine();
                    System.out.print("Priority (High/Medium/Low): ");
                    String prio = sc.nextLine();
                    manager.addTask(new Task(desc,start,end,prio));
                    break;

                case "2":
                    System.out.print("Task to remove: ");
                    String remove = sc.nextLine();
                    manager.removeTask(remove);
                    break;

                case "3":
                    System.out.println("Tasks:");
                    manager.viewTasks();
                    break;

                case "4":
                    System.out.println("Bye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
