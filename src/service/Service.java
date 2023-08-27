package service;

import model.Task;
import org.w3c.dom.NodeList;
import parser.Parser;

import java.util.*;

public interface Service {

    public abstract void writeToFile(List<Task> allTask);

    public abstract List<Task> getAllModel();

    public abstract NodeList getNodeList(String path);

    public abstract void createNewFile();



}
