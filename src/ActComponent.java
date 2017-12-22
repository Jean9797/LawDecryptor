import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ActComponent implements INode {
    protected ActElementType type = null;
    protected String title = "";
    protected String index = null;
    protected INode parent = null;
    protected List<INode> children = new ArrayList<>();
    protected List<String> content = new LinkedList<>();
    protected Iterator<INode> iterator = null;

    public ActComponent(String line, INode parent, TypeAndIndex typeAndIndex){
        this.parent = parent;
        addTitle(line);
        this.index = typeAndIndex.getIndex();
        this.type = typeAndIndex.getType();
    }

    @Override
    public void addTitle(String line){
        this.title = this.title + line + "\n";
    }

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
        for(String text : this.content){
            result.append(text);
            result.append("\n");
        }
        for(INode node : this.children){
            result.append(node.toString());
        }
        return result.toString();
    }

    @Override
    public String getIndex(){
        return this.index;
    }

    @Override
    public ActElementType getType() {
        return type;
    }

    @Override
    public boolean hasNextChild(){
        if(this.iterator == null){
            iterator = children.iterator();
        }
        return this.iterator.hasNext();
    }

    @Override
    public INode nextChild(){
        return iterator.next();
    }
}