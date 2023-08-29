package service.create;

import exception.StopException;
import model.Task;
import model.status.Status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static application.color.ColorConsole.*;
import static mapper.TaskMapper.toLocalDate;
import static mapper.TaskMapper.toStatus;
import static service.check.UserInputChecker.*;
import static service.constants.ApplicationConstants.ALL_FIELDS;
import static service.task_list.TaskManager.*;
import static service.util.UtilService.*;

public class UserCreateTask {

    public void addModelToListStatus(Task task) {
        if (task.getStatus() == Status.NEW) {
            NEW_TASK_LIST.add(task);
            TASK_LIST.add(task);
        } else if (task.getStatus() == Status.IN_PROGRESS) {
            IN_PROGRESS_TASK_LIST.add(task);
            TASK_LIST.add(task);
        } else {
            DONE_TASK_LIST.add(task);
            TASK_LIST.add(task);
        }
    }

    public Task addNewTask() throws StopException {
        Map<String, String> fields = inputFieldsForModel();
        Task task = new Task();
        task.setId(Integer.parseInt(fields.get("id")));
        task.setCaption(fields.get("caption"));
        task.setDescription(fields.get("Description"));
        task.setPriority(Integer.parseInt(fields.get("Priority")));
        task.setDeadline(toLocalDate(fields.get("Deadline")));

        String status = fields.get("Status");
        if (status.equals("done")) {
            task.setComplete(LocalDate.now());
        }
        task.setStatus(toStatus(fields.get("Status")));
        return task;
    }


    public Map<String, String> inputFieldsForModel() throws StopException {
        Map<String, String> dataForTask = new HashMap<>();
        System.out.println(ANSI_CYAN + "Для добавления нового задания введи следующие данные" + ANSI_RESET);
        String userText;

        for (String field : ALL_FIELDS) {
            if (field.equals("id")) {
                dataForTask.put(field, String.valueOf(getIdUnique()));
                continue;
            }
            System.out.println(ANSI_CYAN + "Введите " + field + ANSI_RESET);
            if (field.equals("Deadline")) {
                dataForTask.put(field, inputDeadline());
                continue;
            }
            if (field.equals("Status")) {
                String status = writeStatus();
                dataForTask.put(field, status);
                continue;
            }
            if (field.equals("Priority")) {
                String priority = String.valueOf(checkInt());
                dataForTask.put(field, priority);
                continue;
            }
            userText = scanner.nextLine();
            stopMethod(userText);
            dataForTask.put(field, userText);
        }
        return dataForTask;
    }


    public int getIdUnique() {
        OptionalInt optionaId = TASK_LIST.stream().map(Task::getId).mapToInt(Integer::intValue).max();
        int id = 1;
        if (optionaId.isPresent()) {
            return optionaId.getAsInt() + 1;
        } else {
            return id;
        }
    }
}
