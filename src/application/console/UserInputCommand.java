package application.console;

import application.command.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static service.check.UserInputChecker.scanner;

public class UserInputCommand {
    public static Command consoleScanner() {
        System.out.println("Если Вы захотите выйти из команды которую выбрали, просто введите \"-stop\"\nВведите команду: ");
        String userCommand = scanner.nextLine();
        for (Command command : Command.values()) {
            Pattern pattern = pattern = Pattern.compile(command.getPattern());
            Matcher matcher = matcher = pattern.matcher(userCommand);
            if (matcher.find()) {
                return command;
            }
        }
        System.out.println("Не верно ввели команду, попробуйте ещё раз");
        return consoleScanner();
    }
}
