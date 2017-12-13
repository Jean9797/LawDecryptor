import java.util.regex.Pattern;

public class LineMatcher {
    private Pattern kancelaria = Pattern.compile(".Kancelaria Sejmu");
    private Pattern smiec = Pattern.compile(".");
    private Pattern rozdzial = Pattern.compile("Rozdział [A-Z]+");
    private Pattern tytul = Pattern.compile("[A-ZĄĘŻŹÓĆŚŁŃ[0-9], ]+");
    private Pattern artykul = Pattern.compile("Art\\. [0-9]+\\.");
    private Pattern ustep = Pattern.compile("[0-9]+\\. .+");
    private Pattern punkt = Pattern.compile("[0-9]+[a-z]?\\) .+");
    private Pattern litera = Pattern.compile("[a-z]\\) .+");

    public ActElement parse(String line){
        if(kancelaria.matcher(line).matches()){
            return ActElement.Kancelaria;
        }
        else if(smiec.matcher(line).matches()){
            return ActElement.Smiec;
        }
        else if(rozdzial.matcher(line).matches()){
            return ActElement.Rozdzial;
        }
        else if(tytul.matcher(line).matches()){
            return ActElement.Tytul;
        }
        else if(artykul.matcher(line).matches()){
            return ActElement.Artykul;
        }
        else if(ustep.matcher(line).matches()){
            return ActElement.Ustep;
        }
        else if(punkt.matcher(line).matches()){
            return ActElement.Punkt;
        }
        else if(litera.matcher(line).matches()){
            return ActElement.Litera;
        }
        else return ActElement.Tekst;
    }
}
