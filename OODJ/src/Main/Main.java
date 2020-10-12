/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import User.DeliveryStaff;
import User.User;

/**
 *
 * @author CCK
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Date test = new Date();
//        LocalTime myObj = LocalTime.now();
//        System.out.println(myObj);

//        User u = new User();
//        u.setEmail("admin@email.com");
//        u.setName("admin");
//        u.setPassword("P@$$w0rd");
//        u.setRole("admin");
//        System.out.println(u.create());
//        Admin admin = new Admin(100.00, new User().whereEqual("id", "1"));

        User u = new User();
        u.setEmail("staff@email.com");
        u.setName("staff");
        u.setPassword("P@$$w0rd");
        u.setRole("staff");
        System.out.println(u.create());


//        admin.setName("Ian6");
//        System.out.println(admin.create());
//        Admin admin1 = new Admin("Ian6@email.com","P@$$w0rd");
//        System.out.println(admin1.login());
//        System.out.println(admin1.isAdmin());
//        System.out.println(admin1.isAuthenticated());
    }

}
