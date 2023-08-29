package mapper;

import model.status.Status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

public class TaskMapper {
    public static LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    public static Status toStatus(String statusString) {
        Optional<Status> status = Arrays.stream(Status.values()).
                filter(statusName -> statusName.getName().
                        equals(statusString)).findFirst();

        if (status.isPresent()) {
            return status.get();
        } else {
            System.out.println("No matching enum constant found for: " + statusString);
            throw new IllegalArgumentException("No matching enum constant found for: " + statusString);
        }

    }

}
