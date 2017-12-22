import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParseException, IOException{
        OptionManager optionManager = new OptionManager();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(optionManager.createOptions(), args);

        optionManager.checkCorrectnessOfTheArguments(cmd);

        if (cmd.hasOption("h")){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "java -cp bin:commons-cli-1.4/commons-cli-1.4.jar Main", optionManager.getOptions() , true);
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
