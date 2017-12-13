import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Statute implements INode {
    private List<INode> children = new ArrayList<>();       //contains articles, chapters and subtitles.
    private List<INode> chapters = new ArrayList<>();
    private List<String> introduction = new LinkedList<>();
    private List<String> title = new LinkedList<>();
    private final INode parent = null;
    private Iterator<INode> iterator = null;

    public void addChapter(INode chapter){
        this.children.add(chapter);
        this.chapters.add(chapter);
    }

    public void addTitle(String line){
        title.add(line);
    }

    @Override
    public void addChild(INode node) {
        this.children.add(node);
    }

    @Override
    public void addContent(String line){
        this.introduction.add(line);
    }

    @Override
    public INode getParent() {
        return parent;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for(String text : this.title){
            result.append(text);
            result.append("\n");
        }
        for(String text : this.introduction){
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

    public String toBriefList(){
        StringBuilder result = new StringBuilder("");
        for(String text : this.title){
            result.append(text);
            result.append("\n");
        }
        for(INode node : this.children){
            if(node instanceof Chapter || node instanceof Subtitle)
                result.append(node.toString());
        }
        return result.toString();
    }
}
