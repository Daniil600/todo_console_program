package parser;

import model.Task;
import model.status.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.XMLTransformer;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XMLTransformerTest {
    private XMLTransformer xmlTransformer;
    private List<Task> tasks;
    private final static String PATH_XML_LIST_TEST = "src/test/resources/data_test/data_xml_transformer_test.xml";

    @BeforeEach
    public void setUp() {
        xmlTransformer = new XMLTransformer();
        tasks = createDummyTasks();
    }

    @Test
    public void testReadTasks() {
        List<Task> result = xmlTransformer.readTasks(PATH_XML_LIST_TEST);

        assertEquals(tasks.size(), result.size());
        assertTrue(result.containsAll(tasks));
    }

    @Test
    public void testSaveTasks() {

        // Сохраняем задачи в файл
        xmlTransformer.saveTasks(tasks, PATH_XML_LIST_TEST);

        // Читаем задачи из файла
        List<Task> result = xmlTransformer.readTasks(PATH_XML_LIST_TEST);

        // Удаляем созданный файл
        File file = new File(PATH_XML_LIST_TEST);
        file.delete();

        assertEquals(tasks.size(), result.size());
        assertTrue(result.containsAll(tasks));
    }

    private List<Task> createDummyTasks() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1, "Task 1", "Task description 1", 1, LocalDate.now(), Status.IN_PROGRESS);
        Task task2 = new Task(2, "Task 2", "Task description 2", 2, LocalDate.now(), Status.DONE, LocalDate.of(2023,8,29));
        Task task3 = new Task(3, "Task 3", "Task description 3", 3, LocalDate.now(), Status.NEW);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        return tasks;
    }
}