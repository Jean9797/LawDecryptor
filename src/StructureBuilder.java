import java.util.Stack;

public class StructureBuilder {
    private Statute statute = null;                 //this is root of out dependence tree
    private INode currentChapter = null;            //this is current chapter, it is used to add titles to chapters
    private LineMatcher matcher = new LineMatcher();
    private boolean skip = false;                   //gives an information if the line is not important, used when kancelaria matches
    private boolean isChapterTitle = false;         //gives an information if the line is title of the chapter

    private Stack<INode> trace = new Stack<>();     //place where we will put current trace from the root of the tree to the curren point

    private String transfer = "";                   //next 4 are used for transfering part of the world from end of the line
    private String futureTransfer = "";
    private boolean isTransfer = false;
    private boolean isNextTransfer = false;

    public StructureBuilder(){
        statute = new Statute();
        trace.push(statute);
    }

    void build(String line) {
        if (skip) {
            skip = false;
            return;
        }

        if (isChapterTitle) {
            isChapterTitle = false;
            currentChapter.addContent(line);
            return;
        }

        if (isNextTransfer) {
            isNextTransfer = false;
            isTransfer = true;
            transfer = futureTransfer;
        }

        if (matcher.endsWithDash(line)) {
            isNextTransfer = true;
            futureTransfer = line.substring(line.lastIndexOf(" ") + 1);
            futureTransfer = futureTransfer.substring(0, futureTransfer.length() - 1);
            line = line.substring(0, line.lastIndexOf(" "));
        }

        ActElement element = matcher.parse(line);
        switch (element) {
            case Kancelaria:
                System.out.println(element.toString() + " : " + line);
                skip = true;
                break;
            case Smiec:
                System.out.println(element.toString() + " : " + line);
                break;
            case Rozdzial:
                System.out.println(element.toString() + " : " + line);
                currentChapter = new Chapter(line, statute);
                statute.addChapter(currentChapter);
                isChapterTitle = true;
                break;
            case Tytul:
                System.out.println(element.toString() + " : " + line);
                if (trace.peek() instanceof Statute) {
                    statute.addTitle(line);
                } else {
                    INode subtitle = new Subtitle(line, currentChapter);
                    statute.addChild(subtitle);
                }
                break;
            case Artykul:
                System.out.println(element.toString() + " : " + line);
                while (!(trace.peek() instanceof Statute)) trace.pop();
                INode article = new Article(line, trace.peek());
                trace.peek().addChild(article);
                trace.push(article);
                break;
            case Ustep:
                System.out.println(element.toString() + " : " + line);
                while (!(trace.peek() instanceof Article)) trace.pop();
                INode paragraph = new Paragraph(line, trace.peek());
                trace.peek().addChild(paragraph);
                trace.push(paragraph);
                break;
            case Punkt:
                System.out.println(element.toString() + " : " + line);
                while (!(trace.peek() instanceof Paragraph)){
                    if (trace.peek() instanceof Article){
                        break;
                    }
                    trace.pop();
                }
                INode point = new Point(line, trace.peek());
                trace.peek().addChild(point);
                trace.push(point);
                break;
            case Litera:
                System.out.println(element.toString() + " : " + line);
                while (!(trace.peek() instanceof Point)){
                    if (trace.peek() instanceof Article){
                        break;
                    }
                    trace.pop();
                }
                INode letter = new Letter(line, trace.peek());
                trace.peek().addChild(letter);
                trace.push(letter);
                break;
            default:
                if (isTransfer) {
                    System.out.println(element.toString() + " : " + transfer + line);
                    isTransfer = false;
                    String text = transfer + line;
                    trace.peek().addContent(text);
                } else {
                    System.out.println(element.toString() + " : " + line);
                    trace.peek().addContent(line);
                }
        }
    }

    public Statute getStatute(){
        return this.statute;
    }
}
