package service;

import application.command.Command;
import exception.StopException;
import model.Task;
import model.status.Status;
import org.w3c.dom.NodeList;
import parser.ParserAbstract;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static application.color.ColorConsole.*;

public abstract class Service {
    public static List<Task> TASK_LIST = new ArrayList<>();
    public static List<Task> NEW_TASK_LIST = new ArrayList<>();
    public static List<Task> IN_PROGRESS_TASK_LIST = new ArrayList<>();
    public static List<Task> DONE_TASK_LIST = new ArrayList<>();

    public static final String[] ALL_FIELDS = new String[]{"id", "caption", "Description", "Priority", "Deadline", "Status"};
    public static final String[] FIELDS_FOR_EDIT = new String[]{"caption", "Description", "Priority", "Deadline", "Status"};
    public static final String[] TAG_NAME = new String[]{"Description", "Priority", "Deadline", "Status", "Complete"};


    ParserAbstract parser;
    Scanner scanner;

    public Service(ParserAbstract parser) {
        this.parser = parser;
        scanner = new Scanner(System.in);
    }

    public static final String PATH_XML_FORMAT = "data/data_xml.xml";

    public abstract void writeToFile(List<Task> allTask);

    public abstract List<Task> getAllModel();

    public abstract NodeList getNodeList(String path);

    public Task addNewTask() throws StopException {
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

    private int checkInt() throws StopException {
        System.out.println("Введите число: ");
        String idTask = scanner.nextLine();
        stopMethod(idTask);
        int id = -1;
        try {
            id = Integer.parseInt(idTask);
            return id;
        } catch (RuntimeException e) {
            System.out.println("Вы неправильно ввели данные");
            return checkInt();
        }
    }


    public Task editTask() throws StopException {
        System.out.println("Выберите id задачи которую хотите изменить");
        showTask();
        int id = checkInt();
        Task task = getTaskById(id);
        editFieldInTask(task);

        return task;
    }

    private Task editFieldInTask(Task task) throws StopException {
        System.out.println("Что в задаче вы хотите изменить?");
        int count = 1;
        for (String field : FIELDS_FOR_EDIT) {
            System.out.println(count++ + ") " + field);
        }
        int idField = checkInt();
        if (FIELDS_FOR_EDIT.length < idField) {
            System.out.println("Вашего числа нет среди id полей, попробуйте ещё раз");
            editFieldInTask(task);
        }
        switch (idField) {
            case 1:
                System.out.println("Введите Заголовок");
                task.setCaption(scanner.nextLine());
                break;
            case 2:
                System.out.println("Введите Описание");
                task.setDescription(scanner.nextLine());
                break;
            case 3:
                System.out.println("Введите приоритет задачи: ");
                task.setPriority(checkInt());
                break;
            case 4:
                System.out.println("Введите дату дедлайна");
                task.setDeadline(parser.toLocalDate(writeDeadline()));
                break;
            case 5:
                System.out.println("Введите статус");
                task.setStatus(parser.toStatus(writeStatus()));
                if (task.getStatus() == Status.DONE) {
                    task.setComplete(LocalDate.now());
                }
                break;
        }
        return task;
    }

    private Task getTaskById(int id) {
        Optional<Task> optionalTask = TASK_LIST.stream().filter(task -> task.getId() == id).findFirst();
        if (optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            throw new RuntimeException();
        }
    }

    public Map<String, String> getFields() throws StopException {
        Map<String, String> dataForTask = new HashMap<>();
        System.out.println(ANSI_CYAN + "Для добавления нового задания введи следующие данные" + ANSI_RESET);
        String userText;

        for (int i = 0; i < ALL_FIELDS.length; i++) {
            if (ALL_FIELDS[i].equals("id")) {
                dataForTask.put(ALL_FIELDS[i], String.valueOf(getIdUnique()));
                continue;
            }
            System.out.println(ANSI_CYAN + "Введите " + ALL_FIELDS[i] + ANSI_RESET);
            if (ALL_FIELDS[i].equals("Deadline")) {
                dataForTask.put(ALL_FIELDS[i], writeDeadline());
                continue;
            }
            if (ALL_FIELDS[i].equals("Status")) {
                dataForTask.put(ALL_FIELDS[i], writeStatus());
                continue;
            }
            userText = scanner.nextLine();
            stopMethod(userText);
            dataForTask.put(ALL_FIELDS[i], userText);
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

    public void removeModelFromListStatus(Task task) {
        NEW_TASK_LIST.removeIf(taskNew -> taskNew.getId() == task.getId());
        IN_PROGRESS_TASK_LIST.removeIf(taskInProgress -> taskInProgress.getId() == task.getId());
        DONE_TASK_LIST.removeIf(taskDone -> taskDone.getId() == task.getId());
    }


    private String writeStatus() throws StopException {
        System.out.println(ANSI_PURPLE + "Выбирите один из возможных статусов : " + ANSI_RESET);
        int countStatus = 0;
        HashMap<Integer, String> statusMap = new HashMap<>();
        for (Status status : Status.values()) {
            System.out.println(ANSI_PURPLE + ++countStatus + ") " + status.getName() + ANSI_RESET);
            statusMap.put(countStatus, status.getName());
        }

        System.out.println(ANSI_PURPLE + "Выберете число статуса, который вы хотите поставить: " + ANSI_RESET);

        return statusMap.get(checkInt());

    }

    private String writeDeadline() throws StopException {
        System.out.println(ANSI_PURPLE + "Введите дату в формате '2000-01-01'" + ANSI_RESET);
        String data = scanner.nextLine();
        stopMethod(data);
        Pattern pattern = pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return data;
        } else {
            System.out.println(ANSI_RED + "Вы не правильно ввели дату" + ANSI_RESET);
            return writeDeadline();
        }

    }

    public Command consoleScanner() {
        System.out.println("Введите команду: ");
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

    public static void stopMethod(String stopText) throws StopException {
        if (stopText.equals("-stop")) {
            throw new StopException();
        }
    }

}
