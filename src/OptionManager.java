import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionManager {



    /*private Options createOptions(){
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
    }*/

    public boolean countOptions(CommandLine cmd){
        int number = 0;
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
        return number == 1;
    }

}
