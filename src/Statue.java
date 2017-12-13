import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Statue implements INode {
    private List<INode> articles = new ArrayList<>();
    private List<String> introduction = new LinkedList<>();
    private String title = "";
    private INode parent = null;

    @Override
    public void addContent(String line, ActElement element){

    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public INode getParent() {
        return parent;
    }

    @Override
    public INode nextChild() {
        return null;
    }
}
