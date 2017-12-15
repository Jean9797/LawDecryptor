import java.util.LinkedList;
import java.util.List;

public class Section implements INode {
    private final INode parent;
    private final String index;
    private String name;
    private String title;
    private List<INode> subsections = new LinkedList<>();


    public Section(String line, INode parent){
        this.parent = parent;
        this.name = line;
        String tmp = line.substring(line.indexOf(" ") + 1);
        this.index = converteFromRoman(tmp);
    }

    @Override
    public void addChild(INode node) {
        this.subsections.add(node);
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

    private String converteFromRoman(String roman){
        return (new Integer(RomanToInteger.romanToDecimal(roman))).toString();
    }

    public String getIndex(){
        return this.index;
    }
}
