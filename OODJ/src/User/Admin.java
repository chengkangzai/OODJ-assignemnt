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
public class Admin extends User {

    Double Salary;

    protected Connection con = new Connection("db/users/admin.txt");

    public Admin() {
    }

    public Admin(Double Salary) {
        this.Salary = Salary;
    }

    public Admin(Double Salary, User user) {
        super(user.getId());
        this.Salary = Salary;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double Salary) {
        this.Salary = Salary;
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
