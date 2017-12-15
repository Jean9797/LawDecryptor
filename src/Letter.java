//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Letter implements INode {
    private final String title;
    private final INode parent;
    private final String index;
    //private List<INode> children = new ArrayList<>();   letters do not have children, but if they do, it would be easy to do it
    private List<String> content = new LinkedList<>();

    public Letter(String line, INode parent){
        this.parent = parent;
        this.title = line;
        this.index = line.substring(0, line.indexOf(")"));
    }

    @Override
    public void addChild(INode node) {
        //this.children.add(node);
    }

    @Override
    public void addContent(String line) {
        this.content.add(line);
    }

    @Override
    public INode getParent() {
        return this.parent;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(this.title);
        result.append("\n");
        for(String text : this.content){
            result.append(text);
            result.append("\n");
        }
        /*for(INode node : this.children){
            result.append(node.toString());
        }*/
        return result.toString();
    }

    public String getIndex(){
        return this.index;
    }
}
