package application;

import application.command.Command;
import exception.StopException;
import model.Task;
import model.status.Status;
import parser.XMLTransformer;
import service.ITaskCrudService;
import service.TaskCrudService;

import static application.console.ConsoleOutput.*;
import static application.console.UserInputCommand.consoleScanner;
import static service.task_list.TaskManager.*;

public class TodoApplication {
    private final ITaskCrudService crudService;

    public TodoApplication() {
        this.crudService = new TaskCrudService(new XMLTransformer());
    }

    private void init() {
        TASK_LIST = crudService.readAllTasks();


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


        boolean flag = true;

        while (flag) {
            Command userCommand = consoleScanner();
            switch (userCommand) {
                case STOP:
                    crudService.saveTasks(TASK_LIST);
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
                    try {
                        crudService.createTask();
                        break;
                    } catch (StopException e) {
                        System.out.println("Вы вышли из добавления новой задачи");
                        continue;
                    }
                case EDIT:
                    try {
                        crudService.editTask();
                        break;
                    } catch (StopException e) {
                        System.out.println("Вы вышли из изменения задачи");
                        break;
                    }
                case DELETE:
                    try {
                        crudService.deleteTask();
                        break;
                    } catch (StopException e) {
                        System.out.println("Вы вышли из изменения задачи");
                        break;
                    }
            }
        }
        crudService.saveTasks(TASK_LIST);

    }


}