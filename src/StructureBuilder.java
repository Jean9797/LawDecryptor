import java.util.Stack;

public class StructureBuilder {
    private LineMatcher matcher = new LineMatcher();
    private boolean skip = false;                   //gives an information if the line is not important, used when kancelaria matches

    private Stack<INode> trace = new Stack<>();     //place where we will put current trace from the root of the tree to the curren point

    private String transfer = "";                   //next 4 are used for transfering part of the world from end of the line
    private String futureTransfer = "";
    private boolean isTransfer = false;
    private boolean isNextTransfer = false;

    void build(String line){
        if (skip){
            skip = false;
            return;
        }

        if(isNextTransfer){
            isNextTransfer = false;
            isTransfer = true;
            transfer = futureTransfer;
        }

        if (line.endsWith("-")){
            isNextTransfer = true;
            futureTransfer = line.substring(line.lastIndexOf(" ")+1);
            futureTransfer = futureTransfer.substring(0, futureTransfer.length() - 1);
            line = line.substring(0, line.lastIndexOf(" "));
        }

        ActElement element = matcher.parse(line);
        switch (element){
            case Kancelaria:
                System.out.println(element.toString() + " : " + line);
                skip = true;
                break;
            case Smiec:
                System.out.println(element.toString() + " : " + line);
                break;
            case Rozdzial:
                System.out.println(element.toString() + " : " + line);
                break;
            case Tytul:
                System.out.println(element.toString() + " : " + line);
                break;
            case Artykul:
                System.out.println(element.toString() + " : " + line);
                break;
            case Ustep:
                System.out.println(element.toString() + " : " + line);
                break;
            case Punkt:
                System.out.println(element.toString() + " : " + line);
                break;
            case Litera:
                System.out.println(element.toString() + " : " + line);
                break;
            default:
                if (isTransfer) {
                    System.out.println(element.toString() + " : " + transfer + line);
                    isTransfer = false;
                }
                else System.out.println(element.toString() + " : " + line);
        }
    }
}
