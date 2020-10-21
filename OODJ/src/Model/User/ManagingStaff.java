/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.User;

import Helper.Connection;

/**
 *
 * @author CCK
 */
public class ManagingStaff extends User {

    private int ID;
    private Double Salary;
    private String Position;

    protected final Connection con = new Connection("users/ManagingStaff");

    public ManagingStaff() {
    }

    public ManagingStaff(Double Salary) {
        this.Salary = Salary;
    }

    public ManagingStaff(Double Salary, String Position, User user) {
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

    @Override
    public ManagingStaff where(String type, String queryString) {
        //TODO
        return new ManagingStaff();
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
