import java.util.Stack;

public class StructureBuilder {
    private Statute statute = null;                 //this is root of out dependence tree
    private INode currentSection = null;            //this is current section, it is used to add titles to sections
    private INode currentSubsection = null;         //this is current subsection, it is used to add titles to subsections
    private LineMatcher matcher = new LineMatcher();
    private boolean skip = false;                   //gives an information if the line is not important, used when kancelaria matches
    private boolean isSectionTitle = false;         //gives an information if the line is title of the section
    private boolean isSubsectionTitle = false;      //gives an information if the line is title of the subsection

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

        if (isSectionTitle) {
            isSectionTitle = false;
            currentSection.addContent(line);
            return;
        }

        if (isSubsectionTitle) {
            isSubsectionTitle = false;
            currentSubsection.addContent(line);
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
                //System.out.println(element.toString() + " : " + line);
                skip = true;
                break;
            case Smiec:
                //System.out.println(element.toString() + " : " + line);
                break;
            case Sekcja:
                //System.out.println(element.toString() + " : " + line);
                this.createSection(line);
                break;
            case Tytul:
                //System.out.println(element.toString() + " : " + line);
                this.createTitle(line);
                break;
            case Podrozdzial:
                //System.out.println(element.toString() + " : " + line);
                this.createSubchapter(line);
                break;
            case Artykul:
                //System.out.println(element.toString() + " : " + line);
                this.createArticle(line);
                break;
            case Ustep:
                //System.out.println(element.toString() + " : " + line);
                this.createParagraph(line);
                break;
            case Punkt:
                //System.out.println(element.toString() + " : " + line);
                this.createPoint(line);
                break;
            case Litera:
                //System.out.println(element.toString() + " : " + line);
                this.createLetter(line);
                break;
            default:
                this.addLine(line);
        }
    }

    public Statute getStatute(){
        return this.statute;
    }

    private void createSection(String line){
        currentSection = new Section(line, statute);
        statute.addChild(currentSection);
        isSectionTitle = true;
    }

    private void createTitle(String line){
        if (trace.peek() instanceof Statute) {
            statute.addTitle(line);
        } else {
            INode subtitle = new Subsection(line, currentSection);
            currentSection.addChild(subtitle);
            statute.addChild(subtitle);
        }
    }

    private void createSubchapter(String line){
        currentSubsection = new Subsection(line, currentSection);
        currentSection.addChild(currentSubsection);
        statute.addChild(currentSubsection);
        isSubsectionTitle = true;
    }

    private void createArticle(String line){
        while (!(trace.peek() instanceof Statute)) trace.pop();
        INode article = new Article(line, trace.peek());
        trace.peek().addChild(article);
        trace.push(article);
        if (!this.matcher.getIsConstitution()){
            String tmp = line.substring(line.indexOf(" ") + 1);
            tmp = tmp.substring(tmp.indexOf(" ") + 1);
            if (matcher.isChainedArticle(line)){
                INode paragraph = new Paragraph(tmp, trace.peek());
                trace.peek().addChild(paragraph);
                trace.push(paragraph);
            }
            else{
                article.addContent(tmp);
            }
        }
    }

    private void createParagraph(String line){
        while (!(trace.peek() instanceof Article)) trace.pop();
        INode paragraph = new Paragraph(line, trace.peek());
        trace.peek().addChild(paragraph);
        trace.push(paragraph);
    }

    private void createPoint(String line){
        while (!(trace.peek() instanceof Paragraph)){
            if (trace.peek() instanceof Article){
                break;
            }
            trace.pop();
        }
        INode point = new Point(line, trace.peek());
        trace.peek().addChild(point);
        trace.push(point);
    }

    private void createLetter(String line){
        while (!(trace.peek() instanceof Point)){
            if (trace.peek() instanceof Article){
                break;
            }
            trace.pop();
        }
        INode letter = new Letter(line, trace.peek());
        trace.peek().addChild(letter);
        trace.push(letter);
    }

    private void addLine(String line){
        if (isTransfer) {
            //System.out.println(ActElement.Tekst.toString() + " : " + transfer + line);
            isTransfer = false;
            String text = transfer + line;
            trace.peek().addContent(text);
        } else {
            //System.out.println(ActElement.Tekst.toString() + " : " + line);
            trace.peek().addContent(line);
        }
    }
}