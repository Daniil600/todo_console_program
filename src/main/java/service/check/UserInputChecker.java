package service.check;

import exception.StopException;
import model.Task;

import java.util.Optional;
import java.util.Scanner;

import static application.console.ConsoleOutput.showTask;
import static service.task_list.TaskManager.TASK_LIST;

public class UserInputChecker {
    public static Scanner scanner = new Scanner(System.in);

    public static int checkInt() throws StopException {

        System.out.println("Введите число: ");
        String idTask = scanner.nextLine();
        stopMethod(idTask);
        int id = -1;
        try {
            id = Integer.parseInt(idTask);
            return id;
        } catch (NumberFormatException e) {
            System.out.println("Вы неправильно ввели данные");
            return checkInt();
        }
    }

    public static void stopMethod(String stopText) throws StopException {
        if (stopText.equals("-stop")) {
            throw new StopException();
        }
    }

    public static Task getTaskById() throws StopException {
        System.out.println("Выберите id задачи которую хотите выбрать");
        showTask();
        int id = checkInt();
        Optional<Task> optionalTask = TASK_LIST.stream().filter(task -> task.getId() == id).findFirst();
        if (optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            System.out.println("Такого ID нет. Попробуйте ещё раз или введите \"-stop\" для выхода из этого режима");
            return getTaskById();
        }
    }
}
