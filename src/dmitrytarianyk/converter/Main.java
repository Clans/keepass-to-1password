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

    public static void main(String[] args) throws ParserConfigurationException, SAXException, XMLStreamException {
        CommandLineParser clParser = new BasicParser();
        Options options = new Options();

        Option optFile = new Option(OPT_FILE, "file", true, "path to keepass xml file");
        optFile.setArgName("path");

        Option optHelp = new Option("h", "help", false, "print this message");
        options.addOption(optFile);
        options.addOption(optHelp);

        try {
            CommandLine cl = clParser.parse(options, args);
            if (cl.hasOption("h")) {
                printUsage(options);
            } else {
                String path = cl.getOptionValue(OPT_FILE);
                readData(path);
            }
        } catch (ParseException e) {
            System.err.println("Unexpected error: " + e.getMessage());
            printUsage(options);
        }

    }

    private static void readData(String path) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        File file = new File(path);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            XMLStreamReader reader = factory.createXMLStreamReader(bufferedReader);
            FileWriter writer = new FileWriter(path);
            XMLParser parser = new XMLParser(reader);
            parser.parseXml();
            List<Entry> entryList = parser.getEntries();
            writer.writeToFile(entryList);
            System.out.println("Total entries: " + entryList.size());
            System.out.println("Path to converted file: " + writer.getPath());
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
