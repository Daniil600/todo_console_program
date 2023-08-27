package service;

import exception.StopException;
import model.Task;
import model.status.Status;
import org.w3c.dom.NodeList;
import parser.ParserAbstract;

import java.time.LocalDate;
import java.util.*;

import static application.console.ConsoleOutput.showTask;
import static mapper.TaskMapper.toLocalDate;
import static mapper.TaskMapper.toStatus;
import static service.check.UserInputChecker.checkInt;
import static service.create.UserCreateTask.*;
import static service.ñonstants.ApplicationConstants.FIELDS_FOR_EDIT;

public abstract class Service {
    public static List<Task> TASK_LIST = new ArrayList<>();
    public static List<Task> NEW_TASK_LIST = new ArrayList<>();
    public static List<Task> IN_PROGRESS_TASK_LIST = new ArrayList<>();
    public static List<Task> DONE_TASK_LIST = new ArrayList<>();


    protected ParserAbstract parser;
    protected Scanner scanner;

    public Service(ParserAbstract parser) {
        this.parser = parser;
        this.scanner = new Scanner(System.in);
    }

    public static final String PATH_XML_FORMAT = "data/data_xml.xml";

    public abstract void writeToFile(List<Task> allTask);

    public abstract List<Task> getAllModel();

    public abstract NodeList getNodeList(String path);

    public abstract void createNewFile();



}
