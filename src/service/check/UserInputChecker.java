package service.check;

import exception.StopException;

import java.util.Scanner;

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
}
