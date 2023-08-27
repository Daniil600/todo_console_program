package service.create;

import exception.StopException;
import model.Task;
import model.status.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static application.color.ColorConsole.*;
import static mapper.TaskMapper.toLocalDate;
import static mapper.TaskMapper.toStatus;
import static service.Service.*;
import static service.check.UserInputChecker.*;
import static service.�onstants.ApplicationConstants.ALL_FIELDS;

public class UserCreateTask {

    public static void createTask() throws StopException {
        Task task = addNewTask();
        TASK_LIST.add(task);
        addModelToListStatus(task);
        System.out.println(ANSI_YELLOW + "������ ������� ���������" + ANSI_RESET);
    }

    public static void addModelToListStatus(Task task) {
        if (task.getStatus() == Status.NEW) {
            NEW_TASK_LIST.add(task);
        } else if (task.getStatus() == Status.IN_PROGRESS) {
            IN_PROGRESS_TASK_LIST.add(task);
        } else {
            DONE_TASK_LIST.add(task);
        }
    }

    private static Task addNewTask() throws StopException {
        Map<String, String> fields = inputFieldsForModel();
        Task task = new Task();
        task.setId(Integer.parseInt(fields.get("id")));
        task.setCaption(fields.get("caption"));
        task.setDescription(fields.get("Description"));
        task.setPriority(Integer.parseInt(fields.get("Priority")));
        task.setDeadline(toLocalDate(fields.get("Deadline")));
        task.setStatus(toStatus(fields.get("Status")));
        return task;
    }

    public static String writeStatus() throws StopException {
        System.out.println(ANSI_PURPLE + "�������� ���� �� ��������� �������� : " + ANSI_RESET);
        int countStatus = 0;
        HashMap<Integer, String> statusMap = new HashMap<>();
        for (Status status : Status.values()) {
            System.out.println(ANSI_PURPLE + ++countStatus + ") " + status.getName() + ANSI_RESET);
            statusMap.put(countStatus, status.getName());
        }

        System.out.println(ANSI_PURPLE + "�������� ����� �������, ������� �� ������ ���������: " + ANSI_RESET);

        return statusMap.get(checkInt());

    }

    public static Map<String, String> inputFieldsForModel() throws StopException {
        Map<String, String> dataForTask = new HashMap<>();
        System.out.println(ANSI_CYAN + "��� ���������� ������ ������� ����� ��������� ������" + ANSI_RESET);
        String userText;

        for (String field : ALL_FIELDS) {
            if (field.equals("id")) {
                dataForTask.put(field, String.valueOf(getIdUnique()));
                continue;
            }
            System.out.println(ANSI_CYAN + "������� " + field + ANSI_RESET);
            if (field.equals("Deadline")) {
                dataForTask.put(field, inputDeadline());
                continue;
            }
            if (field.equals("Status")) {
                dataForTask.put(field, writeStatus());
                continue;
            }
            userText = scanner.nextLine();
            stopMethod(userText);
            dataForTask.put(field, userText);
        }
        return dataForTask;
    }

    public static String inputDeadline() throws StopException {
        System.out.println(ANSI_PURPLE + "������� ���� � ������� '2000-01-01'" + ANSI_RESET);
        String data = scanner.nextLine();
        stopMethod(data);
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return data;
        } else {
            System.out.println(ANSI_RED + "�� �� ��������� ����� ����" + ANSI_RESET);
            return inputDeadline();
        }

    }

    public static int getIdUnique() {
        OptionalInt optionaId = TASK_LIST.stream().map(Task::getId).mapToInt(Integer::intValue).max();
        int id = 1;
        if (optionaId.isPresent()) {
            return optionaId.getAsInt() + 1;
        } else {
            return id;
        }
    }
}
