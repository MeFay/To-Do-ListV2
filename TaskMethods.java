import java.sql.Timestamp;
import java.util.*;

public class TaskMethods {

    static ArrayList<TaskOperations> historyTasksList = new ArrayList<>();
    static ArrayList<Task> tasksList = new ArrayList<>();
    static ArrayList<Task> deletedTasksList = new ArrayList<>();
    static int completedTasks = 0;
    static Timestamp timestamp;


    public static void updateHistory(String operation, Task task, Timestamp timestamp) {
        TaskOperations taskOperations = new TaskOperations(operation, task, timestamp);
        historyTasksList.add(taskOperations);
    }

    public static void viewTaskList() {
        try {
            int totalTasks = tasksList.size();
            int completedTasks = 0; // Initialize the completedTasks counter

            LogsManager.log("\nTask List: ");
            LogsManager.log("Total Tasks: " + totalTasks);

            for (int i = 0; i < tasksList.size(); i++) {
                Task task = tasksList.get(i);

                String status = task.isTaskDone() ? "\n  Done ✔︎" : "  Undone ✖︎";
                System.out.println((i + 1) + ": " + task.getTaskContent() + "  |  " + task.getAddNote() + status);

                if (task.isTaskDone()) {
                    completedTasks++;
                }
            }

            // Calculate the completion percentage (with proper handling for zero totalTasks)
            double completionPercentage = totalTasks > 0 ? ((double) completedTasks / totalTasks) * 100 : 0;
            LogsManager.log("\nCompleted Tasks: " + completedTasks + " 💛Keep it up");
            LogsManager.log("Percentage of Tasks Completed: " + completionPercentage + "%");
        } catch (Exception e) {
            LogsManager.log("An error occurred while viewing the task list: " + e.getMessage());
        }
    }

    public static void createTask() {
        try {
            boolean premiumPlan = false;
            Scanner userOptionScanner = new Scanner(System.in);
            timestamp = new Timestamp(System.currentTimeMillis());

            LogsManager.logInput("Create a task:");
            String userNewTask = userOptionScanner.nextLine().trim();

            LogsManager.logInput("Add a note:");
            String userNewNote = userOptionScanner.nextLine().trim();

            if (!userNewTask.isEmpty()) {
                boolean taskAlreadyExists = false;

                for (Task existingTask : tasksList) {
                    if (existingTask.getTaskContent().equals(userNewTask)) {
                        LogsManager.log("That task already exists.");
                        taskAlreadyExists = true;
                        break;
                    }
                }

                if (!taskAlreadyExists) {
                    if (!premiumPlan && ToDoList.taskCounter >= 10) {
                        LogsManager.log("\nTask limit reached! Upgrade to the Premium Plan to add more tasks.");
                    } else {
                        Task newTask = new Task(userNewTask, userNewNote, timestamp);
                        updateHistory("Create", newTask, timestamp);
                        tasksList.add(newTask);
                        ToDoList.taskCounter++;
                        LogsManager.log("\nThe task >" + userNewTask + "< was created!");
                        orderAz();
                    }
                }
            } else {
                LogsManager.log("Task name cannot be empty.");
            }
        } catch (Exception e) {
            LogsManager.log("An error occurred while creating the task: " + e.getMessage());
        }
    }


