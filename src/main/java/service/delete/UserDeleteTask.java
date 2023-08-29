package service.delete;

import model.Task;

import static service.task_list.TaskManager.*;

public class UserDeleteTask {
    public void removeModelFromAllList(Task task) {
        NEW_TASK_LIST.removeIf(taskNew -> taskNew.getId() == task.getId());
        IN_PROGRESS_TASK_LIST.removeIf(taskInProgress -> taskInProgress.getId() == task.getId());
        DONE_TASK_LIST.removeIf(taskDone -> taskDone.getId() == task.getId());
        TASK_LIST.removeIf(taskList -> taskList.getId() == task.getId());
    }
}
