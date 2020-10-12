/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delivery;

import Helper.Connection;
import Helper.Validator;
import User.DeliveryStaff;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author CCK
 */
public class Delivery {

    int ID;
    Double weight;
    String address;
    String status;
    DeliveryStaff sendBy;
    String sendOn;

    Connection reader = new Connection("db/deliveries.txt");
    Validator valid = new Validator();

    public Delivery(int ID, Double weight, String address, String status, DeliveryStaff sendBy, String sendOn) {
        this.ID = ID;
        this.weight = weight;
        this.address = reader.comma2Pipe(status);
        this.status = status;
        this.sendBy = sendBy;
        this.sendOn = sendOn;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return reader.pipe2Comma(address);
    }

    public void setAddress(String address) {
        this.address = reader.comma2Pipe(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DeliveryStaff getSendBy() {
        return sendBy;
    }

    public void setSendBy(DeliveryStaff sendBy) {
        this.sendBy = sendBy;
    }

    public String getSendOn() {
        return sendOn;
    }

    public void setSendOn(String sendOn) {
        this.sendOn = sendOn;
    }

    public Delivery(int ID) {
        this.ID = ID;
    }

    public Delivery() {
    }

    public Delivery where() {
        return new Delivery();
    }

    public boolean create() {
        List<String> fromFile = reader.getFromFile();
        fromFile.add(this.format(true));
        return reader.reWrite(reader.listToString(fromFile));
    }

    public boolean update() {
        return false;
    }

    public boolean delete() {
        return false;
    }

    public boolean deliver() {
        return false;
    }

    private String format(Boolean isCreating) {
        return isCreating
                ? reader.getNewID() + "," + this.weight + "," + this.getAddress() + "," + this.status + "," + this.sendBy.getId() + "," + this.sendOn
                : this.ID + "," + this.weight + "," + this.getAddress() + "," + this.status + "," + this.sendBy.getId() + "," + this.sendOn;
    }

    public static void main(String[] args) {
        Delivery d = new Delivery();
        d.address = "1 Pasar Besar Cheras Jln Cheras Batu 3 1/2 56000 Wilayah Persekutuan 56000 ";
        d.weight = 2.00;
        d.status = "pending";
        d.sendBy = new DeliveryStaff();
        d.sendOn = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        d.create();
    }
}
