package model;

import model.status.Status;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
    private int id;
    private String caption;
    private String description;
    private int priority;
    private LocalDate deadline;
    private Status status;
    private LocalDate complete;

    public Task() {
    }

    public Task(int id, String caption, String description, int priority, LocalDate deadline, Status status, LocalDate complete) {
        this.id = id;
        this.caption = caption;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
        this.complete = complete;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getComplete() {
        return complete;
    }

    public void setComplete(LocalDate complete) {
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && priority == task.priority && Objects.equals(caption, task.caption) && Objects.equals(description, task.description) && Objects.equals(deadline, task.deadline) && status == task.status && Objects.equals(complete, task.complete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caption, description, priority, deadline, status, complete);
    }

    @Override
    public String toString() {
        return status == Status.DONE ? id
                + " | "
                + caption
                + " | "
                + description
                + " | "
                + priority
                + " | "
                + deadline
                + " | "
                + status
                + " | "
                + complete
                :
                id
                        + " | "
                        + caption
                        + " | "
                        + description
                        + " | "
                        + priority
                        + " | "
                        + deadline
                        + " | "
                        + status;
    }
}
