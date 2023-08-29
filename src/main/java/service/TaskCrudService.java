package service;

import exception.StopException;
import model.Task;
import parser.ITaskTransformer;
import service.delete.UserDeleteTask;
import service.edit.UserEditTask;

import java.util.List;

import static application.color.ColorConsole.ANSI_RESET;
import static application.color.ColorConsole.ANSI_YELLOW;
import static service.check.UserInputChecker.getTaskById;
import static service.create.UserCreateTask.addModelToListStatus;
import static service.create.UserCreateTask.addNewTask;
import static service.task_list.TaskManager.TASK_LIST;

public class TaskCrudService implements ITaskCrudService {

    private final ITaskTransformer transformer;
    private final UserEditTask userEditTask;
    private final UserDeleteTask userDeleteTask;


    public TaskCrudService(ITaskTransformer transformer) {
        this.transformer = transformer;
        userEditTask = new UserEditTask();
        userDeleteTask = new UserDeleteTask();
    }

    @Override
    public List<Task> readAllTasks() {
        return transformer.readTasks();
    }

    public Task editTask() throws StopException {
        Task task = getTaskById();
        userDeleteTask.removeModelFromAllList(task);
        task = userEditTask.editFieldInTask(task);
        addModelToListStatus(task);
        System.out.println(ANSI_YELLOW + "Задача успешно изменена" + ANSI_RESET);
        return task;
    }

    public void createTask() throws StopException {
        Task task = addNewTask();
        TASK_LIST.add(task);
        addModelToListStatus(task);
        System.out.println(ANSI_YELLOW + "Задача успешна добавлена" + ANSI_RESET);
    }

    @Override
    public void deleteTask() throws StopException {
        Task task = getTaskById();
        userDeleteTask.removeModelFromAllList(task);
        System.out.println(ANSI_YELLOW + "Задача успешна удалена" + ANSI_RESET);
    }

    @Override
    public void saveTasks(List<Task> taskList) {
        transformer.saveTasks(taskList);
    }

}
