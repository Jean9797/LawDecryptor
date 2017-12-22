
/**
 * Interface for interacting with object structure of the act
 *
 * @author Jan Rodzoń
 */

public interface INode {
    /**
     * method which is used to add title of the component
     *
     * @param line contains title
     */
    void addTitle(String line);

    /**
     * method which is used to build dependence tree
     *
     * @param node will be added to children collection
     */
    void addChild(INode node);

    /**
     * method which add a content to a INode object such as text or title
     * @param line  string to add
     */
    void addContent(String line);

    /**
     * return paren of a node
     *
     * @return parent node if exists, if not return null
     */
    INode getParent();

    /**
     * method which return string representation of the node
     *
     * @return content of a node
     */
    String toString();

    /**
     *
     * @return index which is characteristic for each element on particular level
     */
    String getIndex();

    /**
     *
     * @return type of the component
     */
    ActElementType getType();

    /**
     *
     * @return true if the next child exists, otherwise false
     */
    boolean hasNextChild();

    /**
     *
     * @return the next child
     */
    INode nextChild();
}
