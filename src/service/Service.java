package service;

import exception.StopException;
import model.Task;

import java.util.*;

public interface Service {
    List<Task> getAllModel(String path);

    Task editTask() throws StopException;

    void createTask() throws StopException;

    void deleteTask() throws StopException;
}
