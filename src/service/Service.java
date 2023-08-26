package service;

import model.Task;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

public abstract class Service {

    public static final String XML_FORMAT = "data/data_xml.xml";
    public abstract void writeToFile(List<Task> allTask);
    public abstract List<Task> getAllModel();

    public abstract NodeList getNodeList(String path);

    public Task getModel(String[] fields){
        Task task = new Task();

        return task;
    }



}
