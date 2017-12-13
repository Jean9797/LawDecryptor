import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Chapter implements INode {
    private final INode parent;
    private String name;
    private String title;
    private List<INode> subtitles = new LinkedList<>();
    private Iterator<INode> iterator = null;


    public Chapter(String line, INode parent){
        this.parent = parent;
        this.name = line;
    }

    @Override
    public void addChild(INode node) {
        this.subtitles.add(node);
    }

    @Override
    public void addContent(String line) {           //content of the chapter is its title
        this.title = line;
    }

    @Override
    public INode getParent() {
        return this.parent;
    }

    @Override
    public String toString() {
        return this.name + "\n" + this.title + "\n";
    }
}
