package parser;

import model.Task;
import model.status.Status;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserConstatsXML_Test {

    @Test
    public void testFromElementToModel() {
        // Arrange
        Element element = createSampleElement();

        // Act
        Task task = XMLTransformer.fromElementToModel(element);

        // Assert
        assertNotNull(task);
        assertEquals(1, task.getId());
        assertEquals("Test", task.getCaption());
        assertEquals("Task", task.getDescription());
        assertEquals(10, task.getPriority());
        assertEquals(LocalDate.of(2000, 8, 11), task.getDeadline());
        assertEquals(Status.NEW, task.getStatus());
        assertNull(task.getComplete());
    }

    @Test
    public void testFromModelToElement() {
        // Arrange
        List<Task> tasks = createSampleTasks();
        System.out.println(tasks);

        // Act
        Document document = XMLTransformer.fromModelToElement(tasks);

        // Assert
        assertNotNull(document);
        Element rootElement = document.getDocumentElement();
        assertEquals("ToDoList", rootElement.getTagName());
        assertEquals(tasks.size(), rootElement.getChildNodes().getLength());
    }

    private Element createSampleElement() {
        Document document = XMLTransformer.getDocument();
        Element element = document.createElement("Task");
        element.setAttribute("id", "1");
        element.setAttribute("caption", "Test");

        Element elementDescription = document.createElement("Description");
        Element elementPriority = document.createElement("Priority");
        Element elementDeadline = document.createElement("Deadline");
        Element elementStatus = document.createElement("Status");

        Text textelementDescription = document.createTextNode("Task");
        Text textelementPriority = document.createTextNode(String.valueOf(10));
        Text textelementDeadline = document.createTextNode(String.valueOf(LocalDate.of(2000, 8, 11)));
        Text textelementStatus = document.createTextNode(String.valueOf(Status.NEW.getName()));

        elementDescription.appendChild(textelementDescription);
        elementPriority.appendChild(textelementPriority);
        elementDeadline.appendChild(textelementDeadline);
        elementStatus.appendChild(textelementStatus);

        element.appendChild(elementDescription);
        element.appendChild(elementPriority);
        element.appendChild(elementDeadline);
        element.appendChild(elementStatus);

        return element;
    }

    private List<Task> createSampleTasks() {
        List<Task> tasks = new ArrayList<>();

        Task taskNew = new Task();
        taskNew.setId(1);
        taskNew.setCaption("Test");
        taskNew.setDescription("Task");
        taskNew.setPriority(10);
        taskNew.setStatus(Status.NEW);
        taskNew.setDeadline(LocalDate.of(2014, 7, 8));

        Task taskInProgress = new Task();
        taskInProgress.setId(2);
        taskInProgress.setCaption("Test");
        taskInProgress.setDescription("Task");
        taskInProgress.setPriority(10);
        taskInProgress.setStatus(Status.IN_PROGRESS);
        taskInProgress.setDeadline(LocalDate.of(2014, 7, 8));

        Task taskDone = new Task();
        taskDone.setId(3);
        taskDone.setCaption("Test");
        taskDone.setDescription("Task");
        taskDone.setPriority(10);
        taskDone.setStatus(Status.DONE);
        taskDone.setDeadline(LocalDate.of(2014, 7, 8));
        taskDone.setComplete(LocalDate.of(2023, 8, 28));

        tasks.add(taskNew);
        tasks.add(taskInProgress);
        tasks.add(taskDone);

        return tasks;
    }
}
