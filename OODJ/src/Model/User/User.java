package Model.User;

import Helper.Connection;
import Model.Session.Session;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CCK
 */
public class User extends Model {

    public final String DELIVERY_ROLE = "delivery";
    public final String MANAGING_ROLE = "admin";

    private int id;
    private String name;
    private String email;
    // 'admin' / 'delivery'
    private String role;
    private String password;

    private boolean authenticated = false;

    //protected to let child to use
    protected final Connection reader = new Connection("users/users");

    public User() {
    }

    /**
     * Mainly use for Login
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Mainly use within the class and general
     */
    public User(String name, String email, String role, String password, int id) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.id = id;
    }

    public User(int id) {
        List<String> fromFile = reader.getFromFile();
        for (int i = 1; i < fromFile.size(); i++) {
            String[] split = fromFile.get(i).split(",");
            if (Integer.parseInt(split[0]) == id) {
                this.id = Integer.parseInt(split[0]);
                this.name = split[1];
                this.password = split[2];
                this.email = split[3];
                this.role = split[4];
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
     * Only staff / admin
     */
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

        for (String s : dbLine) {
            String[] split = s.split(",");
            if (split[3].equals(email) && split[2].equals(hashedPassword)) {
                this.id = Integer.parseInt(split[0]);
                this.name = split[1];
                this.email = split[3];
                this.role = split[4];
                this.authenticated = true;
                new Session().setUser(split[0]);
                return true;
            }
        }
        return false;
    }

    /**
     * Register the user by object
     */
    @Override
    public boolean create() {
        if (validate(email, password, name)) return false;

        List<String> dbLine = reader.getFromFile();
        for (int i = 1; i < dbLine.size(); i++) {
            String[] split = dbLine.get(i).split(",");
            if (split[3].equals(email)) {
                //Email exits in database, hence cannot register this guy
                System.out.println("Email exist !");
                return false;
            }
        }

        dbLine.add(this.format(true));
        return reader.reWrite(reader.listToString(dbLine));
    }

    private boolean validate(String email, String password, String name) {
        if (!(valid.isValidEmail(email))) {
            System.out.println("Invalid Email");
            return true;
        }
        if (!(valid.isValidString(password))) {
            System.out.println("Invalid Password");
            return true;
        }
        if (!(valid.isValidString(name))) {
            System.out.println("Invalid Password");
            return true;
        }
        return false;
    }

    /**
     * Update the user
     */
    @Override
    public boolean update() {
        if (validate(email, name, password)) return false;

        List<String> dbLine = reader.getFromFile();
        dbLine.set(this.id, this.format(false));
        return reader.reWrite(reader.listToString(dbLine));
    }

    /**
     *
     * Find user as similar using where in sql inspired by Laravel
     *
     * @return User
     */
    @Override
    public User where(String type, String queryString) {
        int i = 0;
        switch (type.toLowerCase()) {
            case "id" -> i = 0;
            case "name" -> i = 1;
            case "email" -> i = 3;
            case "role" -> i = 4;
            default -> System.out.println("Type not specificied");
        }
        List<String> fromFile = reader.getFromFile();

        for (int j = 1; j < fromFile.size(); j++) {
            String[] split = fromFile.get(j).split(",");
            if (split[i].equals(queryString)) {
                return new User(split[1], split[3], split[4], split[2], Integer.parseInt(split[0]));
            }
        }
        System.out.println("Hoi Error la ! shame on you copying Laravel");
        return null;
    }

    /**
     *
     * you should not use this method... but for the sake of Abstraction ...
     * yeah
     */
    @Override
    public ArrayList<User> where(String type, String queryOperator, String queryString) {

        List<String> fromFile = reader.getFromFile();
        ArrayList<User> temp = new ArrayList<>();

        for (int j = 1; j < fromFile.size(); j++) {
            String[] split = fromFile.get(j).split(",");
            int queryInFile = Integer.parseInt(split[0]);
            int query= Integer.parseInt(queryString);
            
            switch (queryOperator.toLowerCase()) {
                    case ">":
                        if (queryInFile > query) {
                            temp.add(new User(split[1], split[3], split[4], split[2], Integer.parseInt(split[0])));
                        }
                        break;
                    case ">=":
                        if (queryInFile >= query) {
                            temp.add(new User(split[1], split[3], split[4], split[2], Integer.parseInt(split[0])));
                        }
                        break;
                    case "<":
                        if (queryInFile < query) {
                            temp.add(new User(split[1], split[3], split[4], split[2], Integer.parseInt(split[0])));
                        }
                        break;
                    case "<=":
                        if (queryInFile <= query) {
                            temp.add(new User(split[1], split[3], split[4], split[2], Integer.parseInt(split[0])));
                        }
                        break;
                    case "=":
                    case "==":
                    case "===":
                        if (queryInFile == query) {
                            temp.add(new User(split[1], split[3], split[4], split[2], Integer.parseInt(split[0])));
                        }
                        break;
            }
        }

        return temp;
    }

    @Override
    public ArrayList<User> all() {
        return this.where("id", ">=", "1");
    }

    /**
     * Get the hashed value of the input Bytes
     * https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
     */
    @Override
    public String getHash(byte[] inputBytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(inputBytes);
            byte[] bytes = md.digest();
            StringBuilder passwordInString = new StringBuilder();
            for (byte aByte : bytes) {
                passwordInString.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            return passwordInString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("STH wrong la brop");
        return null;
    }

    private String format(boolean isCreating) {
        return isCreating
                ? reader.getNewID() + "," + this.name + "," + getHash(this.password.getBytes()) + "," + this.email + "," + this.role
                : this.id + "," + this.name + "," + this.password + "," + this.email + "," + this.role;
    }

    /**
     *
     * Validate if the user is authenticate or not
     *
     * @return true if the user is authenticated
     */
    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    /**
     *
     * Validated is the user is admin or not
     *
     * @return true if the user is admin
     */
    @Override
    public boolean isAdmin() {
        return this.MANAGING_ROLE.equals(this.role);
    }

    public boolean resetPassword() {
        String DEFAULT_PASSWORD = "password";
        this.setPassword(this.getHash(DEFAULT_PASSWORD.getBytes()));
        return this.update();
    }

    public void resetPassword(String password) {
        this.setPassword(this.getHash(password.getBytes()));
        this.update();
    }

}
