package dmitrytarianyk.converter;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    private XMLStreamReader mReader;
    private FileWriter mWriter;
    private List<Entry> mEntryList = new ArrayList<>();
    private boolean mSkip;
    private boolean mSplitByGroups;

    public XMLParser(XMLStreamReader reader, FileWriter writer, boolean split) {
        mSplitByGroups = split;
        mReader = reader;
        mWriter = writer;
    }

    public void parseXml() throws XMLStreamException {
        Entry entry = null;
        String content = "";
        String key = "";
        String groupName = "";

        while (mReader.hasNext()) {
            int event = mReader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("Entry".equals(mReader.getLocalName())) {
                        entry = new Entry();
                    } else if ("History".equals(mReader.getLocalName())) {
                        mSkip = true;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    content = mReader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    switch (mReader.getLocalName()) {
                        case "Entry":
                            if (!mSkip) {
                                mEntryList.add(entry);
                            }
                            break;
                        case "Key":
                            key = content;
                            break;
                        case "Value":
                            processValue(entry, key, content);
                            break;
                        case "History":
                            mSkip = false;
                            break;
                        case "Name":
                            groupName = content;
                            break;
                        case "Group":
                            if (mSplitByGroups) {
                                writeData(groupName);
                                groupName = "";
                            }
                            break;
                    }
                    break;
            }
        }
    }

    private void processValue(Entry entry, String key, String content) {
        switch (key) {
            case Constants.TITLE:
                entry.title = content;
                break;
            case Constants.USERNAME:
                entry.username = content;
                break;
            case Constants.PASSWORD:
                entry.password = content;
                break;
            case Constants.URL:
                entry.location = content;
                break;
            case Constants.NOTES:
                entry.notes = content;
                break;
        }
    }

    public List<Entry> getEntries() {
        return mEntryList;
    }

    private void writeData(String folder) {
        if (mSplitByGroups && folder.equals("")) return;

        String path = mSplitByGroups ? "/" + folder : "";
        mWriter.writeToFile(mEntryList, path);
        mEntryList = new ArrayList<>();
    }
}
