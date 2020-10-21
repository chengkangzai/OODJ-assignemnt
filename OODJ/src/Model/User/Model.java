/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.User;

import Model.Interface.Creatable;
import Model.Interface.Updatable;
import Model.Interface.Queryable;
import Model.Interface.Validable;

/**
 *
 * @author CCK
 */
abstract public class Model implements Queryable, Creatable, Updatable, Validable {

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
