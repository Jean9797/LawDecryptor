public class Paragraph extends ActComponent {

    public Paragraph(String line, INode parent){
        this.parent = parent;
        this.title = line;
        this.index = line.substring(0, line.indexOf("."));
    }
}