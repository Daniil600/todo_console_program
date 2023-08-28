package application.command;

public enum Command {
    HELP("help", "����� ������� �� ��������", "help"),
    LIST("list", "����� ���� �����", "^list$"),
    LIST_NEW("list -s new", "����� ����� �����", "^list -s new$"),
    LIST_IN_PROGRESS("list -s in progress", "����� ����� � ������", "^list -s in progress$"),
    LIST_DONE("list -s done", "����� ����������� �����", "^list -s done$"),
    STOP("-stop", "������������� ������ ���������", "^-stop$"),
    ADD("-add", "���������� ����� ������", "^-add$"),
    EDIT("-edit", "��������� ������", "^-edit$"),
    DELETE("-delete", "�������� ������", "^-delete$");

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
