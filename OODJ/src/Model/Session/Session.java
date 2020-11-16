/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Session;

import Helper.Connection;
import Model.User.DeliveryStaff;
import Model.User.ManagingStaff;
import Model.User.User;

/**
 *
 * @author CCK
 */
public class Session {

    Connection session = new Connection("users/session");

    public Session() {
    }

    public void setUser(String userId) {
        session.reWrite(userId);
    }

    public User getUser() {
        return new User().where("id", this.getID());
    }

    public DeliveryStaff getDeliveryStaff() {
        return new DeliveryStaff().where("user_id", this.getID());
    }

    public ManagingStaff getManagingStaff() {
        return new ManagingStaff().where("user_id", this.getID());
    }

    private String getID() {
        return session.getFromFile().get(0);
    }

}
