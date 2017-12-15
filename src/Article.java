public class Article extends ActComponent {

    public Article(String line, INode parent){
        this.parent = parent;
        this.title = line;
        String tmp = line.substring(line.indexOf(" ") + 1);
        tmp = tmp.substring(0, tmp.indexOf("."));
        this.index = tmp;
    }
}
