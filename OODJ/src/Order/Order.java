package Order;

import Helper.Connection;
import Interfaces.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author CCK
 */
public class Order implements Creatable, Updatable, Validable, Queryable {

    private int ID;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime payAt;

    private final static Connection reader = new Connection("orders");

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
