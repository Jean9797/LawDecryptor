import java.util.List;

/**
 * Interface for interacting with object structure of the act
 *
 * @author Jan Rodzoń
 */

public interface INode {
    /**
     * method which add a content to a INode object such as text or title
     * @param line  string to add
     * @param element   specify destiny of the first parameter
     */
    void addContent(String line, ActElement element);

    /**
     * method which return string representation of the node
     *
     * @return content of a node
     */
    String toString();

    /**
     * return paren of a node
     *
     * @return parent node if exists, if not return null
     */
    INode getParent();

    /**
     *
     * @return children in a sequence, if there are not any children return null
     */
    INode nextChild();
}
