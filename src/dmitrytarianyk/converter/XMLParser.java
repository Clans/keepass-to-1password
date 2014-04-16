package dmitrytarianyk.converter;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    private XMLStreamReader mReader;
    private List<Entry> mEntryList = new ArrayList<>();
    private boolean mIgnore;

    public XMLParser(XMLStreamReader reader) {
        mReader = reader;
    }

    public void parseXml() throws XMLStreamException {
        Entry entry = null;
        String content = "";
        String key = "";

        while (mReader.hasNext()) {
            int event = mReader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("Group".equals(mReader.getLocalName())) {
//                        parseXml();
                    } else if ("Entry".equals(mReader.getLocalName())) {
                        entry = new Entry();
                    } else if ("History".equals(mReader.getLocalName())) {
                        mIgnore = true;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    content = mReader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    switch (mReader.getLocalName()) {
                        case "Entry":
                            if (!mIgnore) {
                                mEntryList.add(entry);
                            }
                            break;
                        case "Key":
                            key = content;
                            break;
                        case "Value":
                            if (key.equals(Constants.TITLE)) {
                                entry.title = content;
                            } else if (key.equals(Constants.PASSWORD)) {
                                entry.password = content;
                            }
                            break;
                        case "History":
                            mIgnore = false;
                            break;
                    }
                    break;
            }
        }
    }

    public List<Entry> getEntries() {
        return mEntryList;
    }
}
