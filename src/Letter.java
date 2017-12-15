public class Letter extends ActComponent {

    public Letter(String line, INode parent){
        this.parent = parent;
        this.title = line;
        this.index = line.substring(0, line.indexOf(")"));
    }
}
