package mapper;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static parser.constant.ParserConstats.ATTRIBUTE;
import static service.constants.ApplicationConstants.TAG_NAME;

public class ParserMapper {

    public static String getAttributeByIndex(int index, Element element) {
        return element.getAttribute(ATTRIBUTE[index]);

    }

    public static String getTagNameByIndex(int index, Element element) {

        NodeList nodeList = element.getElementsByTagName(TAG_NAME[index]);
        Node node = nodeList.item(0);
        if (node != null) {
            return node.getTextContent();
        } else {
            return "none";
        }

    }
}
