package service;

import exception.StopException;
import model.Task;
import parser.ITaskTransformer;

import java.util.List;

import static application.color.ColorConsole.ANSI_RESET;
import static application.color.ColorConsole.ANSI_YELLOW;
import static service.check.UserInputChecker.getTaskById;
import static service.create.UserCreateTask.addModelToListStatus;
import static service.create.UserCreateTask.addNewTask;
import static service.delete.UserDeleteTask.removeModelFromAllList;
import static service.edit.UserEditTask.editFieldInTask;
import static service.edit.UserEditTask.removeModelFromListStatus;
import static service.task_list.TaskManager.TASK_LIST;

public class TaskCrudService implements ITaskCrudService {

    private final ITaskTransformer transformer;

    public TaskCrudService(ITaskTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public List<Task> readAllTasks() {
        return transformer.readTasks();
    }

    public Task editTask() throws StopException {
        Task task = getTaskById();
        removeModelFromListStatus(task);
        addModelToListStatus(task);
        System.out.println(ANSI_YELLOW + "Задача успешна добавлена" + ANSI_RESET);
        editFieldInTask(task);
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
        removeModelFromAllList(task);
        System.out.println(ANSI_YELLOW + "Задача успешна удалена" + ANSI_RESET);
    }

    @Override
    public void saveTasks(List<Task> taskList) {
        transformer.saveTasks(taskList);
    }

}
