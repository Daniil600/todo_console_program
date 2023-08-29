package parser;

import model.Task;

import java.util.List;

public interface ITaskTransformer {
    List<Task> readTasks(String path);

    void saveTasks(List<Task> tasks, String path);
}
