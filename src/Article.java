import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Article implements INode {
    private final String title;
    private final INode parent;
    private final String number;
    private List<INode> children = new ArrayList<>();
    private List<String> content = new LinkedList<>();
    private Iterator<INode> iterator = null;

    public Article(String line, INode parent){
        this.parent = parent;
        this.title = line;
        String tmp = line.substring(5);
        tmp = tmp.substring(0, tmp.indexOf("."));
        this.number = tmp;
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
