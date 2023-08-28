package parser;

import model.Task;
import model.status.Status;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

import static mapper.ParserMapper.getAttributeByIndex;
import static mapper.ParserMapper.getTagNameByIndex;
import static mapper.TaskMapper.toLocalDate;
import static mapper.TaskMapper.toStatus;
import static parser.constant.ParserConstats.*;
import static service.ñonstants.ApplicationConstants.TAG_NAME;


public class ParserXML {

    public static Task fromElementToModel(Element element) {
        for (int i = 0; i < ATTRIBUTE.length; i++) {
            ATTRIBUTE_WITH_VALUES.put(ATTRIBUTE[i], getAttributeByIndex(i, element));
        }
        for (int i = 0; i < TAG_NAME.length; i++) {
            TAG_NAME_WITH_VALUES.put(TAG_NAME[i], getTagNameByIndex(i, element));
        }
        Task model = new Task();
        model.setId(Integer.parseInt(ATTRIBUTE_WITH_VALUES.get("id")));
        model.setCaption(ATTRIBUTE_WITH_VALUES.get("caption"));
        model.setDescription(TAG_NAME_WITH_VALUES.get("Description"));
        model.setPriority(Integer.parseInt(TAG_NAME_WITH_VALUES.get("Priority")));
        model.setDeadline(toLocalDate(TAG_NAME_WITH_VALUES.get("Deadline")));
        model.setStatus(toStatus(TAG_NAME_WITH_VALUES.get("Status")));
        if (model.getStatus() == Status.DONE) {
            model.setComplete(toLocalDate(TAG_NAME_WITH_VALUES.get("Complete")));
        }
        return model;
    }


    public static Document fromModelToElement(List<Task> tasks) {
        Document document = getDocument();
        Element elementToDoList = document.createElement("ToDoList");

        for (Task task : tasks) {
            Element elementTask = document.createElement("Task");
            elementTask.setAttribute("id", String.valueOf(task.getId()));
            elementTask.setAttribute("caption", String.valueOf(task.getCaption()));

            Element elementDescription = document.createElement("Description");
            Element elementPriority = document.createElement("Priority");
            Element elementDeadline = document.createElement("Deadline");
            Element elementStatus = document.createElement("Status");


            Text textelementDescription = document.createTextNode(task.getDescription());
            Text textelementPriority = document.createTextNode(String.valueOf(task.getPriority()));
            Text textelementDeadline = document.createTextNode(String.valueOf(task.getDeadline()));
            Text textelementStatus = document.createTextNode(String.valueOf(task.getStatus().getName()));


            elementDescription.appendChild(textelementDescription);
            elementPriority.appendChild(textelementPriority);
            elementDeadline.appendChild(textelementDeadline);
            elementStatus.appendChild(textelementStatus);


            elementTask.appendChild(elementDescription);
            elementTask.appendChild(elementPriority);
            elementTask.appendChild(elementDeadline);
            elementTask.appendChild(elementStatus);

            if (task.getStatus() == Status.DONE) {
                Element elementComplete = document.createElement("Complete");
                Text textElementComplete = document.createTextNode(String.valueOf(task.getComplete()));
                elementComplete.appendChild(textElementComplete);
                elementTask.appendChild(elementComplete);
            }


            elementToDoList.appendChild(elementTask);
        }
        document.appendChild(elementToDoList);

        return document;

    }

    public static Document getDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        return builder.newDocument();
    }


}
