package service.util;

import exception.StopException;
import model.status.Status;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static application.color.ColorConsole.*;
import static application.color.ColorConsole.ANSI_RESET;
import static service.check.UserInputChecker.*;

public class UtilService {

    public static String writeStatus() throws StopException {
        System.out.println(ANSI_PURPLE + "Выбирите один из возможных статусов : " + ANSI_RESET);
        int countStatus = 0;
        HashMap<Integer, String> statusMap = new HashMap<>();
        for (Status status : Status.values()) {
            System.out.println(ANSI_PURPLE + ++countStatus + ") " + status.getName() + ANSI_RESET);
            statusMap.put(countStatus, status.getName());
        }

        System.out.println(ANSI_PURPLE + "Выберете число статуса, который вы хотите поставить: " + ANSI_RESET);

        return statusMap.get(checkInt());

    }
    public static String inputDeadline() throws StopException {
        System.out.println(ANSI_PURPLE + "Введите дату в формате '2000-01-01'" + ANSI_RESET);
        String data = scanner.nextLine();
        stopMethod(data);
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return data;
        } else {
            System.out.println(ANSI_RED + "Вы не правильно ввели дату" + ANSI_RESET);
            return inputDeadline();
        }
    }
}
