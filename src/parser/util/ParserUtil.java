package parser.util;

import model.Task;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static parser.ParserXML.fromModelToElement;
import static path.Paths.PATH_XML_FORMAT;

public class ParserUtil {


    public static void saveTransform(Document document) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(PATH_XML_FORMAT)));
        } catch (FileNotFoundException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }


    public static void writeToFile(List<Task> allTask) {
        Document document = fromModelToElement(allTask);
        saveTransform(document);
    }

    public static Document getNodeListDocument(DocumentBuilderFactory factory, File inputFile) {
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setValidating(false);
            return builder.parse(inputFile);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static NodeList getNodeList(String path) {
        File inputFile = new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = getNodeListDocument(factory, inputFile);
        document.getDocumentElement().normalize();
        return document.getElementsByTagName("Task");

    }
}
