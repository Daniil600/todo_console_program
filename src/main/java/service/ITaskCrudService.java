package service;

import exception.StopException;
import model.Task;

import java.util.List;

public interface ITaskCrudService {
    
    List<Task> readAllTasks();

    Task editTask() throws StopException;

    void createTask() throws StopException;

    void deleteTask() throws StopException;

    void saveTasks(List<Task> taskList);
}
