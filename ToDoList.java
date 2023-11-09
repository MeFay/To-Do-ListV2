import java.util.InputMismatchException;
import java.util.Scanner;

public class ToDoList {
    static int taskCounter = 0;

    public static void main(String[] args) {
        startApp();
    }


    public static void startApp(){
        Scanner scan = new Scanner(System.in);
        int userChoice = 0;
        boolean premiumPlan = false;
        String[] toDoList = new String[premiumPlan ? 50 : 10];

        if (!premiumPlan) {
            LogsManager.log("\t\t\t\t\u001b[43;1m\u001b[38;5;15mIMPORTANT WARNING\u001b[0m\u001b[38;5;11m\nYou are currently using the Free Plan of ToDoList!\nYou can upgrade to Premium Plan in the menu!\u001b[0m");
        }

        do {
            try {
                LogsManager.log("\nYou still have " + (toDoList.length - taskCounter) + " free spaces on the list!\n");
                LogsManager.log("1 - View Task List");
                LogsManager.log("2 - Create Task");
                LogsManager.log("3 - Edit Task");
                LogsManager.log("4 - Mark as Done/Undone");
                LogsManager.log("5 - Clean List");
                LogsManager.log("6 - Recover Tasks");
                LogsManager.log("7 - History");
                LogsManager.log("8 - Upgrade ToDoList Plan");
                LogsManager.log("0 - Exit");
                LogsManager.logInput("Choose an option: ");
                userChoice = scan.nextInt();
                scan.nextLine(); // Consume the newline character

                switch (userChoice) {
                    case 1:
                        TaskMethods.viewTaskList();
                        break;
                    case 2:
                        TaskMethods.createTask();
                        break;
                    case 3:
                        TaskMethods.editTask();
                        break;
                    case 4:
                        TaskMethods.markAsDoneUndone();
                        break;
                    case 5:
                        TaskMethods.cleanTaskList();
                        break;
                    case 6:
                        TaskMethods.recoverTask();
                        break;
                    case 7:
                        TaskMethods.visualizeHistory();
                        break;
                    case 8:
                        premiumPlan = TaskMethods.upgradeToDoListPlan(premiumPlan);
                        if (premiumPlan) {
                            String[] tempToDoList = new String[30];

                            for (int i = 0; i < toDoList.length; i++) {
                                if (i < tempToDoList.length) {
                                    tempToDoList[i] = toDoList[i];
                                } else {
                                    break;
                                }
                            }

                            toDoList = tempToDoList;
                        }
                        break;
                    case 0:
                        LogsManager.log("Closing ToDoList program...Hope to see you soon, byebye");
                        break;
                    default:
                        LogsManager.log("Invalid option!");
                        startApp();
                        break;
                }
            } catch (InputMismatchException e) {
                LogsManager.log("Invalid input. Please enter a valid option.");
                startApp();
                scan.nextLine();
            }
        } while (userChoice != 0);
    }
}