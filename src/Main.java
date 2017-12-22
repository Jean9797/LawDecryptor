import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParseException, IOException{
        OptionManager optionManager = new OptionManager();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(optionManager.createOptions(), args);

        if ((!cmd.hasOption("f") && !cmd.hasOption("h")) || (cmd.hasOption("f") && cmd.hasOption("h"))){
            throw new IllegalArgumentException("You must choose only one from -f <file path> or -h for help.");
        }

        if (optionManager.countSelectedOptions(cmd) != 1){
            throw new IllegalArgumentException("You must choose only one parameter from: -h, -F, -d, -s, -r, -A, -a");
        }

        if (cmd.hasOption("h")){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "java main", optionManager.getOptions() , true);
            return;
        }

        FileScanner file = new FileScanner();
        List<String> text = file.read(cmd.getOptionValue("f"));
        Preparser preparser = new Preparser();
        List<String> preparedText = preparser.preparse(text);
        StructureBuilder builder = new StructureBuilder();
        for (String line : preparedText){
            builder.build(line);
        }
        Statute statute = builder.getStatute();
        optionManager.executeSelectedOptions(cmd, statute);
    }
}
