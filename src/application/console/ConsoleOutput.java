package application.console;

import application.command.Command;
import model.Task;

import static application.color.ColorConsole.*;
import static service.task_list.TaskManager.*;

public class ConsoleOutput {
    public static void showNewTask() {
        for (Task task : NEW_TASK_LIST) {
            System.out.println(ANSI_WHITE + task + ANSI_RESET);
        }
    }

    public static void showInProgressTask() {
        for (Task task : IN_PROGRESS_TASK_LIST) {
            System.out.println(ANSI_BLUE + task + ANSI_RESET);
        }
    }

    public static void showDownTask() {
        for (Task task : DONE_TASK_LIST) {
            System.out.println(ANSI_GREEN + task + ANSI_RESET);
        }
    }

    public static void welcomeMessage() {
        System.out.println("\nПривет, это Консольный TODO. И вот все задачи которые есть:\n");
    }

    public static void showTask() {
        showNewTask();
        showInProgressTask();
        showDownTask();
    }

    public static void showConsoleCommand() {
        System.out.println(ANSI_PURPLE + "\nВот консольные команды которые доступны:\n");
        for (Command command : Command.values()) {
            System.out.println(ANSI_PURPLE + command.getDiscription() + ": " + command.getCommandName() + ANSI_RESET);
        }
    }
}
