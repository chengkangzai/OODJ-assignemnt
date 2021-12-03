package Helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @author CCK
 */
public class Connection {

    private final Path PATH;

    Validator valid = new Validator();

    /**
     * For the sake of Interface implementation...
     */
    public Connection() {
        this.PATH = Paths.get("test");
    }

    public Connection(String FILENAME) {
        this.PATH = Paths.get("db/" + FILENAME + ".csv");
    }

    /**
     * Get all the line from the text file
     *
     * @return Raw data from the text file
     */
    public List<String> getFromFile() {
        List<String> data = null;
        try {
            data = Files.readAllLines(PATH);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return data;
    }

    /**
     * https://rollbar.com/guides/java-throwing-exceptions/
     */
    public String listToString(List<String> arraylist) {
        StringBuilder line = new StringBuilder();
        if (!arraylist.isEmpty()) {
            for (String s : arraylist) {
                line.append(s).append("\n");
            }
        }
        return line.toString();
    }

    /**
     * Rewrite the text file, make sure the line is properly formatted
     */
    public boolean reWrite(String data) {
        try {
            Files.write(PATH, data.getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            return true;
        } catch (IOException e) {
            System.out.println("The connection between text file cannot be initiate");
            return false;
        }
    }

    private String getLastUsedID() {
        return getFromFile().get(getFromFile().size() - 1).split(",")[0];
    }

    /**
     * Get new ID for the model
     *
     */
    public int getNewID() {
        return this.getLastUsedID().equals("ID")
                ? 1
                : Integer.parseInt(this.getLastUsedID()) + 1;
    }

    /**
     * Change comma to pipe
     */
    public String comma2Pipe(String input) {
        return input.replace(',', '|');
    }

    /**
     * Change pipe to comma (Display purpose)
     */
    public String pipe2Comma(String input) {
        return input.replace('|', ',');
    }

}
