package service;

import application.command.Command;
import model.Task;
import model.status.Status;
import org.w3c.dom.NodeList;
import parser.ParserAbstract;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static application.color.ColorConsole.*;
import static application.color.ColorConsole.ANSI_RESET;
import static parser.ParserAbstract.ALL_FIELDS;

public abstract class Service {
    public static List<Task> TASK_LIST = new ArrayList<>();
    public static List<Task> NEW_TASK_LIST = new ArrayList<>();
    public static List<Task> IN_PROGRESS_TASK_LIST = new ArrayList<>();
    public static List<Task> DONE_TASK_LIST = new ArrayList<>();


    ParserAbstract parser;

    public Service(ParserAbstract parser) {
        this.parser = parser;
    }

    public static final String PATH_XML_FORMAT = "data/data_xml.xml";

    public abstract void writeToFile(List<Task> allTask);

    public abstract List<Task> getAllModel();

    public abstract NodeList getNodeList(String path);

    public Task addNewTask() {
        Map<String, String> fields = getFields();
        Task task = new Task();
        task.setId(Integer.parseInt(fields.get("id")));
        task.setCaption(fields.get("caption"));
        task.setDescription(fields.get("Description"));
        task.setPriority(Integer.parseInt(fields.get("Priority")));
        task.setDeadline(parser.toLocalDate(fields.get("Deadline")));
        task.setStatus(parser.toStatus(fields.get("Status")));
        return task;
    }

    public Task editTask() {
        System.out.println("");

        Task task = new Task();

        return task;
    }

    public Map<String, String> getFields() {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> dataForTask = new HashMap<>();
        String[] fields = new String[ALL_FIELDS.length];
        System.out.println(ANSI_CYAN + "Для добавления нового задания введи следующие данные" + ANSI_RESET);

        for (int i = 0; i < ALL_FIELDS.length; i++) {
            if (ALL_FIELDS[i].equals("id")) {
                dataForTask.put(ALL_FIELDS[i], String.valueOf(getIdUnique()));
                continue;
            }
            System.out.println(ANSI_CYAN + "Введите " + ALL_FIELDS[i] + ANSI_RESET);
            if (ALL_FIELDS[i].equals("Deadline")) {
                dataForTask.put(ALL_FIELDS[i], writeDeadline(scanner));
                continue;
            }
            if (ALL_FIELDS[i].equals("Status")) {
                dataForTask.put(ALL_FIELDS[i], writeStatus(scanner));
                continue;
            }
            dataForTask.put(ALL_FIELDS[i], scanner.nextLine());
        }
        return dataForTask;
    }

    private int getIdUnique() {
        return TASK_LIST.stream().map(Task::getId).mapToInt(Integer::intValue).max().getAsInt() + 1;
    }

    public void addModelToListStatus(Task task) {
        if (task.getStatus() == Status.NEW) {
            NEW_TASK_LIST.add(task);
        } else if (task.getStatus() == Status.IN_PROGRESS) {
            IN_PROGRESS_TASK_LIST.add(task);
        } else {
            DONE_TASK_LIST.add(task);
        }
    }

    private String writeStatus(Scanner scanner) {
        System.out.println(ANSI_PURPLE + "Выбирите один из возможных статусов : " + ANSI_RESET);
        int countStatus = 0;
        HashMap<Integer, String> statusMap = new HashMap<>();
        for (Status status : Status.values()) {
            System.out.println(ANSI_PURPLE + ++countStatus + ") " + status.getName() + ANSI_RESET);
            statusMap.put(Integer.valueOf(countStatus), status.getName());
        }
        System.out.println(ANSI_PURPLE + "Выберете число статуса, который вы хотите поставить: " + ANSI_RESET);
        try {
            return statusMap.get(scanner.nextInt());
        } catch (InputMismatchException e) {
            System.out.println(ANSI_RED + "Вы ввели неверное число" + ANSI_RESET);
            return writeStatus(scanner);
        }
    }

    private String writeDeadline(Scanner scanner) {
        System.out.println(ANSI_PURPLE + "Введите дату в формате '2000-01-01'" + ANSI_RESET);
        String data = scanner.nextLine();

        Pattern pattern = pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            return data;
        } else {
            System.out.println(ANSI_RED + "Вы не правильно ввели дату" + ANSI_RESET);
            return writeDeadline(scanner);
        }

    }

    public Command consoleScanner() {
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


    public void showNewTask() {
        for (Task task : NEW_TASK_LIST) {
            System.out.println(ANSI_WHITE + task + ANSI_RESET);
        }
    }

    public void showInProgressTask() {
        for (Task task : IN_PROGRESS_TASK_LIST) {
            System.out.println(ANSI_BLUE + task + ANSI_RESET);
        }
    }

    public void showDownTask() {
        for (Task task : DONE_TASK_LIST) {
            System.out.println(ANSI_GREEN + task + ANSI_RESET);
        }
    }

    public void welcomeMessage() {
        System.out.println("\nПривет, это Консольный TODO. И вот все задачи которые есть:\n");
    }

    public void showTask() {
        showNewTask();
        showInProgressTask();
        showDownTask();
    }

    public void showConsoleCommand() {
        System.out.println(ANSI_PURPLE + "\nВот консольные команды которые доступны:\n");
        for (Command command : Command.values()) {
            System.out.println(ANSI_PURPLE + command.getDiscription() + ": " + command.getCommandName() + ANSI_RESET);
        }
    }


}
