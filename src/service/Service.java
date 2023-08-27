package service;

import model.Task;
import org.w3c.dom.NodeList;
import parser.Parser;

import java.util.*;

public interface Service {

    void writeToFile(List<Task> allTask);

    List<Task> getAllModel();

    NodeList getNodeList(String path);


}
