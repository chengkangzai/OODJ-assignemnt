/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.User;

import Helper.Connection;
import java.util.List;

/**
 *
 * @author CCK
 */
public class DeliveryStaff extends User {

    private int ID;
    private String phoneNumber;
    private String carPlate;
    private Double salary;
    private User user;

    protected Connection con = new Connection("users/DeliveryStaff");

    public DeliveryStaff() {
    }

    public DeliveryStaff(int ID, String phoneNumber, String carPlate, Double salary) {
        this.phoneNumber = phoneNumber;
        this.carPlate = carPlate;
        this.salary = salary;
        this.ID = ID;
    }

    public DeliveryStaff(int ID, String phoneNumber, String carPlate, Double salary, User user) {
        super(user.getId());
        this.phoneNumber = phoneNumber;
        this.carPlate = carPlate;
        this.salary = salary;
        this.ID = ID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public DeliveryStaff where(String type, String queryString) {
        int i = 0;
        switch (type.toLowerCase()) {
            case "id":
                i = 0;
                break;
            case "phonenumber":
                i = 1;
                break;
            case "carplate":
                i = 2;
                break;
            case "salary":
                i = 3;
                break;
            case "user_id":
                i = 4;
                break;
            default:
                System.out.println("Type not specificied");
                break;
        }

        List<String> fromFile = con.getFromFile();
        for (int j = 1; j < fromFile.size(); j++) {
            String[] split = fromFile.get(j).split(",");
            if (split[i].equals(String.valueOf(queryString))) {
                return new DeliveryStaff(Integer.valueOf(split[0]), split[1], split[2], Double.valueOf(split[3]),
                        new User().where("id", split[4])
                );
            }

        }
        return null;
    }

    @Override
    public boolean create() {
        if (!valid.isValidString(carPlate)) {
            return false;
        }
        if (!valid.isValidString(this.phoneNumber)) {
            return false;
        }
        if (this.salary <= 0) {
            return false;
        }
        List<String> fromFile = con.getFromFile();

        fromFile.add(this.format(true));
        return con.reWrite(con.listToString(fromFile));

    }

    @Override
    public boolean update() {
        if (!valid.isValidString(carPlate)) {
            return false;
        }
        if (!valid.isValidString(this.phoneNumber)) {
            return false;
        }
        if (this.salary <= 0) {
            return false;
        }

        List<String> fromFile = con.getFromFile();
        fromFile.set(getIndex(), this.format(false));

        return con.reWrite(con.listToString(fromFile));
    }

    private String format(boolean isCreating) {
        return (isCreating)
                ? con.getNewID() + "," + this.phoneNumber + "," + this.carPlate + "," + this.salary + "," + this.user.getId()
                : this.ID + "," + this.phoneNumber + "," + this.carPlate + "," + this.salary + "," + this.user.getId();
    }

    private int getIndex() {
        List<String> fromFile = con.getFromFile();
        int userID = super.getId();
        for (int i = 0; i < fromFile.size(); i++) {
            if (fromFile.get(i).split(",")[4].equals(String.valueOf(userID))) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    public static void main(String[] args) {
        DeliveryStaff staff = new DeliveryStaff().where("user_id", "2");
        staff.setSalary(2000.00);
        System.out.println(staff.update());

    }
}
