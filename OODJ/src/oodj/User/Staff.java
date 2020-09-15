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
public class Staff extends User {

    public Staff() {
    }

    public Staff(String name, String email) {
        super(name, email);
    }

    public Staff(String name, String email, String role, String password, int id) {
        super(name, email, role, password, id);
    }

    public Staff(int id) {
        super(id);
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
