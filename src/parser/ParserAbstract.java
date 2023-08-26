package parser;

import model.Task;
import model.status.Status;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ParserAbstract {

    public static final String[] ALL_FIELDS = new String[]{"id", "caption","Description", "Priority", "Deadline", "Status"};
    public static final String[] TAG_NAME = new String[]{"Description", "Priority", "Deadline", "Status", "Complete"};
    public static final String[] ATTRIBUTE = new String[]{"id", "caption"};

    public static final Map<String, String> TAG_NAME_WITH_VALUES = new HashMap<>();
    public static final Map<String, String> ATTRIBUTE_WITH_VALUES = new HashMap<>();


    public abstract Task fromElementToModel(Element element);
    public abstract Document fromModelToElement(List<Task> tasks);

    public static LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    public static String fromLocalDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public static Status toStatus(String statusString) {
        Status status = Status.valueOf(statusString.toUpperCase());
        if (status != null) {
            return status;
        } else {
            System.out.println("No matching enum constant found for: " + statusString);
            throw new IllegalArgumentException("No matching enum constant found for: " + statusString);
        }
    }
    public static String fromStatus(Status status) {
        return status.getName();
    }

    protected static String getAttributeByIndex(int index, Element element) {
        return element.getAttribute(ATTRIBUTE[index]);

    }

    protected static String getTagNameByIndex(int index, Element element) {
        String data = element.getElementsByTagName(TAG_NAME[index]).item(0).getTextContent();
        return data;

    }
}
