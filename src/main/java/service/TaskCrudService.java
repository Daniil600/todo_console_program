package service;

import exception.StopException;
import model.Task;
import parser.ITaskTransformer;
import service.create.UserCreateTask;
import service.delete.UserDeleteTask;
import service.edit.UserEditTask;

import java.util.List;

import static application.color.ColorConsole.ANSI_RESET;
import static application.color.ColorConsole.ANSI_YELLOW;
import static path.Paths.PATH_XML_FORMAT;
import static service.check.UserInputChecker.getTaskById;
import static service.task_list.TaskManager.TASK_LIST;

/**
 * Класс {@code TaskCrudService} реализует интерфейс {@link ITaskCrudService} и предоставляет сервисный уровень
 * для операций CRUD (create, read, update, delete) задач.
 * <p>
 * Этот класс использует {@link ITaskTransformer} для чтения и сохранения списка задач.
 * <p>
 * Дополнительно он использует следующие классы:
 * <ul>
 * <li>{@link UserEditTask} для редактирования задачи;</li>
 * <li>{@link UserDeleteTask} для удаления задачи;</li>
 * <li>{@link UserCreateTask} для создания новой задачи;</li>
 * <li>{@link service.util.UtilService} для содержания дополнительных статичных методов,которые используются разными классами сервиса;</li>
 * </ul>
 *
 * @author Daniil Sazonov
 * @see ITaskCrudService
 * @see ITaskTransformer
 * @see UserEditTask
 * @see UserDeleteTask
 * @see UserCreateTask
 * @since 1.0
 */
public class TaskCrudService implements ITaskCrudService {

    private final ITaskTransformer transformer;
    private final UserEditTask userEditTask;
    private final UserDeleteTask userDeleteTask;
    private final UserCreateTask userCreateTask;


    public TaskCrudService(ITaskTransformer transformer) {
        this.transformer = transformer;
        this.userEditTask = new UserEditTask();
        this.userDeleteTask = new UserDeleteTask();
        this.userCreateTask = new UserCreateTask();
    }

    @Override
    public List<Task> readAllTasks() {
        return transformer.readTasks(PATH_XML_FORMAT);
    }

    public Task editTask() throws StopException {
        Task task = getTaskById();
        userDeleteTask.removeModelFromAllList(task);
        task = userEditTask.editFieldInTask(task);
        userCreateTask.addModelToListStatus(task);
        System.out.println(ANSI_YELLOW + "Задача успешно изменена" + ANSI_RESET);
        return task;
    }

    public void createTask() throws StopException {
        Task task = userCreateTask.addNewTask();
        userCreateTask.addModelToListStatus(task);
        System.out.println(ANSI_YELLOW + "Задача успешна добавлена" + ANSI_RESET);
    }

    @Override
    public void deleteTask() throws StopException {
        Task task = getTaskById();
        userDeleteTask.removeModelFromAllList(task);
        System.out.println(ANSI_YELLOW + "Задача успешна удалена" + ANSI_RESET);
    }

    @Override
    public void saveTasks(List<Task> taskList, String path) {
        transformer.saveTasks(taskList, path);
    }

}
