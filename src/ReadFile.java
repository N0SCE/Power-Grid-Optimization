import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFile {

    public String[] readFile(String fileName) throws IOException {
        String[] empty = new String[0];
        String line;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            int lenght = Files.readAllLines(Paths.get(fileName)).size();
            String[] array = new String[lenght];
            int lineNumber = 0;
            while ((line = bufferedReader.readLine()) != null) {
                array[lineNumber] = line;
                lineNumber++;
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return empty;
    }
}
