package parser;

import model.Task;
import model.status.Status;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static service.Service.*;

public abstract class ParserAbstract {


    public static final String[] ATTRIBUTE = new String[]{"id", "caption"};

    public static final Map<String, String> TAG_NAME_WITH_VALUES = new HashMap<>();
    public static final Map<String, String> ATTRIBUTE_WITH_VALUES = new HashMap<>();


    public abstract Task fromElementToModel(Element element);

    public abstract Document fromModelToElement(List<Task> tasks);
    public abstract Document newDocument();

    public LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    public String fromLocalDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public Status toStatus(String statusString) {
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

    public String fromStatus(Status status) {
        return status.getName();
    }

    protected static String getAttributeByIndex(int index, Element element) {
        return element.getAttribute(ATTRIBUTE[index]);

    }

    protected static String getTagNameByIndex(int index, Element element) {

        NodeList nodeList = element.getElementsByTagName(TAG_NAME[index]);
        Node node = nodeList.item(0);
        if (node != null) {
            return node.getTextContent();
        } else {
            return "none";
        }

    }
}
