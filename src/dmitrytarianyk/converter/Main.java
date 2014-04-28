package dmitrytarianyk.converter;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.List;

public class Main {


    public static void main(String[] args) throws ParserConfigurationException, SAXException, XMLStreamException {
        // TODO: read input params
        XMLInputFactory factory = XMLInputFactory.newInstance();
        File file = new File("/mnt/data/Dropbox/keepass/Database.xml");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            XMLStreamReader reader = factory.createXMLStreamReader(bufferedReader);
            FileWriter writer = new FileWriter();
            XMLParser parser = new XMLParser(reader, writer);
            parser.parseXml();
            List<Entry> entryList = parser.getEntries();

            for (Entry entry : entryList) {
                System.out.println(entry);
            }

//            writer.writeToFile(entryList, "/mnt/data/Dropbox/keepass");

            System.out.println("Total number of entries: " + entryList.size());
        } catch (FileNotFoundException e) {
            System.err.printf("No such file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
