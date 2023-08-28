package service;

import exception.StopException;
import model.Task;

import java.util.*;

public interface Service {
    List<Task> getAllModel(String path);

    public Task editTask() throws StopException;

    public void createTask() throws StopException;

}
