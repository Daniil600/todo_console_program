package service.task_list;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    public static List<Task> TASK_LIST = new ArrayList<>();
    public static List<Task> NEW_TASK_LIST = new ArrayList<>();
    public static List<Task> IN_PROGRESS_TASK_LIST = new ArrayList<>();
    public static List<Task> DONE_TASK_LIST = new ArrayList<>();
}
