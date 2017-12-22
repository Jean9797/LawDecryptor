import java.util.Stack;

public class StructureBuilder {
    private Statute statute = null;                 //this is root of out dependence tree
    private INode currentSection = null;            //this is current section, it is used to add titles to sections
    private INode currentSubsection = null;         //this is current subsection, it is used to add titles to subsections
    private LineMatcher matcher = new LineMatcher();
    private boolean isSectionTitle = false;         //gives an information if the line is title of the section
    private boolean isSubsectionTitle = false;      //gives an information if the line is title of the subsection

    private Stack<INode> trace = new Stack<>();     //place where we will put current trace from the root of the tree to the curren point

    public StructureBuilder(){
        statute = new Statute();
        trace.push(statute);
    }

    void build(String line) {

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

        TypeAndIndex typeAndIndex = matcher.parse(line);
        switch (typeAndIndex.getType()) {
            case Sekcja:
                //System.out.println(element.toString() + " : " + line);
                this.createSection(line, typeAndIndex);
                break;
            case Podsekcja:
                //System.out.println(element.toString() + " : " + line);
                this.createSubsection(line, typeAndIndex);
                break;
            case Artykul:
                //System.out.println(element.toString() + " : " + line);
                this.createArticle(line, typeAndIndex);
                break;
            case Ustep:
                //System.out.println(element.toString() + " : " + line);
                this.createSmallComponent(line, typeAndIndex, ActElementType.Artykul);
                break;
            case Punkt:
                //System.out.println(element.toString() + " : " + line);
                this.createSmallComponent(line, typeAndIndex, ActElementType.Ustep);
                break;
            case Litera:
                //System.out.println(element.toString() + " : " + line);
                this.createSmallComponent(line, typeAndIndex, ActElementType.Punkt);
                break;
            default:
                //System.out.println(ActElementType.Tekst.toString() + " : " + line);
                this.addLine(line);
        }
    }

    public Statute getStatute(){
        return this.statute;
    }

    private void createSection(String line, TypeAndIndex typeAndIndex){
        currentSection = new ActComponent(line, statute, typeAndIndex);
        statute.addChild(currentSection);
        isSectionTitle = true;
    }

    private void createSubsection(String line, TypeAndIndex typeAndIndex){
        if (trace.peek() instanceof Statute){
            statute.addTitle(line);
        }
        else {
            currentSubsection = new ActComponent(line, currentSection, typeAndIndex);
            statute.addChild(currentSubsection);
            if (!matcher.getIsConstitution())
                isSubsectionTitle = true;
        }
    }

    private void createArticle(String line, TypeAndIndex typeAndIndex){
        while (!(trace.peek() instanceof Statute)) trace.pop();
        INode article = new ActComponent(line, trace.peek(), typeAndIndex);
        trace.peek().addChild(article);
        trace.push(article);
    }

    private void createSmallComponent(String line, TypeAndIndex typeAndIndex, ActElementType parentComponent){
        while (!(trace.peek().getType() == parentComponent)){
            if (trace.peek().getType() == ActElementType.Artykul){
                break;
            }
            trace.pop();
        }
        INode createdComponent = new ActComponent(line, trace.peek(), typeAndIndex);
        trace.peek().addChild(createdComponent);
        trace.push(createdComponent);
    }

    private void addLine(String line){
        trace.peek().addContent(line);
    }
}