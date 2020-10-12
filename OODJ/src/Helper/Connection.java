/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 *
 * @author CCK
 */
public class Connection {

    private final Path PATH;

    public Connection(String FILENAME) {
        this.PATH = Paths.get(FILENAME);
    }

    /**
     *
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
     *
     * https://rollbar.com/guides/java-throwing-exceptions/
     *
     * @param arraylist
     * @return
     */
    public String listToString(List<String> arraylist) {
        String line = new String();
        if (!arraylist.isEmpty()) {
            for (int i = 0; i < arraylist.size(); i++) {
                line += arraylist.get(i) + "\n";
            }
        }
        return line;
    }

    /**
     *
     * Rewrite the text file, make sure the line is properly formatted
     *
     * @param data
     * @return
     */
    public boolean reWrite(String data) {
        boolean success = false;
        try {
            Files.write(PATH, data.getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            success = true;
        } catch (IOException e) {
            System.out.println("The connection between text file cannot be initiate");
        }
        return success;
    }

    public String getLastUsedID() {
        return getFromFile().get(getFromFile().size() - 1).split("\\,")[0];
    }

    public int getNewID() {
        return (this.getLastUsedID().equals("ID"))
                ? 1
                : Integer.valueOf(this.getLastUsedID()) + 1;
    }

    public String comma2Pipe(String input) {
        return input.replace(',', '|');
    }

    public String pipe2Comma(String input) {
        return input.replace('|', ',');
    }

    public static void main(String[] args) {
        Connection connection = new Connection("deliveries.txt");
        List<String> fromFile = connection.getFromFile();
        System.out.println(connection.getNewID());
        fromFile.forEach((element) -> {
            System.out.println(element);
        });
    }

}
