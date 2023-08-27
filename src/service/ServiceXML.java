package service;

import model.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import parser.Parser;
import parser.ParserXML;

import javax.xml.XMLConstants;

import javax.xml.transform.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static parser.Parser.PATH_XML_FORMAT;

public class ServiceXML implements Service {
    Parser parser;

    public ServiceXML(ParserXML parser) {
        this.parser = parser;
    }

    @Override
    public List<Task> getAllModel() {
        Task model;
        NodeList taskList = getNodeList(PATH_XML_FORMAT);
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

    @Override
    public NodeList getNodeList(String path) {
        File inputFile = new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = getNodeListDocument(factory, inputFile);
        document.getDocumentElement().normalize();
        return document.getElementsByTagName("Task");

    }


    private void saveTransform(Document document) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(PATH_XML_FORMAT)));
        } catch (FileNotFoundException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile(List<Task> allTask) {
        Document document = parser.fromModelToElement(allTask);
        saveTransform(document);
    }

    private Document getNodeListDocument(DocumentBuilderFactory factory, File inputFile) {
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setValidating(false);
            return builder.parse(inputFile);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
