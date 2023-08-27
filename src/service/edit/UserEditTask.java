package service.edit;

import exception.StopException;
import model.Task;
import model.status.Status;

import java.time.LocalDate;
import java.util.Optional;

import static application.color.ColorConsole.ANSI_RESET;
import static application.color.ColorConsole.ANSI_YELLOW;
import static application.console.ConsoleOutput.showTask;
import static mapper.TaskMapper.toLocalDate;
import static mapper.TaskMapper.toStatus;
import static service.Service.*;
import static service.check.UserInputChecker.checkInt;
import static service.check.UserInputChecker.scanner;
import static service.create.UserCreateTask.*;
import static service.�onstants.ApplicationConstants.FIELDS_FOR_EDIT;

public class UserEditTask {

    public static Task editTask() throws StopException {
        Task task = getTaskById();
        removeModelFromListStatus(task);
        addModelToListStatus(task);
        System.out.println(ANSI_YELLOW + "������ ������� ���������" + ANSI_RESET);
        editFieldInTask(task);
        return task;
    }

    public static void removeModelFromListStatus(Task task) {
        NEW_TASK_LIST.removeIf(taskNew -> taskNew.getId() == task.getId());
        IN_PROGRESS_TASK_LIST.removeIf(taskInProgress -> taskInProgress.getId() == task.getId());
        DONE_TASK_LIST.removeIf(taskDone -> taskDone.getId() == task.getId());
    }



    public static Task editFieldInTask(Task task) throws StopException {
        System.out.println("��� � ������ �� ������ ��������?");
        int count = 1;
        for (String field : FIELDS_FOR_EDIT) {
            System.out.println(count++ + ") " + field);
        }
        int idField = checkInt();
        if (FIELDS_FOR_EDIT.length < idField) {
            System.out.println("������ ����� ��� ����� id �����, ���������� ��� ���");
            editFieldInTask(task);
        }
        switch (idField) {
            case 1 -> {
                System.out.println("������� ���������");
                task.setCaption(scanner.nextLine());
            }
            case 2 -> {
                System.out.println("������� ��������");
                task.setDescription(scanner.nextLine());
            }
            case 3 -> {
                System.out.println("������� ��������� ������: ");
                task.setPriority(checkInt());
            }
            case 4 -> {
                System.out.println("������� ���� ��������");
                task.setDeadline(toLocalDate(inputDeadline()));
            }
            case 5 -> {
                System.out.println("������� ������");
                task.setStatus(toStatus(writeStatus()));
                if (task.getStatus() == Status.DONE) {
                    task.setComplete(LocalDate.now());
                }
            }
        }
        return task;
    }

    public static Task getTaskById() throws StopException {
        System.out.println("�������� id ������ ������� ������ ��������");
        showTask();
        int id = checkInt();
        Optional<Task> optionalTask = TASK_LIST.stream().filter(task -> task.getId() == id).findFirst();
        if (optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            System.out.println("������ ID ���. ���������� ��� ��� ��� ������� \'-stop\' ��� ������ �� ����� ������");
            return getTaskById();
        }
    }
}
