import org.apache.commons.cli.*;
import java.io.IOException;

public class Main {
    //wyrzucamy artykuły uchylone

    public static void main(String[] args) throws ParseException, IOException{
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(Main.createOptions(), args);

        FileTransformer file = new FileTransformer();
        Statute statute = file.transform(cmd.getOptionValue("f"));
        if(cmd.hasOption("F")){
            System.out.println(statute.toString());
        }
        else if (cmd.hasOption("d")){
            System.out.println(statute);
        }
        else if (cmd.hasOption("s")){
            System.out.println(statute.toBriefList());
        }
        else if (cmd.hasOption("r")){
            System.out.println(statute.printChapter(cmd.getOptionValue("r")));
        }
        else if (cmd.hasOption("A")){
            System.out.println(statute.printRangeOfArticles(cmd.getOptionValues("A")));
        }
        else if (cmd.hasOption("a")){
            if (cmd.hasOption("u")){
                if (cmd.hasOption("p")){
                    if (cmd.hasOption("l")){
                        String[] optionValues = {cmd.getOptionValue("a"), cmd.getOptionValue("u"), cmd.getOptionValue("p"), cmd.getOptionValue("l")};
                        System.out.println(statute.printElement(optionValues, ActElement.Litera));
                    }
                    else{
                        String[] optionValues = {cmd.getOptionValue("a"), cmd.getOptionValue("u"), cmd.getOptionValue("p")};
                        System.out.println(statute.printElement(optionValues, ActElement.Punkt));
                    }
                }
                else {
                    if (cmd.hasOption("l")){
                        throw new IllegalArgumentException("You can't specify letter without specify point.");
                    }
                    else {
                        String[] optionValues = {cmd.getOptionValue("a"), cmd.getOptionValue("u")};
                        System.out.println(statute.printElement(optionValues, ActElement.Ustep));
                    }
                }
            }
            else {
                if (cmd.hasOption("p")){
                    if (cmd.hasOption("l")){
                        String[] optionValues = {cmd.getOptionValue("a"), cmd.getOptionValue("p"), cmd.getOptionValue("l")};
                        System.out.println(statute.printElement(optionValues, ActElement.Litera));
                    }
                    else {
                        String[] optionValues = {cmd.getOptionValue("a"), cmd.getOptionValue("p")};
                        System.out.println(statute.printElement(optionValues, ActElement.Punkt));
                    }
                }
                else {
                    String[] optionValues = {cmd.getOptionValue("a")};
                    System.out.println(statute.printElement(optionValues, ActElement.Artykul));
                }
            }
        }
    }

    private static Options createOptions(){
        Options options = new Options();
        options.addOption(Option.builder("f").argName("ścieżka").hasArg().desc("odczytaj dany plik").required().build());
        options.addOption(Option.builder("a").argName("numerArtykułu").hasArg().desc("pokaż pojedynczy artykuł").longOpt("artykuł").build());
        options.addOption(Option.builder("A").argName("zakresArtykułów").numberOfArgs(2).valueSeparator('-').desc("pokaż zakres artykułów").longOpt("artykuły").build());
        options.addOption(Option.builder("u").argName("ust").hasArg().desc("pokaż ustęp artykułu").build());
        options.addOption(Option.builder("p").argName("pkt").hasArg().desc("pokaż punkt ustępu artykułu").build());
        options.addOption(Option.builder("l").argName("lit").hasArg().desc("pokaż literę punktu ustępu artykułu").build());
        options.addOption(Option.builder("r").argName("roz").hasArg().desc("pokaż rozdział").build());
        options.addOption(Option.builder("s").desc("pokaż spis").build());
        options.addOption(Option.builder("d").argName("dz").hasArg().desc("pokaż spis działu").build());
        options.addOption(Option.builder("F").desc("pokaż pełny spis").longOpt("full").build());
        return options;
    }
}
