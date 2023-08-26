package application;

import application.command.Command;
import model.Task;
import model.status.Status;
import parser.ParserAbstract;
import parser.ParserXML;

import static parser.ParserXML.*;

import service.Service;
import service.ServiceXML;

import static application.color.ColorConsole.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskTrackerApplication {
    private static List<Task> TASK_LIST = new ArrayList<>();
    private static List<Task> NEW_TASK_LIST = new ArrayList<>();
    private static List<Task> IN_PROGRESS_TASK_LIST = new ArrayList<>();
    private static List<Task> DONE_TASK_LIST = new ArrayList<>();
    Service service;

    public TaskTrackerApplication() {
        this.service = new ServiceXML();
    }

    private void init() {

        TASK_LIST = service.getAllModel();

        for (Task task : TASK_LIST) {
            if (task.getStatus() == Status.NEW) {
                NEW_TASK_LIST.add(task);
            }
            if (task.getStatus() == Status.IN_PROGRESS) {
                IN_PROGRESS_TASK_LIST.add(task);
            }
            if (task.getStatus() == Status.DONE) {
                DONE_TASK_LIST.add(task);
            }
        }

    }

    public void application() {
        init();

        welcomeMessage();

        showTask();

        showConsoleCommand();

        service.writeToFile(TASK_LIST);

        boolean flag = true;

        while (flag) {
            Command userCommand = consoleScanner();
            switch (userCommand) {
                case STOP:
                    flag = false;
                    break;
                case HELP:
                    showConsoleCommand();
                    continue;
                case LIST:
                    showTask();
                    continue;
                case LIST_NEW:
                    showNewTask();
                    continue;
                case LIST_IN_PROGRESS:
                    showInProgressTask();
                    continue;
                case LIST_DONE:
                    showDownTask();
                    continue;
                case ADD:
                    TASK_LIST.add(service.getModel(getFields()));
            }
        }
        service.writeToFile(TASK_LIST);

    }

    private void showTask() {
        showNewTask();
        showInProgressTask();
        showDownTask();
    }

    private String[] getFields() {
        Scanner scanner = new Scanner(System.in);
        String[] fields = new String[ATTRIBUTE.length + TAG_NAME.length];
        System.out.println("Для добавления нового задания введи следующие данные");
        for (int i = 0; i < ALL_FIELDS.length; i++) {
            System.out.println("Введите " + ATTRIBUTE[i]);
            if (ATTRIBUTE[i].equals("Deadline")) {
                System.out.println("Число введите в формате 2000-01-01");
                String time = scanner.nextLine();
            }
            if (ATTRIBUTE[i].equals("Compile")) {
                ATTRIBUTE[i] = null;
            }
            fields[i] = scanner.nextLine();
        }
        return fields;
    }

    private String getTime(Scanner scanner) {
        System.out.println("Введите дату в формате '2000-01-01'");
        String data = scanner.nextLine();

        Pattern pattern = pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            return data;
        } else {
            System.out.println("Вы не правильно ввели дату");
            return getTime(scanner);
        }

    }

    private void showNewTask() {
        for (Task task : NEW_TASK_LIST) {
            System.out.println(ANSI_WHITE + task + ANSI_RESET);
        }
    }

    private void showInProgressTask() {
        for (Task task : IN_PROGRESS_TASK_LIST) {
            System.out.println(ANSI_BLUE + task + ANSI_RESET);
        }
    }

    private void showDownTask() {
        for (Task task : DONE_TASK_LIST) {
            System.out.println(ANSI_GREEN + task + ANSI_RESET);
        }
    }

    private void showConsoleCommand() {
        System.out.println(ANSI_PURPLE + "\nВот консольные команды которые доступны:\n");
        for (Command command : Command.values()) {
            System.out.println(ANSI_PURPLE + command.getDiscription() + ": " + command.getCommandName() + ANSI_RESET);
        }

    }

    private void welcomeMessage() {
        System.out.println("\nПривет, это Консольный TODO. И вот все задачи которые есть:\n");
    }

    private Command consoleScanner() {
        System.out.println("Введите команду: ");
        Scanner scanner = new Scanner(System.in);
        String userCommand = scanner.nextLine();

        for (Command command : Command.values()) {
            Pattern pattern = pattern = Pattern.compile(command.getPattern());
            Matcher matcher = matcher = pattern.matcher(userCommand);
            if (matcher.find()) {
                return command;
            }
        }
        System.out.println("Не верно ввели команду, попробуйте ещё раз");
        return consoleScanner();
    }


}
