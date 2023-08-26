package model.status;

public enum Status {
    NEW("new"),
    IN_PROGRESS("in progress"),
    DONE("done");

    String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
