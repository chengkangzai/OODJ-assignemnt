/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Interface;

/**
 *
 * @author CCK
 */
public interface Queryable {

    /**
     *
     * Model should be queryable by where operator
     *
     * @param type
     * @param queryOperator
     * @param queryString
     * @return
     */
    public Object where(String type, String queryOperator, String queryString);

    /**
     *
     * Model should be queryable by where
     *
     * @param type
     * @param queryString
     * @return
     */
    public Object where(String type, String queryString);

    /**
     *
     * Model should be get All
     * 
     * @return
     */
    public Object all();
}
