public class Subtitle implements INode {
    private final String title;
    private final INode parent;


    public Subtitle(String line, INode parent){
        this.title = line;
        this.parent = parent;
    }

    @Override
    public void addChild(INode node) {
        //there is no child of subtitle
    }

    @Override
    public void addContent(String line) {
        //there is no content
    }

    @Override
    public INode getParent() {
        return this.parent;
    }

    @Override
    public String toString() {
        return title + "\n";
    }

    @Override
    public String getIndex() {
        return this.title;
    }
}
