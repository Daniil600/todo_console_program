package parser;

import model.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface Parser {


    String[] ATTRIBUTE = new String[]{"id", "caption"};

    Map<String, String> TAG_NAME_WITH_VALUES = new HashMap<>();
    Map<String, String> ATTRIBUTE_WITH_VALUES = new HashMap<>();
    public static final String PATH_XML_FORMAT = "data/data_xml.xml";


    Task fromElementToModel(Element element);

    Document fromModelToElement(List<Task> tasks);

    Document newDocument();


}
