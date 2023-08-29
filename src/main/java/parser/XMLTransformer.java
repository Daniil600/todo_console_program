package parser;

import model.Task;
import model.status.Status;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static mapper.ParserMapper.getAttributeByIndex;
import static mapper.ParserMapper.getTagNameByIndex;
import static mapper.TaskMapper.toLocalDate;
import static mapper.TaskMapper.toStatus;
import static parser.constant.ParserConstats.*;
import static path.Paths.PATH_XML_FORMAT;
import static service.constants.ApplicationConstants.TAG_NAME;

/**
 * Класс XMLTransformer представляет собой инструмент для чтения и сохранения задач из/в XML-файл.
 */
public class XMLTransformer implements ITaskTransformer {
    public List<Task> readTasks(String path) {
        Task model;
        NodeList taskList = getNodeList(path);
        List<Task> tasks = new ArrayList<>();

        for (int index = 0; index < taskList.getLength(); index++) {
            Node taskNode = taskList.item(index);
            if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                Element taskElement = (Element) taskNode;
                model = readTask(taskElement);
                tasks.add(model);
            }
        }
        return tasks;
    }

    private Task readTask(Element element) {
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

    @Override
    public void saveTasks(List<Task> tasks, String path) {
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

        saveTransform(document, path);
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


    public static void saveTransform(Document document, String path) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(path)));
        } catch (FileNotFoundException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public static Document getNodeListDocument(DocumentBuilderFactory factory, File inputFile) {
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setValidating(false);
            return builder.parse(inputFile);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static NodeList getNodeList(String path) {
        File inputFile = new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = getNodeListDocument(factory, inputFile);
        document.getDocumentElement().normalize();
        return document.getElementsByTagName("Task");
    }
}
