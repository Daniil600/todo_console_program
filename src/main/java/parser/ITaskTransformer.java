package parser;

import model.Task;

import java.util.List;

public interface ITaskTransformer {
    List<Task> readTasks();

    void saveTasks(List<Task> tasks);
}
