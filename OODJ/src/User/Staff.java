/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Helper.Connection;
import java.util.List;

/**
 *
 * @author CCK
 */
public class Staff extends User {

    private String phoneNumber;
    private String carPlate;
    private Double salary;

    protected Connection con = new Connection("db/users/staff.txt");

    public Staff() {
    }

    public Staff(String phoneNumber, String carPlate, Double salary) {
        this.phoneNumber = phoneNumber;
        this.carPlate = carPlate;
        this.salary = salary;
    }

    public Staff(String phoneNumber, String carPlate, Double salary, User user) {
        super(user.getId());
        this.phoneNumber = phoneNumber;
        this.carPlate = carPlate;
        this.salary = salary;
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

    public Staff where(String type, String queryString) {
        User user = whereEqual("id", String.valueOf(super.getId()));
        if (user.isAdmin()) {
            List<String> fromFile = con.getFromFile();
            for (int i = 0; i < fromFile.size(); i++) {
                String[] split = fromFile.get(i).split(",");
                if (split[0].equals(String.valueOf(user.getId()))) {
                    return new Staff(split[1], split[2], Double.valueOf(split[3]), user);
                }
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

        super.create();
        
        List<String> fromFile = con.getFromFile();
        fromFile.set(super.getId(), this.format());

        return con.reWrite(con.listToString(fromFile));
    }

    @Override
    public boolean update() {
        super.update();
        System.out.println("Hello");
        return false;
    }

    private String format() {
        return super.getId() + "," + this.phoneNumber + "," + this.carPlate + "," + this.salary;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

}
