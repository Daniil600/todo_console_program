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
import static path.Paths.PATH_XML_FORMAT;
import static service.task_list.TaskManager.*;

/**
 * Класс TodoApplication представляет приложение для управления задачами.
 * Позволяет пользователю выполнять операции добавления, редактирования и удаления задач,
 * а также просматривать список всех задач и списки задач по статусам.
 * <p>
 * Команды для взаимодействия с программой:
 * <ul>
 * <li>helt - отображение возможных команд</li>
 * <li>stop - для остановки программы с сохранением данных</li>
 * <li>edit - для редактирования задачи;</li>
 * <li>delete - для удаления задачи;</li>
 * <li>add - для создания новой задачи;</li>
 * <li>list -s new - отображение задач со статусом new</li>
 * <li>list -s in progress - отображение задач со статусом progress</li>
 * <li>list -s done - отображение задач со статусом done</li>
 * <li>list - отображение всех задач</li>
 * </ul>
 */
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
                    crudService.saveTasks(TASK_LIST, PATH_XML_FORMAT);
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
        crudService.saveTasks(TASK_LIST, PATH_XML_FORMAT);

    }


}
