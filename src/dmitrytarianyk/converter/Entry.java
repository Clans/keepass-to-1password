package dmitrytarianyk.converter;

public class Entry {

    String title;
    String location;
    String username;
    String password;
    String notes;

    @Override
    public String toString() {
        return "Entry{" +
                "title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
