package service;

import model.Task;
import model.status.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import parser.ITaskTransformer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskCrudServiceTest {
    private TaskCrudService taskCrudService;
    private ITaskTransformer transformer;

    private static final String PATH_XML_TEST = "src/test/resources/data_test/data_xml_service_test.xml";
    private List<Task> tasks;

    @BeforeEach
    public void setUp() {
        transformer = mock(ITaskTransformer.class);
        tasks = createDummyTasks();

        taskCrudService = new TaskCrudService(transformer);
    }

    @Test
    public void testReadAllTasks() {
        // Создание фиктивных задач
        List<Task> tasks = new ArrayList<>();


        // Заглушка для метода чтения задач из трансформера
        when(transformer.readTasks(any(String.class))).thenReturn(tasks);

        // Вызов метода и проверка результатов
        List<Task> result = taskCrudService.readAllTasks();
        assertEquals(tasks, result);
    }


    @Test
    public void testSaveTasks() {
        taskCrudService.saveTasks(tasks, PATH_XML_TEST);
        Mockito.verify(transformer).saveTasks(tasks, PATH_XML_TEST);
    }

    private List<Task> createDummyTasks() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1, "Task 1", "Task description 1", 1, LocalDate.now(), Status.IN_PROGRESS);
        Task task2 = new Task(2, "Task 2", "Task description 2", 2, LocalDate.now(), Status.DONE, LocalDate.of(2023, 8, 29));
        Task task3 = new Task(3, "Task 3", "Task description 3", 3, LocalDate.now(), Status.NEW);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        return tasks;
    }
}