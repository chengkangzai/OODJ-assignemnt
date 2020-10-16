/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Interfaces.*;

/**
 *
 * @author CCK
 */
abstract public class Model implements Creatable, Updatable, Queryable, Validable {

    abstract public String getHash(byte[] inputBytes);

    public boolean isAdmin() {
        return false;
    }

    public boolean isAuthenticated() {
        return false;
    }
}
