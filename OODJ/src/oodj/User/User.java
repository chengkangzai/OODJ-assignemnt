/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oodj.User;

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
    private String password;
    private int id;
    private boolean authenticated = false;

    private final static String FILENAME = "user.txt";
    private final static Path PATH = Paths.get(FILENAME);

    public User() {

    }

    /**
     * Mainly use for Login
     *
     * @param email
     * @param password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Mainly use within the class and general
     *
     * @param name
     * @param email
     * @param role
     * @param password
     * @param id
     */
    public User(String name, String email, String role, String password, int id) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = getHash(password.getBytes());
        this.id = id;
    }

    public User(int id) {
        List<String> fromFile = getFromFile();
        for (int i = 1; i < fromFile.size(); i++) {
            String split[] = fromFile.get(i).split(",");
            if (Integer.valueOf(split[0]) == id) {
                this.id = Integer.valueOf(split[0]);
                this.name = split[1];
                this.password = split[2];
                this.email = split[3];
                this.role = split[4];
            }
        }
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public boolean login() {
        if (isValidEmail(this.email) && isValidString(this.password)) {
            List<String> dbLine = getFromFile();
            String hashedPassword = getHash(this.password.getBytes());

            for (int i = 0; i < dbLine.size(); i++) {
                String split[] = dbLine.get(i).split(",");

                if (split[3].equals(email) && split[2].equals(hashedPassword)) {
                    this.id = Integer.valueOf(split[0]);
                    this.name = split[1];
                    this.email = split[3];
                    this.role = split[4];
                    this.authenticated = true;
                    return true;
                }
            }

        }
        System.out.println("Invalid Email or Password !");
        return false;
    }

    /**
     *
     * Register the user by object
     *
     * @return
     */
    public boolean register() {
        if (isValidEmail(this.email) && isValidString(this.name) && isValidString(this.password)) {
            List<String> dbLine = getFromFile();
            for (int i = 1; i < dbLine.size(); i++) {
                String split[] = dbLine.get(i).split(",");
                if (split[3].equals(email)) {
                    //Email exits in database, hence cannot register this guy
                    System.out.println("Email exist !");
                    return false;
                }
            }

            String hashedPassword = getHash(this.password.getBytes());
            int ID = getFromFile().size();
            String userRole = isAdmin() ? "admin" : "delivery";
            dbLine.add(ID + "," + this.name + "," + hashedPassword + "," + this.email + "," + userRole);

            return reWriteDB(listToString(dbLine));
        }
        System.out.println("Invalid Email or Name or Password !");
        return false;
    }

    public boolean update() {
        List<String> dbLine = getFromFile();
        if (isValidEmail(this.email) && isValidString(this.name)) {

            for (int i = 1; i < dbLine.size(); i++) {
                String split[] = dbLine.get(i).split(",");
                if (split[3].equals(email)) {
                    this.id = Integer.valueOf(split[0]);
                }
            }

            String hashedPassword = getHash(this.password.getBytes());
            int ID = whereEqual("email", email).id;
            String userRole = isAdmin() ? "admin" : "delivery";
            String line = ID + "," + this.name + "," + hashedPassword + "," + this.email + "," + userRole;
            dbLine.set(this.id, line);

            return reWriteDB(listToString(dbLine));
        }

        return reWriteDB(listToString(dbLine));

    }

    public User whereEqual(String type, String queryString) {
        int i = 0;
        switch (type.toLowerCase()) {
            case "id":
                i = 0;
                break;
            case "name":
                i = 1;
                break;
            case "email":
                i = 3;
                break;
            case "role":
                i = 4;
                break;
            default:
                System.out.println("Type not specificied");
                break;
        }
        List<String> fromFile = getFromFile();
        for (String element : fromFile) {
            String[] split = element.split(",");
            if (split[i].equals(queryString)) {
                return new User(split[1], split[3], split[4], split[2], Integer.valueOf(split[0]));
            }
        }
        System.out.println("Hoi Error la ! shame on you copying Laravel");
        return null;
    }

    /**
     * Get the hashed value of the input Bytes
     * https://www.tutorialspoint.com/java_cryptography/java_cryptography_message_digest.htm
     *
     * @param inputBytes
     * @return
     */
    public static String getHash(byte[] inputBytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(inputBytes);
            byte[] digestedByte = md.digest();
            return DatatypeConverter.printHexBinary(digestedByte).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("STH wrong la brop");
        return "";
    }

    /**
     *
     * Validate the string is valid or not
     *
     * @param String input
     * @return true is the string is not empty and not containing coma(,)
     */
    private static boolean isValidString(String input) {
        return !(input == null || input.isEmpty() || input.matches(","));
    }

    /**
     *
     * Validate the email is valid or not
     *
     * @param String email
     * @return true if the input string is not empty and is a email
     */
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return isValidString(email) && pat.matcher(email).matches();
    }

    /**
     *
     * Validate if the user is authenticate or not
     *
     * @return true if the user is authenticated
     */
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    /**
     *
     * Validated is the user is admin or not
     *
     * @return true if the user is admin
     */
    public boolean isAdmin() {
        return "admin".equals(this.role);
    }

    /**
     *
     * Get all the line from the text file
     *
     * @return Raw data from the text file
     */
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
     * @return List<String>
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

    /**
     *
     * Rewrite the text file, make sure the line is properly formatted
     *
     * @param data
     * @return
     */
    private boolean reWriteDB(String data) {
        boolean success = false;
        try {
            Files.write(PATH, data.getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            success = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    public static void main(String args[]) {
//        User u = new User("Ian3@email.com", "P@$$w0rd");
//        u.setName("Ian3");
//        u.setRole("admin");
//        System.out.println(u.getPassword());
//        System.out.println(u.getEmail());
//        System.out.println(u.getName());
//        u.register();

//        User u = new User("Ian@email.com", "password");
////        u.setPassword("password");
////        u.setRole("admin");
//        System.out.println(u.login());
//        System.out.println(u.isAdmin());
//        System.out.println(u.update());
    }

}
