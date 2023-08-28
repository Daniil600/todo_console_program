package service;

import model.Task;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import parser.ParserXML;

import java.util.ArrayList;
import java.util.List;

import static parser.util.ParserUtil.getNodeList;

public class ServiceXML implements Service {

    ParserXML parser;

    public ServiceXML(ParserXML parser) {
        this.parser = parser;
    }

    @Override
    public List<Task> getAllModel(String path) {
        Task model;
        NodeList taskList = getNodeList(path);
        List<Task> tasks = new ArrayList<>();

        for (int index = 0; index < taskList.getLength(); index++) {
            Node taskNode = taskList.item(index);
            if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                Element taskElement = (Element) taskNode;
                model = parser.fromElementToModel(taskElement);
                tasks.add(model);
            }
        }
        return tasks;
    }


}
