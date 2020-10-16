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

    /**
     *
     * Get the hashed value of input Bytes
     *
     * @param inputBytes
     * @return
     */
    abstract public String getHash(byte[] inputBytes);

    /**
     *
     * Determine if the mode is admin
     *
     * @return
     */
    public boolean isAdmin() {
        return false;
    }

    /**
     *
     * Determine if the mode is Authenticated
     *
     * @return
     */
    public boolean isAuthenticated() {
        return false;
    }
}
