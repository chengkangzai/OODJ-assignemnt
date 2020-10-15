/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author CCK
 */
public interface Queryable {

    public Object where(String type, String queryOperator, String queryString);

    public Object where(String type, String queryString);
}
