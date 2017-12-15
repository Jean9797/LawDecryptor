import org.apache.commons.cli.*;
import java.io.IOException;

public class Main {
    //wyrzucamy artykuły uchylone

    public static void main(String[] args) throws ParseException, IOException{
        OptionManager optionManager = new OptionManager();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(optionManager.createOptions(), args);

        if (optionManager.countSelectedOptions(cmd) != 1){
            throw new IllegalArgumentException("You can choose only one parameter from: -F, -d, -s, -r, -A, -a");
        }

        FileTransformer file = new FileTransformer();
        Statute statute = file.transform(cmd.getOptionValue("f"));
        optionManager.executeSelectedOptions(cmd, statute);
    }
}
