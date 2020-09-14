/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oodj;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Pattern;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author CCK
 */
public class User {

    private String name;
    private String email;
    // 'admin' / 'delivery'
    private String role;
    private int id;
    private boolean authenticated;

    private static String FILENAME = "user.txt";
    private static Path PATH = Paths.get(FILENAME);

    public User() {
        this.authenticated = false;
    }

    public User(String name, int id, String email) {
        this.authenticated = false;
        this.name = name;
        this.id = id;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @param email
     * @param password
     * @return
     */
    public boolean login(String email, String password) {
        if (isValidEmail(email) && isValidString(password)) {
            List<String> dbLine = getFromFile();
            String hashedPassword = getHash(password.getBytes(), "SHA-256");

            for (int i = 0; i < dbLine.size(); i++) {
                String split[] = dbLine.get(i).split(",");

                if (split[3].equals(email) && split[2].equals(hashedPassword)) {
                    this.id = Integer.valueOf(split[0]);
                    this.name = split[1];
                    this.email = split[3];
                    this.role = split[3];
                    this.authenticated = true;
                    return true;
                }
            }

        }
        return false;
    }

    public boolean register(User user, String password) {
        if (isValidEmail(user.email) && isValidString(user.name)) {

            String hashedPassword = getHash(password.getBytes(), "SHA-256");
            int ID = getFromFile().size();
            List<String> dbLine = getFromFile();
            String userRole = (user.isAdmin()) ? "admin" : "delivery";

            dbLine.add(ID + "," + user.name + "," + hashedPassword + "," + user.email + "," + userRole + "\n");

            return reWriteDB(listToString(dbLine));
        }
        return false;
    }

    /**
     *
     * https://www.tutorialspoint.com/java_cryptography/java_cryptography_message_digest.htm
     *
     * @param inputBytes
     * @param algorithm
     * @return
     */
    public static String getHash(byte[] inputBytes, String algorithm) {
        String hashValue = "";
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(inputBytes);
            byte[] digestedByte = md.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedByte).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return hashValue;
    }

    private static boolean isValidString(String input) {
        return !(input.isEmpty() || input.matches(","));
    }

    private static boolean isValidEmail(String email) {
        if (!isValidString(email)) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    public boolean isAuthenticated() {
        return this.authenticated;
    }

    public boolean isAdmin() {
        return this.isAuthenticated() && "admin".equals(this.role);
    }

    private static List<String> getFromFile() {
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
     * @throws Exception
     */
    private String listToString(List<String> arraylist) {
        String line = new String();
        if (!arraylist.isEmpty()) {
            for (int i = 0; i < arraylist.size(); i++) {
                line += arraylist.get(i) + "\n";
            }
        }
        return line;
    }

    public boolean reWriteDB(String data) {
        boolean success = false;
        try {
            Files.write(PATH, data.getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            success = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    public static void main(String[] args) {
        User u = new User();
        System.out.println(u.login("email@email.com", "P@$$word"));

//        u.setEmail("email@email.com");
//        u.setName("Ian");
//        u.register(u, "P@$$word");
    }

}
