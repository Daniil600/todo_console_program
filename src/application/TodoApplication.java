package application;

import application.command.Command;
import exception.StopException;
import model.Task;
import model.status.Status;
import parser.ParserXML;
import service.Service;
import service.ServiceXML;

import static application.console.ConsoleOutput.*;
import static application.console.UserInputCommand.consoleScanner;
import static parser.util.ParserUtil.writeToFile;
import static path.Paths.PATH_XML_FORMAT;
import static service.create.UserCreateTask.createTask;
import static service.edit.UserEditTask.editTask;
import static service.task_list.TaskManager.*;

public class TodoApplication {
    Service service;

    public TodoApplication() {
        this.service = new ServiceXML(new ParserXML());
    }

    private void init() {

        TASK_LIST = service.getAllModel(PATH_XML_FORMAT);


        for (Task task : TASK_LIST) {
            if (task.getStatus() == Status.NEW) {
                NEW_TASK_LIST.add(task);
            }
            if (task.getStatus() == Status.IN_PROGRESS) {
                IN_PROGRESS_TASK_LIST.add(task);
            }
            if (task.getStatus() == Status.DONE) {
                DONE_TASK_LIST.add(task);
            }
        }

    }

    public void application() {
        init();

        welcomeMessage();

        showTask();

        showConsoleCommand();

        writeToFile(TASK_LIST);

        boolean flag = true;

        while (flag) {
            Command userCommand = consoleScanner();
            switch (userCommand) {
                case STOP:
                    flag = false;
                    break;
                case HELP:
                    showConsoleCommand();
                    continue;
                case LIST:
                    showTask();
                    continue;
                case LIST_NEW:
                    showNewTask();
                    continue;
                case LIST_IN_PROGRESS:
                    showInProgressTask();
                    continue;
                case LIST_DONE:
                    showDownTask();
                    continue;
                case ADD:
                    try {
                        createTask();
                        break;
                    } catch (StopException e) {
                        System.out.println("Вы вышли из добавления новой задачи");
                        continue;
                    }
                case EDIT:
                    try {
                        editTask();
                        break;
                    } catch (StopException e) {
                        System.out.println("Вы вышли из изменения задачи");
                        break;
                    }
            }
        }
        writeToFile(TASK_LIST);

    }


}
