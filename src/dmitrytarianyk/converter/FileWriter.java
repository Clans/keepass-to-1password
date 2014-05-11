package dmitrytarianyk.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {

    private static final String PATH_SUFFIX = "/keepass-to-1password";

    private String mPath;

    public FileWriter(String path) {
        mPath = path.substring(0, path.lastIndexOf(File.separator)) + PATH_SUFFIX;
    }

    public void writeToFile(List<Entry> entryList, String pathToFile) {
        pathToFile = mPath + pathToFile;
        Path file = Paths.get(pathToFile + "/database-1psw.csv");
        try {
            Files.deleteIfExists(file);
            Files.createDirectories(Paths.get(pathToFile));
            file = Files.createFile(file);
        } catch (IOException e) {
            System.err.println("Error creating file");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(file, Charset.forName("UTF-8"))) {
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
