import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineMatcher {
    private boolean isConstitution = true;

    private Pattern rozdzial = Pattern.compile("Rozdział [A-Z]+");
    private Pattern tytul = Pattern.compile("[A-ZĄĘŻŹÓĆŚŁŃ, ][A-ZĄĘŻŹÓĆŚŁŃ0-9, ]+");
    private Pattern podrozdzial = Pattern.compile("Rozdział [0-9]+[a-z]?");
    private Pattern dzial = Pattern.compile("DZIAŁ [A-Z]+");
    private Pattern artykul = Pattern.compile("Art\\. [0-9]+[a-z]?\\.");
    private Pattern ustep = Pattern.compile("[0-9]+[a-z]?\\. .+");
    private Pattern punkt = Pattern.compile("[0-9]+[a-z]?\\) .+");
    private Pattern litera = Pattern.compile("[a-z]\\) .+");

    public TypeAndIndex parse(String line){
        if(dzial.matcher(line).matches()){
            this.isConstitution = false;
            return new TypeAndIndex(ActElementType.Sekcja, this.excludeIndex(line, ActElementType.Sekcja));
        }
        else if(this.isConstitution && rozdzial.matcher(line).matches()){
            return new TypeAndIndex(ActElementType.Sekcja, this.excludeIndex(line, ActElementType.Sekcja));
        }
        else if(!this.isConstitution && podrozdzial.matcher(line).matches()){
            return new TypeAndIndex(ActElementType.Podsekcja, this.excludeIndex(line, ActElementType.Podsekcja));
        }
        else if(this.isConstitution && tytul.matcher(line).matches()){
            return new TypeAndIndex(ActElementType.Podsekcja, this.excludeIndex(line, ActElementType.Podsekcja));
        }
        else if(artykul.matcher(line).matches()){
            return new TypeAndIndex(ActElementType.Artykul, this.excludeIndex(line, ActElementType.Artykul));
        }
        else if(ustep.matcher(line).matches()){
            return new TypeAndIndex(ActElementType.Ustep, this.excludeIndex(line, ActElementType.Ustep));
        }
        else if(punkt.matcher(line).matches()){
            return new TypeAndIndex(ActElementType.Punkt, this.excludeIndex(line, ActElementType.Punkt));
        }
        else if(litera.matcher(line).matches()){
            return new TypeAndIndex(ActElementType.Litera, this.excludeIndex(line, ActElementType.Litera));
        }
        else return new TypeAndIndex(ActElementType.Tekst, this.excludeIndex(line, ActElementType.Tekst));
    }

    public boolean getIsConstitution(){
        return this.isConstitution;
    }

    private String excludeIndex(String line, ActElementType type){
        Pattern pattern;
        Matcher matcher;
        switch (type){
            case Sekcja:
                String tmp = line.substring(line.indexOf(" ") + 1);
                return tmp;
            case Podsekcja:
                return "";
            case Artykul:
                pattern = Pattern.compile("Art\\. ([0-9]+[a-z]?)\\.");
                matcher = pattern.matcher(line);
                matcher.matches();
                return matcher.group(1);
            case Ustep:
                pattern = Pattern.compile("([0-9]+[a-z]?)\\. .+");
                matcher = pattern.matcher(line);
                matcher.matches();
                return matcher.group(1);
            case Punkt:
                pattern = Pattern.compile("([0-9]+[a-z]?)\\) .+");
                matcher = pattern.matcher(line);
                matcher.matches();
                return matcher.group(1);
            case Litera:
                pattern = Pattern.compile("([a-z])\\) .+");
                matcher = pattern.matcher(line);
                matcher.matches();
                return matcher.group(1);
            case Tekst:
                return "";
            default:
                throw new IllegalArgumentException("ActElementType " + type.toString() + " is not allowed in excludeIndex method.");
        }
    }
}
