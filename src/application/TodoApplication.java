package application;

import application.command.Command;
import model.Task;
import model.status.Status;

import service.Service;

import static application.color.ColorConsole.ANSI_RESET;
import static application.color.ColorConsole.ANSI_YELLOW;
import static service.Service.*;

import service.ServiceXML;

public class TodoApplication {

    Service service;

    public TodoApplication() {
        this.service = new ServiceXML();
    }

    private void init() {

        TASK_LIST = service.getAllModel();

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

        service.welcomeMessage();

        service.showTask();

        service.showConsoleCommand();

        service.writeToFile(TASK_LIST);

        boolean flag = true;

        while (flag) {
            Command userCommand = service.consoleScanner();
            switch (userCommand) {
                case STOP:
                    flag = false;
                    break;
                case HELP:
                    service.showConsoleCommand();
                    continue;
                case LIST:
                    service.showTask();
                    continue;
                case LIST_NEW:
                    service.showNewTask();
                    continue;
                case LIST_IN_PROGRESS:
                    service.showInProgressTask();
                    continue;
                case LIST_DONE:
                    service.showDownTask();
                    continue;
                case ADD:
                    Task task = service.addNewTask();
                    TASK_LIST.add(task);
                    service.addModelToListStatus(task);
                    System.out.println(ANSI_YELLOW + "������ ������� ���������" + ANSI_RESET);
                    break;
                case EDIT:

            }
        }
        service.writeToFile(TASK_LIST);

    }


}
