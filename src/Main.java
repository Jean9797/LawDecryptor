import org.apache.commons.cli.*;

import java.io.IOException;

public class Main {
    //wyrzucamy artykuły uchylone

    public static void main(String[] args) throws ParseException, IOException{
        CommandLineParser parser = new DefaultParser();

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

        CommandLine line = parser.parse(options, args);
        FileScanner file = new FileScanner();
        if(line.hasOption("f")){
            Statute statute = file.transform(line.getOptionValue("f"));
            System.out.println(statute.toString());
            System.out.println(statute.toBriefList());
        }


    }
}
