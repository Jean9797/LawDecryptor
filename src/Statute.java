public class Statute extends ActComponent {

    public Statute(){
        super("", null, new TypeAndIndex(ActElementType.Ustawa, ""));
        this.title = "";
    }

    public String toBriefList(){
        StringBuilder result = new StringBuilder(this.title);
        for(INode node : this.children){
            if(node.getType() == ActElementType.Sekcja || node.getType() == ActElementType.Podsekcja)
                result.append(node.toString());
        }
        return result.toString();
    }

    public String printSection(String number){
        if (!number.matches("[0-9]+")){
            number = (new Integer(RomanToInteger.romanToDecimal(number))).toString();
        }
        INode tmp = null;
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if(tmp.getType() == ActElementType.Sekcja && tmp.getIndex().equals(number)){
                break;
            }
        }
        if (tmp == null) throw new NullPointerException("Critical error detected. No articles or chapters found.");
        if(!tmp.getIndex().equals(number)){
            throw new IllegalArgumentException("Section not found. Probably wrong number of chapter. Check this out, please.");
        }
        StringBuilder result = new StringBuilder(tmp.toString());
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if(tmp.getType() == ActElementType.Sekcja) break;
            result.append(tmp.toString());
        }
        return result.toString();
    }

    public String printBriefSection(String number){
        if (!number.matches("[0-9]+")){
            number = (new Integer(RomanToInteger.romanToDecimal(number))).toString();
        }
        INode tmp = null;
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if(tmp.getType() == ActElementType.Sekcja && tmp.getIndex().equals(number)){
                break;
            }
        }
        if (tmp == null) throw new NullPointerException("Critical error detected. No articles or chapters found.");
        if(!tmp.getIndex().equals(number)){
            throw new IllegalArgumentException("Section not found. Probably wrong number of chapter. Check this out, please.");
        }
        StringBuilder result = new StringBuilder(tmp.toString());
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if(tmp.getType() == ActElementType.Sekcja) break;
            if(tmp.getType() == ActElementType.Podsekcja) {
                result.append(tmp.toString());
            }
        }
        return result.toString();
    }

    public String printRangeOfArticles(String[] articles){
        INode tmp = null;
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if(tmp.getType() == ActElementType.Artykul && tmp.getIndex().equals(articles[0])) break;
        }
        if (tmp == null){
            throw new IllegalArgumentException("Wrong articles range. Please type proper numbers after -A");
        }
        StringBuilder result = new StringBuilder(tmp.toString());
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if(tmp.getType() == ActElementType.Artykul && tmp.getIndex().equals(articles[1])){
                result.append(tmp.toString());
                break;
            }
            if (tmp.getType() == ActElementType.Sekcja || tmp.getType() == ActElementType.Podsekcja) continue;
            result.append(tmp.toString());
        }
        if (!(tmp.getIndex().equals(articles[1]))){
            throw new IllegalArgumentException("Wrong articles range. Please type proper numbers after -A");
        }
        return result.toString();
    }

    public String printActElement(TypeAndIndex[] elements){
        return printActElementEngine(elements, 0, this, "");
    }

    private String printActElementEngine(TypeAndIndex[] elements, int deepth, INode current, String path){
        path = path + this.buildPath(elements[deepth]);
        INode child;
        while (current.hasNextChild()){
            child = current.nextChild();
            if (child.getType() == elements[deepth].getType() && child.getIndex().equals(elements[deepth].getIndex())){
                if (deepth < elements.length - 1)
                    return printActElementEngine(elements, deepth + 1, child, path);
                else
                    return path + "\n" + child.toString();
            }
        }
        throw new ElementNotFoundException(path + " wasn't found.");
    }

    private String buildPath(TypeAndIndex typeAndIndex){
        switch (typeAndIndex.getType()){
            case Artykul:
                return "Art. " + typeAndIndex.getIndex() + ".";
            case Ustep:
                return " ust. " + typeAndIndex.getIndex() + ".";
            case Punkt:
                return " pkt. " + typeAndIndex.getIndex() + ")";
            case Litera:
                return " lit. " + typeAndIndex.getIndex() + ")";
            default:
                return "";
        }
    }

    @Deprecated
    public String printElement(String[] numberOfElements, ActElementType element){          //this method return article or paragraph or point or letter
        INode tmp = null;
        while(this.hasNextChild()){
            tmp = this.nextChild();
            if (tmp.getType() == ActElementType.Artykul && tmp.getIndex().equals(numberOfElements[0])){
                INode article = tmp;

                if (element.equals(ActElementType.Artykul)) return article.toString();

                while (article.hasNextChild()){
                    tmp = article.nextChild();
                    if (tmp.getIndex().equals(numberOfElements[1])){
                        if (element.equals(ActElementType.Ustep)){
                            if (tmp.getType() == ActElementType.Ustep) return "Art. " + numberOfElements[0] + ". ust. " + tmp.toString();
                            throw new IllegalArgumentException("Paragraph " + numberOfElements[1] + " wasn't found in Art. " + numberOfElements[0] + ".");
                        }
                        else {
                            if (tmp.getType() == ActElementType.Punkt){
                                if (element.equals(ActElementType.Punkt)) return "Art. " + numberOfElements[0] + ". pkt. " + tmp.toString();
                                if (numberOfElements.length == 2) throw new IllegalArgumentException("Point " + numberOfElements[1] + "wasn't found in Art. " + numberOfElements[0] + ".");
                                INode point = tmp;
                                while (point.hasNextChild()){
                                    tmp = point.nextChild();
                                    if (tmp.getIndex().equals(numberOfElements[2])) return "Art. " + numberOfElements[0] + ". pkt. " + numberOfElements[1] + ") lit. " + tmp.toString();
                                }
                                throw new IllegalArgumentException("Letter " + numberOfElements[2] + " wasn't found in Art. " + numberOfElements[0] + ". pkt. " + numberOfElements[1] + ")");
                            }
                            else {
                                INode paragraph = tmp;
                                while (paragraph.hasNextChild()){
                                    tmp = paragraph.nextChild();
                                    if (tmp.getIndex().equals(numberOfElements[2])){
                                        if (element.equals(ActElementType.Punkt)) return "Art. " + numberOfElements[0] + ". ust. " + numberOfElements[1] + ". pkt. " + tmp.toString();
                                        INode point = tmp;
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
                if (element.equals(ActElementType.Ustep))
                    throw new IllegalArgumentException("ust. " + numberOfElements[1] + ". not found in Art. " + numberOfElements[0] + ".");
                else
                    throw new IllegalArgumentException("pkt. " + numberOfElements[1] + ") not found in Art. " + numberOfElements[0] + ".");
            }
        }
        throw new IllegalArgumentException("Art. " + numberOfElements[0] + ". wasn't found.");
    }
}