import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Statute implements INode {
    private List<INode> children = new ArrayList<>();       //contains articles, chapters and subtitles.
    private List<String> introduction = new LinkedList<>();
    private List<String> title = new LinkedList<>();
    private final INode parent = null;
    private Iterator<INode> iterator = null;

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

    @Override
    public String getIndex() {
        StringBuilder result = new StringBuilder();
        for(String text : this.title){
            result.append(text);
            result.append("\n");
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

    public String printChapter(String number){
        if (!number.matches("[0-9]+")){
            number = (new Integer(RomanToInteger.romanToDecimal(number))).toString();
        }
        INode tmp = null;
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if(tmp instanceof Chapter && tmp.getIndex().equals(number)){
                break;
            }
        }
        if (tmp == null) throw new NullPointerException("Critical error detected. No articles or chapters found.");
        if(!tmp.getIndex().equals(number)){
            throw new IllegalArgumentException("Chapter not found. Probably wrong number of chapter. Check this out, please.");
        }
        StringBuilder result = new StringBuilder(tmp.toString());
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if(tmp instanceof Chapter) break;
            result.append(tmp.toString());
        }
        return result.toString();
    }

    public String printRangeOfArticles(String[] articles){
        INode tmp = null;
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if(tmp instanceof Article && tmp.getIndex().equals(articles[0])) break;
        }
        if (tmp == null){
            throw new IllegalArgumentException("Wrong articles range. Please type proper numbers after -A");
        }
        StringBuilder result = new StringBuilder(tmp.toString());
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if(tmp instanceof Article && tmp.getIndex().equals(articles[1])){
                result.append(tmp.toString());
                break;
            }
            if (tmp instanceof Chapter || tmp instanceof Subtitle) continue;
            result.append(tmp.toString());
        }
        if (!(tmp.getIndex().equals(articles[1]))){
            throw new IllegalArgumentException("Wrong articles range. Please type proper numbers after -A");
        }
        return result.toString();
    }

    public String printElement(String[] numberOfElements, ActElement element){          //this method return article or paragraph or point or letter
        INode tmp = null;
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if (tmp instanceof Article && tmp.getIndex().equals(numberOfElements[0])){
                Article article = (Article) tmp;

                if (element.equals(ActElement.Artykul)) return article.toString();

                while (article.hasNextChild()){
                    tmp = article.nextChild();
                    if (tmp.getIndex().equals(numberOfElements[1])){
                        if (element.equals(ActElement.Ustep)){
                            if (tmp instanceof Paragraph) return "Art. " + numberOfElements[0] + ". ust. " + tmp.toString();
                            throw new IllegalArgumentException("Paragraph with " + numberOfElements[1] + " number wasn't found in Art. " + numberOfElements[0] + ".");
                        }
                        else {
                            if (tmp instanceof Point){
                                if (element.equals(ActElement.Punkt)) return "Art. " + numberOfElements[0] + ". pkt. " + tmp.toString();
                                Point point = (Point) tmp;
                                while (point.hasNextChild()){
                                    tmp = point.nextChild();
                                    if (tmp.getIndex().equals(numberOfElements[2])) return "Art. " + numberOfElements[0] + ". pkt. " + numberOfElements[1] + ") lit. " + tmp.toString();
                                }
                                throw new IllegalArgumentException("Letter " + numberOfElements[2] + " wasn't found in Art. " + numberOfElements[0] + ". pkt. " + numberOfElements[1] + ")");
                            }
                            else {
                                Paragraph paragraph = (Paragraph) tmp;
                                while (paragraph.hasNextChild()){
                                    tmp = paragraph.nextChild();
                                    if (tmp.getIndex().equals(numberOfElements[2])){
                                        if (element.equals(ActElement.Punkt)) return "Art. " + numberOfElements[0] + ". ust. " + numberOfElements[1] + ". pkt. " + tmp.toString();
                                        Point point = (Point) tmp;
                                        while (point.hasNextChild()){
                                            tmp = point.nextChild();
                                            if (tmp.getIndex().equals(numberOfElements[3])) return "Art. " + numberOfElements[0] + ". ust. " + numberOfElements[1] + ". pkt. " + numberOfElements[2] + ") lit. " +  tmp.toString();
                                        }
                                        throw new IllegalArgumentException("Letter " + numberOfElements[3] + " wasn't found in Art. " + numberOfElements[0] + ". ust. " + numberOfElements[1] + ". pkt. " + numberOfElements[2] + ")");
                                    }
                                }
                                throw new IllegalArgumentException("Point " + numberOfElements[2] + " wasn't found in Art. " + numberOfElements[0] + ". ust. " + numberOfElements[1] + ".");
                            }
                        }
                    }
                }
                if (element.equals(ActElement.Ustep))
                    throw new IllegalArgumentException("ust. " + numberOfElements[1] + ". not found in Art. " + numberOfElements[0] + ".");
                else
                    throw new IllegalArgumentException("pkt. " + numberOfElements[1] + ") not found in Art. " + numberOfElements[0] + ".");
            }
        }
        throw new IllegalArgumentException("Art. " + numberOfElements[0] + ". wasn't found.");
    }
}
