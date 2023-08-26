package model;

import java.util.List;

public class ToDoList {
    private List<Task> tasks;

    public ToDoList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
