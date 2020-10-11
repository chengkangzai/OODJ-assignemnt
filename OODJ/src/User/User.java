/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Helper.Connection;
import Helper.Validator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author CCK
 */
public class User {

    private int id;
    private String name;
    private String email;
    // 'admin' / 'delivery'
    private String role;
    private String password;

    private boolean authenticated = false;

    Connection reader = new Connection("user.txt");
    Validator valid = new Validator();

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
        List<String> fromFile = reader.getFromFile();
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
        if (!(valid.isValidEmail(email))) {
            System.out.println("Ivalid Email");
            return false;
        }
        if (!(valid.isValidString(password))) {
            System.out.println("Ivalid Password");
            return false;
        }

        List<String> dbLine = reader.getFromFile();
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
        return false;
    }

    /**
     *
     * Register the user by object
     *
     * @return
     */
    public boolean register() {
        if (!(valid.isValidEmail(email))) {
            System.out.println("Ivalid Email");
            return false;
        }
        if (!(valid.isValidString(password))) {
            System.out.println("Ivalid Password");
            return false;
        }
        if (!(valid.isValidString(name))) {
            System.out.println("Ivalid Password");
            return false;
        }

        List<String> dbLine = reader.getFromFile();
        for (int i = 1; i < dbLine.size(); i++) {
            String split[] = dbLine.get(i).split(",");
            if (split[3].equals(email)) {
                //Email exits in database, hence cannot register this guy
                System.out.println("Email exist !");
                return false;
            }
        }

        String hashedPassword = getHash(this.password.getBytes());
        int ID = reader.getFromFile().size();
        String userRole = isAdmin() ? "admin" : "delivery";
        dbLine.add(ID + "," + this.name + "," + hashedPassword + "," + this.email + "," + userRole);

        return reader.reWriteDB(reader.listToString(dbLine));

    }

    /**
     *
     * Update the user
     *
     * @return
     */
    public boolean update() {
        if (!(valid.isValidEmail(email))) {
            System.out.println("Ivalid Email");
            return false;
        }
        if (!(valid.isValidString(name))) {
            System.out.println("Ivalid Password");
            return false;
        }
        if (!(valid.isValidString(password))) {
            System.out.println("Ivalid Password");
            return false;
        }

        List<String> dbLine = reader.getFromFile();
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

        return reader.reWriteDB(reader.listToString(dbLine));
    }

    /**
     *
     * Find user as similar using where in sql inspired by Laravel
     *
     * @param type
     * @param queryString
     * @return User
     */
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
        List<String> fromFile = reader.getFromFile();
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

    public static void main(String args[]) {
        User u = new User("Ian7@email.com", "P@$$w0rd");
        u.setName("Ian7");
        u.setRole("admin");
        System.out.println(u.getPassword());
        System.out.println(u.getEmail());
        System.out.println(u.getName());
        u.register();

//        User u = new User("Ian@email.com", "password");
////        u.setPassword("password");
////        u.setRole("admin");
//        System.out.println(u.login());
//        System.out.println(u.isAdmin());
//        System.out.println(u.update());
    }

}