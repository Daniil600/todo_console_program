package parser;

import model.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static service.ñonstants.ApplicationConstants.TAG_NAME;


public abstract class ParserAbstract {


    public static final String[] ATTRIBUTE = new String[]{"id", "caption"};

    public static final Map<String, String> TAG_NAME_WITH_VALUES = new HashMap<>();
    public static final Map<String, String> ATTRIBUTE_WITH_VALUES = new HashMap<>();


    public abstract Task fromElementToModel(Element element);

    public abstract Document fromModelToElement(List<Task> tasks);

    public abstract Document newDocument();

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
