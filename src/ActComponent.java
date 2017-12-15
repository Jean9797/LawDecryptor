import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ActComponent implements INode {
    protected String title = null;
    protected String index = null;
    protected INode parent = null;
    protected List<INode> children = new ArrayList<>();
    protected List<String> content = new LinkedList<>();
    protected Iterator<INode> iterator = null;

    @Override
    public void addChild(INode node) {
        this.children.add(node);
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
        for(INode node : this.children){
            result.append(node.toString());
        }
        return result.toString();
    }

    public String getIndex(){
        return this.index;
    }

    public boolean hasNextChild(){
        if(this.iterator == null){
            iterator = children.iterator();
        }
        return this.iterator.hasNext();
    }

    public INode nextChild(){
        return iterator.next();
    }
}
