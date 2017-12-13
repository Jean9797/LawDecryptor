import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileScanner {
    public Statute transform(String file) throws IOException{
        StructureBuilder builder = new StructureBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                builder.build(line);
            }
        }
        return builder.getStatute();
    }
}