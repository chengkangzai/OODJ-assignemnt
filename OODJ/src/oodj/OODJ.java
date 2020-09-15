/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oodj;

import oodj.User.User;

/**
 *
 * @author CCK
 */
public class OODJ {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        User u = new User();
        System.out.println(u.isAuthenticated());
    }
    
}
