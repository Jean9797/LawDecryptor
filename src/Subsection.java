public class Subsection implements INode {
    private final String name;
    private String title = null;
    private final INode parent;


    public Subsection(String line, INode parent){
        this.name = line;
        this.parent = parent;
    }

    @Override
    public void addChild(INode node) {
        //there is no child of subtitle
    }

    @Override
    public void addContent(String line) {
        this.title = line;
    }

    @Override
    public INode getParent() {
        return this.parent;
    }

    @Override
    public String toString() {
        if (this.title == null)
            return this.name + "\n";
        else
            return this.name + "\n" + this.title + "\n";
    }

    @Override
    public String getIndex() {
        return this.name;
    }
}
