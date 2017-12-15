public class Point extends ActComponent {

    public Point(String line, INode parent){
        this.parent = parent;
        this.title = line;
        this.index = line.substring(0, line.indexOf(")"));
    }
}
