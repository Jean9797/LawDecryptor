import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionManager {
    private Options options;

    public Options getOptions(){
        return this.options;
    }

    public Options createOptions(){
        this.options = new Options();
        options.addOption(Option.builder("f").argName("ścieżka do pliku").hasArg().desc("odczytaj dany plik").build());
        options.addOption(Option.builder("a").argName("numer Artykułu").hasArg().desc("pokaż pojedynczy artykuł").longOpt("artykuł").build());
        options.addOption(Option.builder("A").argName("zakres Artykułów").numberOfArgs(2).valueSeparator('-').desc("pokaż zakres artykułów").longOpt("artykuły").build());
        options.addOption(Option.builder("u").argName("numer Ustępu").hasArg().desc("pokaż ustęp artykułu").build());
        options.addOption(Option.builder("p").argName("numer punktu").hasArg().desc("pokaż punkt ustępu artykułu").build());
        options.addOption(Option.builder("l").argName("numer litery").hasArg().desc("pokaż literę punktu ustępu artykułu").build());
        options.addOption(Option.builder("r").argName("numer rozdziału").hasArg().desc("pokaż rozdział").build());
        options.addOption(Option.builder("s").desc("pokaż spis").build());
        options.addOption(Option.builder("d").argName("numer działu").hasArg().desc("pokaż spis działu").build());
        options.addOption(Option.builder("F").desc("pokaż pełny spis").longOpt("full").build());
        options.addOption(Option.builder("h").desc("pokaż tą wiadomość").longOpt("help").build());
        return options;
    }

    public void checkCorrectnessOfTheArguments(CommandLine cmd){
        if ((!cmd.hasOption("f") && !cmd.hasOption("h")) || (cmd.hasOption("f") && cmd.hasOption("h"))){
            throw new IllegalArgumentException("You must choose only one from -f <file path> or -h for help.");
        }

        if (this.countSelectedOptions(cmd) != 1){
            throw new IllegalArgumentException("You must choose only one parameter from: -h, -F, -d, -s, -r, -A, -a");
        }
    }

    public int countSelectedOptions(CommandLine cmd){
        int number = 0;
        if(cmd.hasOption("h")){
            number++;
        }
        if(cmd.hasOption("F")){
            number++;
        }
        if (cmd.hasOption("d")){
            number++;
        }
        if (cmd.hasOption("s")){
            number++;
        }
        if (cmd.hasOption("r")){
            number++;
        }
        if (cmd.hasOption("A")){
            number++;
        }
        if (cmd.hasOption("a")){
            number++;
        }
        return number;
    }

    public void executeSelectedOptions(CommandLine cmd, Statute statute){
        if(cmd.hasOption("F")){
            System.out.println(statute.toString());
        }
        else if (cmd.hasOption("d")){
            System.out.println(statute.printBriefSection(cmd.getOptionValue("d")));
        }
        else if (cmd.hasOption("s")){
            System.out.println(statute.toBriefList());
        }
        else if (cmd.hasOption("r")){
            System.out.println(statute.printSection(cmd.getOptionValue("r")));
        }
        else if (cmd.hasOption("A")){
            System.out.println(statute.printRangeOfArticles(cmd.getOptionValues("A")));
        }
        else if (cmd.hasOption("a")){
            if (cmd.hasOption("u")){
                if (cmd.hasOption("p")){
                    if (cmd.hasOption("l")){
                        TypeAndIndex[] optionValues = {new TypeAndIndex(ActElementType.Artykul, cmd.getOptionValue("a")),
                                new TypeAndIndex(ActElementType.Ustep, cmd.getOptionValue("u")),
                                new TypeAndIndex(ActElementType.Punkt, cmd.getOptionValue("p")),
                                new TypeAndIndex(ActElementType.Litera, cmd.getOptionValue("l"))};
                        System.out.println(statute.printActElement(optionValues));
                    }
                    else{
                        TypeAndIndex[] optionValues = {new TypeAndIndex(ActElementType.Artykul, cmd.getOptionValue("a")),
                                new TypeAndIndex(ActElementType.Ustep, cmd.getOptionValue("u")),
                                new TypeAndIndex(ActElementType.Punkt, cmd.getOptionValue("p"))};
                        System.out.println(statute.printActElement(optionValues));
                    }
                }
                else {
                    if (cmd.hasOption("l")){
                        throw new IllegalArgumentException("You can't specify letter without specify point.");
                    }
                    else {
                        TypeAndIndex[] optionValues = {new TypeAndIndex(ActElementType.Artykul, cmd.getOptionValue("a")),
                                new TypeAndIndex(ActElementType.Ustep, cmd.getOptionValue("u"))};
                        System.out.println(statute.printActElement(optionValues));
                    }
                }
            }
            else {
                if (cmd.hasOption("p")){
                    if (cmd.hasOption("l")){
                        TypeAndIndex[] optionValues = {new TypeAndIndex(ActElementType.Artykul, cmd.getOptionValue("a")),
                                new TypeAndIndex(ActElementType.Punkt, cmd.getOptionValue("p")),
                                new TypeAndIndex(ActElementType.Litera, cmd.getOptionValue("l"))};
                        System.out.println(statute.printActElement(optionValues));
                    }
                    else {
                        TypeAndIndex[] optionValues = {new TypeAndIndex(ActElementType.Artykul, cmd.getOptionValue("a")),
                                new TypeAndIndex(ActElementType.Punkt, cmd.getOptionValue("p"))};
                        System.out.println(statute.printActElement(optionValues));
                    }
                }
                else {
                    if (cmd.hasOption("l")){
                        throw new IllegalArgumentException("You can't specify letter without specify point.");
                    }
                    else {
                        TypeAndIndex[] optionValues = {new TypeAndIndex(ActElementType.Artykul, cmd.getOptionValue("a"))};
                        System.out.println(statute.printActElement(optionValues));
                    }
                }
            }
        }
    }

}