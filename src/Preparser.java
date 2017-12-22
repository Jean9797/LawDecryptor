import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preparser {
    private List<String> preparedText;              //here the processed text is stored
    private Pattern kancelaria = Pattern.compile(".Kancelaria Sejmu.*");
    private Pattern data = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    private Pattern smiec = Pattern.compile(".");
    private Pattern uchylony = Pattern.compile(".*\\(uchylony\\)");
    private Pattern pominiete = Pattern.compile(".*\\(pominięte\\)");
    private Pattern chainedArticle = Pattern.compile("Art\\. [0-9]+[a-z]?\\. .+");
    private Pattern dashEnd = Pattern.compile(".* [^ ]+-");

    private String transfer = "";                   //next 2 are used for transfering part of the world from end of the line
    private boolean isTransfer = false;

    public List<String> preparse(List<String> text){
        preparedText = new LinkedList<>();
        for (String line : text){
            this.process(line);
        }
        return this.preparedText;
    }

    private void process(String line){
        if (kancelaria.matcher(line).matches() || data.matcher(line).matches() || smiec.matcher(line).matches() || uchylony.matcher(line).matches() || pominiete.matcher(line).matches() || line.equals("")){}
        else if (chainedArticle.matcher(line).matches()){
            Pattern pattern = Pattern.compile("(Art\\..[0-9]+[a-z]?\\.) (.*)");
            Matcher matcher = pattern.matcher(line);
            matcher.matches();
            String firstPart = matcher.group(1);
            String secondPart = matcher.group(2);
            this.addLine(firstPart);
            this.process(secondPart);
        }
        else if (dashEnd.matcher(line).matches()){
            Pattern pattern = Pattern.compile("(.*) ([^ ]+)-");
            Matcher matcher = pattern.matcher(line);
            matcher.matches();
            String firstPart = matcher.group(1);
            String secondPart = matcher.group(2);
            this.addLine(firstPart);
            isTransfer = true;
            transfer = secondPart;
        }
        else {
            this.addLine(line);
        }
    }

    private void addLine(String line){
        if (isTransfer){
            isTransfer = false;
            preparedText.add(transfer + line);
        }
        else {
            preparedText.add(line);
        }
    }
}