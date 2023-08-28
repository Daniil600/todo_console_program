package application.command;

public enum Command {
    HELP("help", "Вывод справки по командам", "help"),
    LIST("list", "Вывод всех задач", "^list$"),
    LIST_NEW("list -s new", "Вывод новых задач", "^list -s new$"),
    LIST_IN_PROGRESS("list -s in progress", "Вывод задач в работе", "^list -s in progress$"),
    LIST_DONE("list -s done", "Вывод выполненных задач", "^list -s done$"),
    STOP("-stop", "Останавливает работу программы", "^-stop$"),
    ADD("-add", "Добавление новой задачи", "^-add$"),
    EDIT("-edit", "Изменение задачи", "^-edit$"),
    DELETE("-delete", "удаление задачи", "^-delete$");

    private String commandName;
    private String discription;
    private String pattern;

    Command(String commandName, String discription, String pattern) {
        this.commandName = commandName;
        this.discription = discription;
        this.pattern = pattern;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getPattern() {
        return pattern;
    }

    public String getDiscription() {
        return discription;
    }

}
