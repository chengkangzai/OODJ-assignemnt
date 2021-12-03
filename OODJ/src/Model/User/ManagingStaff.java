/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.User;

import Helper.Connection;
import Helper.Validator;
import java.util.List;

/**
 *
 * @author CCK
 */
public class ManagingStaff extends User {

    private int ID;
    private Double Salary;
    private String Position;
    private User user;

    protected final Connection con = new Connection("users/ManagingStaff");
    protected final Validator valid = new Validator();

    public ManagingStaff() {
    }

    public ManagingStaff(Double Salary) {
        this.Salary = Salary;
    }

    public ManagingStaff(int ID, String Position, Double Salary, User user) {
        super(user.getId());
        this.user = user;
        this.ID = ID;
        this.Salary = Salary;
        this.Position = Position;
    }

    public ManagingStaff(String Position, Double Salary, User user) {
        super(user.getId());
        this.Salary = Salary;
        this.Position = Position;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double Salary) {
        this.Salary = Salary;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public ManagingStaff where(String type, String queryString) {

        int i = 0;
        switch (type.toLowerCase()) {
            case "id" -> i = 0;
            case "position" -> i = 1;
            case "salary" -> i = 2;
            case "user_id" -> i = 3;
            default -> System.out.println("Type not specificied");
        }

        List<String> fromFile = con.getFromFile();
        for (int j = 1; j < fromFile.size(); j++) {
            String[] split = fromFile.get(j).split(",");
            if (split[i].equals(String.valueOf(queryString))) {
                return new ManagingStaff(Integer.parseInt(split[0]), split[1], Double.valueOf(split[2]),
                        new User().where("id", split[3])
                );
            }

        }
        return null;
    }

    @Override
    public boolean create() {
        if (!valid.isValidString(this.Position)) {
            return false;
        }

        List<String> fromFile = con.getFromFile();
        fromFile.add(this.format(true));
        return con.reWrite(con.listToString(fromFile));
    }

    @Override
    public boolean update() {
        if (!valid.isValidString(this.Position)) {
            return false;
        }
        List<String> fromFile = con.getFromFile();
        fromFile.set(getIndex(), this.format(false));
        return con.reWrite(con.listToString(fromFile));
    }

    private int getIndex() {
        List<String> fromFile = con.getFromFile();
        int userID = super.getId();
        for (int i = 0; i < fromFile.size(); i++) {
            if (fromFile.get(i).split(",")[3].equals(String.valueOf(userID))) {
                return i;
            }
        }
        return 0;
    }

    private String format(boolean isCreating) {
        if (!valid.isValidString(this.getPosition())) {
            return null;
        }
        return isCreating
                ? con.getNewID() + "," + this.getPosition() + "," + this.getSalary() + "," + this.user.getId()
                : this.ID + "," + this.getPosition() + "," + this.getSalary() + "," + this.user.getId();
    }

    @Override
    public boolean isAdmin() {
        return true;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

}
