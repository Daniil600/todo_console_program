package service;

import model.Task;
import model.status.Status;
import org.junit.jupiter.api.Test;
import parser.ParserXML;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ServiceXML_Test {
    private static final String PATH_DATA_EMPETY_TEST = "test/data_test/data_xml_empty_test.xml";
    private static final String PATH_DATA_TEST = "test/data_test/data_xml_test.xml";
    ParserXML parser;
    ServiceXML service;

    public ServiceXML_Test() {
        this.parser = new ParserXML();
        this.service = new ServiceXML(parser);
    }

    @Test
    public void testGetAllModelEmpty() {
        // Arrange
        List<Task> tasksExpected = new ArrayList<>();

        // Act
        List<Task> tasksActual = service.getAllModel(PATH_DATA_EMPETY_TEST);

        // Assert
        assertEquals(tasksExpected, tasksActual);
    }

    @Test
    public void testGetAllModelNotEmpty_success() {
        // Arrange
        List<Task> tasksExpected = new ArrayList<>();
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

        Task taskDown = new Task();
        taskDown.setId(3);
        taskDown.setCaption("Test");
        taskDown.setDescription("Task");
        taskDown.setPriority(10);
        taskDown.setStatus(Status.DONE);
        taskDown.setDeadline(LocalDate.of(2014, 7, 8));
        taskDown.setComplete(LocalDate.of(2023, 8, 28));

        tasksExpected.add(taskNew);
        tasksExpected.add(taskInProgress);
        tasksExpected.add(taskDown);

        // Act
        List<Task> tasksActual = service.getAllModel(PATH_DATA_TEST);

        // Assert
        assertEquals(tasksExpected, tasksActual);
    }

    @Test
    public void testGetAllModelEmpty_except() {
        // Arrange
        List<Task> tasksExpected = new ArrayList<>();

        // Act
        List<Task> tasksActual = service.getAllModel(PATH_DATA_TEST);

        // Assert
        assertNotEquals(tasksExpected, tasksActual);
    }


    @Test
    public void testGetAllModel_() {
        // Arrange
        List<Task> tasksExpected = new ArrayList<>();
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

        Task taskDown = new Task();
        taskDown.setId(3);
        taskDown.setCaption("Test");
        taskDown.setDescription("Task");
        taskDown.setPriority(10);
        taskDown.setStatus(Status.DONE);
        taskDown.setDeadline(LocalDate.of(2014, 7, 8));
        taskDown.setComplete(LocalDate.of(2023, 8, 28));

        tasksExpected.add(taskNew);
        tasksExpected.add(taskInProgress);
        tasksExpected.add(taskDown);

        // Act
        List<Task> tasksActual = service.getAllModel(PATH_DATA_EMPETY_TEST);

        // Assert
        assertNotEquals(tasksExpected, tasksActual);
    }


}