    public static void editTask() {
        try {
            Scanner userOptionScanner = new Scanner(System.in);
            int userOptionChoice = 10;
            while (userOptionChoice != 0) {

                LogsManager.logInput("\n⦿ What would you like to do?: ");

                LogsManager.log("\n1・ Edit a task");
                LogsManager.log("2・ Add a note");
                LogsManager.log("0・ Go back to the menu");

                userOptionChoice = userOptionScanner.nextInt();

                switch (userOptionChoice) {
                    case 1: //edit option
                        LogsManager.log("\nWhich task would you like to edit?");

                        viewTaskList();

                        int editTaskNumber = userOptionScanner.nextInt();

                        if (editTaskNumber >= 1 && editTaskNumber <= tasksList.size()) {
                            Task taskToEdit = tasksList.get(editTaskNumber - 1);

                            LogsManager.logInput("Modify the task to: ");
                            userOptionScanner.nextLine();
                            String modifyingContent = userOptionScanner.nextLine().trim();

                            if (modifyingContent.equals(taskToEdit.getTaskContent())) {
                                LogsManager.log("That task exists already!");
                            } else {
                                taskToEdit.setTaskContent(modifyingContent);
                                LogsManager.log("Task successfully edited! It is now: " + modifyingContent);
                                updateHistory("Edit ", taskToEdit, timestamp);
                                orderAz();
                            }
                        } else {
                            LogsManager.log("Invalid task number. Please enter a valid task number.");
                        }
                        break;
                    case 2:
                        LogsManager.log("Which task would you like to add a note?");

                        viewTaskList();

                        int taskToAddNote = userOptionScanner.nextInt();
                        userOptionScanner.nextLine();

                        if (taskToAddNote >= 1 && taskToAddNote <= tasksList.size()) {
                            Task taskNoteIndex = tasksList.get(taskToAddNote - 1);

                            LogsManager.logInput("\nNote content: ");
                            String noteContent = userOptionScanner.nextLine().trim();

                            // Update the task's note and timestamp
                            taskNoteIndex.setAddNote(noteContent);


                            LogsManager.log("\nNote added to the task: " + noteContent + " ✏️");
                            updateHistory("Add note ", taskNoteIndex, timestamp);
                        } else {
                            LogsManager.log("\nInvalid task number. Please enter a valid task number.");
                        }
                        break;
                    case 0:
                        return;
                    default:
                        LogsManager.log("✖︎ Invalid choice ✖︎ Please try again.");
                }
            }
        } catch (Exception e) {
            LogsManager.log("An error occurred while editing the task: " + e.getMessage());
        }
    }


    public static void markAsDoneUndone() {
        try {
            Scanner userOptionScanner = new Scanner(System.in);
            int userOptionChoice = 10;
            while (userOptionChoice != 0) {
                LogsManager.logInput("\n⦿ What would you like to do?: ");
                LogsManager.log("\n1・ Check a task ✔︎");
                LogsManager.log("2・ Uncheck a task ✖︎");
                LogsManager.log("0・ Go back to the menu");

                userOptionChoice = userOptionScanner.nextInt();

                for (int i = 0; i < tasksList.size(); i++) {
                    Task task = tasksList.get(i);
                    String status = task.isTaskDone() ? "  Done ✔︎" : "Undone ✖︎";
                    LogsManager.logInput((i + 1) + " : " + task.getTaskContent() + " (" + status + ")");
                    orderAz();
                }


                switch (userOptionChoice) {
                    case 1: //check task as done
                        LogsManager.logInput("\nWhich task would you like to mark as done?");
                        int taskToCheck = userOptionScanner.nextInt();

                        if (taskToCheck >= 1 && taskToCheck <= tasksList.size()) {
                            Task task = tasksList.get(taskToCheck - 1);

                            if (!task.isTaskDone()) {
                                task.setTaskDone(true);
                                completedTasks++;
                                LogsManager.log("\nTask marked as Done ✔︎\n︎︎");
                                updateHistory("Add note ", task, timestamp);
                            } else {
                                LogsManager.log("\nTask is already checked as Done ✔︎");
                            }
                        } else {
                            LogsManager.log("\nInvalid task number. Please enter a valid task number.");
                        }
                        break;
                    case 2: //uncheck task as done
                        LogsManager.logInput("\nWhich task would you like to mark as undone?");
                        int taskToUncheck = userOptionScanner.nextInt();

                        if (taskToUncheck >= 1 && taskToUncheck <= tasksList.size()) {
                            Task task = tasksList.get(taskToUncheck - 1);

                            if (task.isTaskDone()) {
                                task.setTaskDone(false);
                                completedTasks--;
                                LogsManager.log("\nTask marked as Undone ✖︎");
                                updateHistory("Add note ", task, timestamp);
                                orderAz();
                            } else {
                                System.out.println("\nTask is already Undone ✖︎");
                            }
                        } else {
                            LogsManager.log("\nInvalid task number. Please enter a valid task number.");
                        }
                        break;
                    case 0:
                        return;

                    default:
                        LogsManager.log("\n✖︎ Invalid choice ✖︎ Please try again.");
                }
            }
        } catch (Exception e) {
            LogsManager.log("An error occurred while marking a task as done/undone: " + e.getMessage());
        }
    }


