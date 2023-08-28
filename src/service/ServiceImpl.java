package service;

import exception.StopException;
import model.Task;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import parser.ParserXML;

import java.util.ArrayList;
import java.util.List;

import static application.color.ColorConsole.ANSI_RESET;
import static application.color.ColorConsole.ANSI_YELLOW;
import static parser.util.ParserUtil.getNodeList;
import static service.create.UserCreateTask.addModelToListStatus;
import static service.create.UserCreateTask.addNewTask;
import static service.edit.UserEditTask.*;
import static service.task_list.TaskManager.TASK_LIST;

public class ServiceImpl implements Service {

    ParserXML parser;

    public ServiceImpl(ParserXML parser) {
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

    public Task editTask() throws StopException {
        Task task = getTaskById();
        removeModelFromListStatus(task);
        addModelToListStatus(task);
        System.out.println(ANSI_YELLOW + "Задача успешна добавлена" + ANSI_RESET);
        editFieldInTask(task);
        return task;
    }

    public void createTask() throws StopException {
        Task task = addNewTask();
        TASK_LIST.add(task);
        addModelToListStatus(task);
        System.out.println(ANSI_YELLOW + "Задача успешна добавлена" + ANSI_RESET);
    }

}
