/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oodj.User;

/**
 *
 * @author CCK
 */
public class Admin extends User{

    public Admin() {
    }

    public Admin(String email, String password) {
        super(email, password);
    }

    public Admin(String name, String email, String role, String password, int id) {
        super(name, email, role, password, id);
    }

    public Admin(int id) {
        super(id);
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
