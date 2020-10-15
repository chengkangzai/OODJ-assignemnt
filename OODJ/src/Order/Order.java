package Order;

import Helper.Connection;
import Helper.Validator;
import Interfaces.Queryable;
import Interfaces.Validable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import Interfaces.Creatable;
import Interfaces.Updatable;

/**
 *
 * @author CCK
 */
public class Order implements Creatable, Updatable, Validable, Queryable {

    int ID;
    Double price;
    LocalDateTime createdAt;
    LocalDateTime payAt;

    Connection reader = new Connection("db/order.txt");
    Validator valid = new Validator();

    public Order(Integer ID, Double price, LocalDateTime createdAt, LocalDateTime payAt) {
        this.ID = ID;
        this.price = price;
        this.createdAt = createdAt;
        this.payAt = payAt;
    }

    public Order() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPayAt() {
        return payAt;
    }

    public void setPayAt(LocalDateTime payAt) {
        this.payAt = payAt;
    }

    public Order where(String type, String queryString) {
        return new Order();
    }

    public ArrayList<Order> where(String type, String queryOperator, String queryString) {
        return new ArrayList<>();
    }

    public boolean create() {
        return true;
    }

    public boolean update() {
        return true;
    }

    public boolean delete() {
        return true;
    }

}
