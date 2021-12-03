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
     * Get the hashed value of input Bytes
     */
    abstract public String getHash(byte[] inputBytes);

    /**
     * Determine if the mode is admin
     */
    public boolean isAdmin() {
        return false;
    }

    /**
     * Determine if the mode is Authenticated
     */
    public boolean isAuthenticated() {
        return false;
    }
}
