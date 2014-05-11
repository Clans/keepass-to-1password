package dmitrytarianyk.converter;

import org.apache.commons.cli.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.List;

public class Main {

    private static final String OPT_FILE = "f";
    private static final String OPT_SPLIT = "s";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, XMLStreamException {
        CommandLineParser clParser = new BasicParser();
        Options options = new Options();

        Option optFile = new Option(OPT_FILE, "file", true, "path to keepass xml file");
        optFile.setArgName("path");
        Option optSplit = new Option(OPT_SPLIT, "split", false, "split groups on folders");

        Option optHelp = new Option("h", "help", false, "print this message");
        options.addOption(optFile);
        options.addOption(optSplit);
        options.addOption(optHelp);

        try {
            CommandLine cl = clParser.parse(options, args);
            String path = cl.getOptionValue(OPT_FILE);
            boolean split = cl.hasOption(OPT_SPLIT);

            readData(path, split);
        } catch (ParseException e) {
            System.err.println("Unexpected error: " + e.getMessage());
            printUsage(options);
        }

    }

    private static void readData(String path, boolean split) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        File file = new File(path);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            XMLStreamReader reader = factory.createXMLStreamReader(bufferedReader);
            FileWriter writer = new FileWriter(path);
            XMLParser parser = new XMLParser(reader, writer, split);
            parser.parseXml();
            if (!split) {
                List<Entry> entryList = parser.getEntries();
                writer.writeToFile(entryList, "");
            }
        } catch (FileNotFoundException e) {
            System.err.printf("No such file");
        } catch (IOException e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void printUsage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar keepass-to-1password.jar", "Options:", options, null, true);
    }
}
