/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.management;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ashwe
 */
public class DeliveryManager {
    private List<Delivery> listofDelivery;
    
    public DeliveryManager() {
        this.listofDelivery = new ArrayList<>();
    }
    
    public int addDelivery(Delivery d){
        this.listofDelivery.add(d);
        return this.listofDelivery.size();
    
}
    
    public int count () {
        return this.listofDelivery.size();
        
    }
    
    public Delivery getDelivery(int index) {
        if (index < 0 || index >= count()){
            return null;
        
        }
        return this.listofDelivery.get (index);

    }

    boolean removeDelivery(int id) {
       int index = -1;
       for (int i = 0, n = count(); i< n; i++) {
           if(this.listofDelivery.get(i).getId() == id) {
               index = i;
               break;
           }
       }
       if(index != -1) {
           this.listofDelivery.remove(index);
           return true;
       }
       return false;
    }
}