    public static void cleanTaskList() {
        try {
            Scanner userOptionScanner = new Scanner(System.in);
            int userOptionChoice = 10;
            while (userOptionChoice != 0) {
                LogsManager.logInput("\n⦿ What would you like to do?: ");
                LogsManager.log("\n1・ Delete a task");
                LogsManager.log("2・ Remove all Done Tasks");
                LogsManager.log("0・ Go back to the menu");

                userOptionChoice = userOptionScanner.nextInt();

                switch (userOptionChoice) {
                    case 1: //delete a task
                        viewTaskList();
                        LogsManager.logInput("Which task would you like to delete? ");

                        int taskToDelete = userOptionScanner.nextInt();
                        userOptionScanner.nextLine();

                        if (taskToDelete >= 1 && taskToDelete <= tasksList.size()) {
                            Task deletedTask = tasksList.get(taskToDelete - 1);
                            tasksList.remove(deletedTask);
                            deletedTasksList.add(deletedTask);
                            LogsManager.log("Task deleted.");
                            updateHistory("Delete task ", deletedTask, timestamp);
                        } else {
                            LogsManager.log("Invalid task number. Please enter a valid task number.");
                        }

                        break;
                    case 2: //remove all done Tasks
                        for (int i = 0; i < tasksList.size(); i++) {
                            Task task = tasksList.get(i);
                            if (task.isTaskDone()) {
                                deletedTasksList.add(task);
                                updateHistory("Deleted task", task, timestamp);
                            }
                        }
                        tasksList.removeIf(Task::isTaskDone);
                        viewTaskList();
                        break;
                    case 0:
                        return;
                    default:
                        LogsManager.log("✖︎ Invalid choice ✖︎ Please try again.");
                }
            }
        } catch (Exception e) {
            LogsManager.log("An error occurred while cleaning the task list: " + e.getMessage());
        }
    }


    public static void recoverTask() {
        try {
            LogsManager.log("Deleted Tasks List: ");
            for (int i = 0; i < deletedTasksList.size(); i++) {
                System.out.println((i + 1) + ". " + deletedTasksList.get(i).getTaskContent());
            }
            LogsManager.logInput("Which task would you like to retrieve?");
            Scanner userOptionScanner = new Scanner(System.in);
            int taskToRetrieve = userOptionScanner.nextInt();

            if (taskToRetrieve >= 1 && taskToRetrieve <= deletedTasksList.size()) {
                Task retrievedTask = deletedTasksList.get(taskToRetrieve - 1);
                tasksList.add(retrievedTask);
                deletedTasksList.remove(retrievedTask);
                LogsManager.log("\nTask retrieved!");

                updateHistory("Add note", retrievedTask, timestamp);
            } else {
                LogsManager.log("Invalid task number. Please enter a valid task number.");
            }
        } catch (Exception e) {
            LogsManager.log("An error occurred while recovering a task: " + e.getMessage());
        }
    }


    public static boolean upgradeToDoListPlan(boolean premiumPlan) {
        Scanner scan = new Scanner(System.in);
        if (!premiumPlan) {
            LogsManager.log("\nDo you want to buy Premium Plan? (yes or no)");
            LogsManager.logInput("> ");
            String userUpgradeOption = scan.next();

            switch (userUpgradeOption) {
                case "yes":
                    premiumPlan = true;
                    LogsManager.log("\nCurrently plan set to Premium! Thank you!");
                    break;
                default:
                    premiumPlan = false;
                    LogsManager.log("\nMaybe next time then...");
                    break;
            }
        } else {
            LogsManager.log("\nYour plan is already set to Premium! You don't need to buy it again.");
        }
        return premiumPlan;
    }


    public static void visualizeHistory() {
        for (int i = 0; i < historyTasksList.size(); i++) {
            System.out.println(historyTasksList.get(i).toString());
        }
        //System.out.println(historyTasksList);
    }


    public static void orderAz() {
        try {
            tasksList.sort(Comparator.comparing(Task::getTaskContent));
        } catch (Exception e) {
            LogsManager.log("An error occurred while ordering tasks alphabetically: " + e.getMessage());
        }
    }
}



