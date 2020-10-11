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
public class Admin extends User {

    Double Salary;

    public Admin() {
    }

    public Admin(Double Salary) {
        this.Salary = Salary;
    }

    public Admin(Double Salary, String email, String password) {
        super(email, password);
        this.Salary = Salary;
    }

    public Admin(Double Salary, String name, String email, String role, String password, int id) {
        super(name, email, role, password, id);
        this.Salary = Salary;
    }

    public Admin(Double Salary, int id) {
        super(id);
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
