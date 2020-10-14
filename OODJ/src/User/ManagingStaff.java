/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Helper.Connection;

/**
 *
 * @author CCK
 */
public class ManagingStaff extends User {

    private Double Salary;

    protected final Connection con = new Connection("db/users/ManagingStaff.txt");

    public ManagingStaff() {
    }

    public ManagingStaff(Double Salary) {
        this.Salary = Salary;
    }

    public ManagingStaff(Double Salary, User user) {
        super(user.getId());
        this.Salary = Salary;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double Salary) {
        this.Salary = Salary;
    }

    public ManagingStaff where(String type,String queryString) {
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
