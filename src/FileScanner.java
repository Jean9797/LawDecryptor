import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileScanner {
    public void read(String file) throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            StructureBuilder builder = new StructureBuilder();
            while ((line = br.readLine()) != null) {
                builder.build(line);
            }
        }
    }
}