/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

/**
 *
 * @author CCK
 */
public class Staff extends User {

    String phoneNumber;
    String carPlate;
    Double salary;

    public Staff() {
    }

    public Staff(String phoneNumber, String carPlate, Double salary) {
        this.phoneNumber = phoneNumber;
        this.carPlate = carPlate;
        this.salary = salary;
    }

    public Staff(String phoneNumber, String carPlate, Double salary, String email, String password) {
        super(email, password);
        this.phoneNumber = phoneNumber;
        this.carPlate = carPlate;
        this.salary = salary;
    }

    public Staff(String phoneNumber, String carPlate, Double salary, String name, String email, String role, String password, int id) {
        super(name, email, role, password, id);
        this.phoneNumber = phoneNumber;
        this.carPlate = carPlate;
        this.salary = salary;
    }

    public Staff(String phoneNumber, String carPlate, Double salary, int id) {
        super(id);
        this.phoneNumber = phoneNumber;
        this.carPlate = carPlate;
        this.salary = salary;
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
