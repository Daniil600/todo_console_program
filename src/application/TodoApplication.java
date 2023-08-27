package application;

import application.command.Command;
import exception.StopException;
import model.Task;
import model.status.Status;

import parser.ParserAbstract;
import service.Service;

import static application.color.ColorConsole.ANSI_RESET;
import static application.color.ColorConsole.ANSI_YELLOW;
import static service.Service.*;

import service.ServiceXML;

public class TodoApplication {

    private final Service service;

    public TodoApplication() {
        this.service = new ServiceXML();
    }

    private void init() {
        try {
            TASK_LIST = service.getAllModel();
        } catch (RuntimeException e) {
            service.createNewFile();
            init();
        }

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
                    try {
                        Task task = service.addNewTask();
                        TASK_LIST.add(task);
                        service.addModelToListStatus(task);
                        System.out.println(ANSI_YELLOW + "Задача успешна добавлена" + ANSI_RESET);
                        break;
                    } catch (StopException e) {
                        System.out.println("Вы вышли из добавления новой задачи");
                        continue;
                    }
                case EDIT:
                    try {
                        Task task = service.editTask();
                        service.removeModelFromListStatus(task);
                        service.addModelToListStatus(task);
                        System.out.println(ANSI_YELLOW + "Задача успешна добавлена" + ANSI_RESET);
                        break;
                    } catch (StopException e) {
                        System.out.println("Вы вышли из изменения задачи");
                        break;
                    }
            }
        }
        service.writeToFile(TASK_LIST);

    }


}
