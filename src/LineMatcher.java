import java.util.regex.Pattern;

public class LineMatcher {
    private boolean isConstitution = true;

    private Pattern kancelaria = Pattern.compile(".Kancelaria Sejmu");
    private Pattern smiec = Pattern.compile(".");
    private Pattern uchylony = Pattern.compile(".*\\(uchylony\\)");
    private Pattern pominiete = Pattern.compile(".*\\(pominięte\\)");
    private Pattern rozdzial = Pattern.compile("Rozdział [A-Z]+");
    private Pattern tytul = Pattern.compile("[A-ZĄĘŻŹÓĆŚŁŃ, ][A-ZĄĘŻŹÓĆŚŁŃ0-9, ]+");
    private Pattern podrozdzial = Pattern.compile("Rozdział [0-9]+[a-z]?");
    private Pattern dzial = Pattern.compile("DZIAŁ [A-Z]+");
    private Pattern artykul = Pattern.compile("Art\\. [0-9]+[a-z]?\\..*");
    private Pattern ustep = Pattern.compile("[0-9]+[a-z]?\\. .+");
    private Pattern punkt = Pattern.compile("[0-9]+[a-z]?\\) .+");
    private Pattern litera = Pattern.compile("[a-z]\\) .+");

    public ActElement parse(String line){
        if(kancelaria.matcher(line).matches()){
            return ActElement.Kancelaria;
        }
        else if(smiec.matcher(line).matches()){
            return ActElement.Smiec;
        }
        else if(uchylony.matcher(line).matches()){
            return ActElement.Smiec;
        }
        else if(pominiete.matcher(line).matches()){
            return ActElement.Smiec;
        }
        else if(dzial.matcher(line).matches()){
            this.isConstitution = false;
            return ActElement.Sekcja;
        }
        else if(!this.isConstitution && podrozdzial.matcher(line).matches()){
            return ActElement.Podrozdzial;
        }
        else if(this.isConstitution && rozdzial.matcher(line).matches()){
            return ActElement.Sekcja;
        }
        else if(this.isConstitution && tytul.matcher(line).matches()){
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

    private Pattern dashEnd = Pattern.compile(".*[a-zżźćąśęńół]+-");

    public boolean endsWithDash(String line){
        return dashEnd.matcher(line).matches();
    }

    public boolean getIsConstitution(){
        return this.isConstitution;
    }

    private Pattern chainedArticle = Pattern.compile("Art\\. [0-9]+[a-z]?\\. [0-9]+[a-z]?\\..*");

    public boolean isChainedArticle(String line){
        return this.chainedArticle.matcher(line).matches();
    }
}
