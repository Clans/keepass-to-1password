package dmitrytarianyk.converter;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        File file = new File("/mnt/data/Dropbox/keepass/database-min.xml");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            XMLStreamReader reader = factory.createXMLStreamReader(bufferedReader);
            XMLParser parser = new XMLParser(reader);
            parser.parseXml();

            for (Entry entry : parser.getEntries()) {
                System.out.println(entry);
            }
        }
    }
}
