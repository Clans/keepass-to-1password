package dmitrytarianyk.converter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {

    public void writeToFile(List<Entry> entryList, String pathToFile) {
        Path file = Paths.get(pathToFile + "/new-database.csv");
        try {
            Files.deleteIfExists(file);
            Files.createDirectories(Paths.get(pathToFile));
            file = Files.createFile(file);
        } catch (IOException e) {
            System.err.println("Error creating file");
        }

        try(BufferedWriter writer = Files.newBufferedWriter(file, Charset.forName("UTF-8"))) {
            for (Entry entry : entryList) {
                writer.append(entry.toString());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing to file");
        }
    }
}
