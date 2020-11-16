/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Delivery;

import Model.Interface.Creatable;
import Model.Interface.Updatable;
import Model.Interface.Queryable;
import Model.Interface.Validable;
import Helper.Connection;
import Model.User.DeliveryStaff;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Model.Order.Order;

/**
 *
 * @author CCK
 */
public class Delivery implements Creatable, Updatable, Queryable, Validable {

    private int ID;
    private Double weight;
    private String address;
    // "delivered","pending"
    private String status;
    private DeliveryStaff sendBy;
    private LocalDateTime sendOn;
    private Order order;

    private final Connection reader = new Connection("deliveries");

    public Delivery() {
    }

    public Delivery(int ID, Double weight, String address, String status, DeliveryStaff sendBy, LocalDateTime sendOn, Order order) {
        this.ID = ID;
        this.weight = weight;
        this.address = address;
        this.status = status;
        this.sendBy = sendBy;
        this.sendOn = sendOn;
        this.order = order;
    }

    public int getID() {
        return ID;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public LocalDateTime getSendOn() {
        return sendOn;
    }

    public void setSendOn(LocalDateTime sendOn) {
        this.sendOn = sendOn;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Connection getReader() {
        return reader;
    }

    
    /**
     * Get certain Delivery by using where statement like laravel
     *
     * @param type
     * @param queryString
     * @return
     */
    @Override
    public Delivery where(String type, String queryString) {
        int i = 0;
        switch (type.toLowerCase()) {
            case "id":
                i = 0;
                break;
            case "weight":
                i = 1;
                break;
            case "address":
                i = 2;
                break;
            case "status":
                i = 3;
                break;
            case "sendBy":
                i = 4;
                break;
            case "sendOn":
                i = 5;
                break;
            default:
                System.out.println("Type not specificied");
                break;
        }
        List<String> fromFile = reader.getFromFile();
        for (String element : fromFile) {
            String[] split = element.split(",");
            if (split[i].equals(queryString)) {
                return new Delivery(
                        Integer.valueOf(split[0]),
                        Double.valueOf(split[1]), split[2], split[3],
                        new DeliveryStaff().where("user_id", split[4]),
                        LocalDateTime.parse(split[5]),
                        new Order().where("id", split[6])
                );
            }
        }
        System.out.println("Hoi Error la ! shame on you copying Laravel");
        return null;
    }

    /**
     *
     * Get array of Delivery with where statement
     * https://howtodoinjava.com/java/date-time/compare-localdatetime/
     *
     * @param type
     * @param queryOperator
     * @param queryString
     * @return
     */
    @Override
    public ArrayList<Delivery> where(String type, String queryOperator, String queryString) {
        //Weight, Send On...        
        //< , >, >=, <= 
        int i = 0;
        switch (type.toLowerCase()) {
            case "id":
                i = 0;
                break;
            case "weight":
                i = 1;
                break;
            case "sendby":
                i = 4;
                break;
            case "sendon":
                i = 5;
                break;
            case "order_id":
                i = 6;
                break;
            default:
                System.out.println("Type not specificied or supported\n Support Type : id,weight,sendon");
                break;
        }

        if (!valid.isValidOperator(queryOperator)) {
            System.out.println("Invalid Operator !");
            return null;
        }

        ArrayList<Delivery> temp = new ArrayList();
        List<String> fromFile = reader.getFromFile();

        if (i == 0 || i == 1) {
            Double query = Double.valueOf(queryString);
            for (int j = 1; j < fromFile.size(); j++) {
                String[] split = fromFile.get(j).split(",");
                Double fileQuery = Double.valueOf(split[i]);
                String statusInFile = split[3];
                switch (queryOperator) {
                    case ">":
                        if (fileQuery > query) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5]),
                                    new Order().where("id", split[6])));
                        }
                        break;
                    case ">=":
                        if (fileQuery >= query) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5]),
                                    new Order().where("id", split[6])));
                        }
                        break;
                    case "<":
                        if (fileQuery < query) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5]),
                                    new Order().where("id", split[6])));
                        }
                        break;
                    case "<=":
                        if (fileQuery <= query) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5]),
                                    new Order().where("id", split[6])));
                        }
                        break;
                    case "=":
                    case "==":
                    case "===":
                        if (fileQuery.equals(query) && statusInFile.equals("pending")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5]),
                                    new Order().where("id", split[6])));
                        }
                        break;
                }
            }
        }

        if (i == 5) {
            LocalDateTime queryTime = LocalDateTime.parse(queryString);
            for (int j = 1; j < fromFile.size(); j++) {
                String[] split = fromFile.get(j).split(",");
                LocalDateTime fileTime = LocalDateTime.parse(split[5]);
                String statusInFile = split[3];
                switch (queryOperator.toLowerCase()) {
                    case ">":
                        if (fileTime.isAfter(queryTime)) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5]),
                                    new Order().where("id", split[6])));
                        }
                        break;
                    case ">=":
                        if (fileTime.isAfter(queryTime) || fileTime.isEqual(queryTime)) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5]),
                                    new Order().where("id", split[6])));
                        }
                        break;
                    case "<":
                        if (fileTime.isBefore(queryTime)) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5]),
                                    new Order().where("id", split[6])));
                        }
                        break;
                    case "<=":
                        if (fileTime.isBefore(queryTime) || fileTime.isEqual(queryTime)) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5]),
                                    new Order().where("id", split[6])));
                        }
                        break;
                    case "=":
                    case "==":
                    case "===":
                        if (fileTime.isEqual(queryTime)) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5]),
                                    new Order().where("id", split[6])));
                        }
                        break;
                }
            }
        }
        return temp;
    }

    @Override
    public ArrayList<Delivery> all() {
        return this.where("id", ">=", "1");
    }

    /**
     * Create Delivery
     *
     * @return
     */
    @Override
    public boolean create() {
        if (!(status.equals("pending") || status.equals("delivered"))) {
            return false;
        }
        List<String> fromFile = reader.getFromFile();
        fromFile.add(this.format(true));
        return reader.reWrite(reader.listToString(fromFile));
    }

    /**
     *
     * Update Delivery
     *
     * @return Boolean
     */
    @Override
    public boolean update() {
        if (!(status.equals("pending") || status.equals("delivered"))) {
            return false;
        }
        List<String> fromFile = reader.getFromFile();
        fromFile.set(this.getID(), this.format(false));
        return reader.reWrite(reader.listToString(fromFile));
    }

    /**
     *
     * Mark the delivery is delivered by a delivery Staff
     *
     * @param staff
     * @return
     */
    public Boolean deliverBy(DeliveryStaff staff) {
        this.setStatus("delivered");
        this.setSendBy(staff);
        this.setSendOn(LocalDateTime.now());
        List<String> fromFile = reader.getFromFile();
        fromFile.set(this.ID, this.format(false));
        return reader.reWrite(reader.listToString(fromFile));
    }

    /**
     *
     * Change the address format when only it is setting it to text file
     *
     * @param isCreating
     * @return
     */
    private String format(Boolean isCreating) {
        if (!valid.isValidString(this.status)) {
            return null;
        }
        
        String userId = this.sendBy==null ? "0" : String.valueOf(this.sendBy.getID());
        String orderID = this.order==null ? "0" : String.valueOf(this.order.getID());

        return isCreating
                ? reader.getNewID() + "," + this.weight + "," + reader.comma2Pipe(this.address) + "," + this.status + "," + userId + "," + this.sendOn + "," + orderID
                : this.ID + "," + this.weight + "," + reader.comma2Pipe(this.address) + "," + this.status + "," + userId + "," + this.sendOn + "," + orderID;
    }

    public static void main(String[] args) {
        //Update
        Delivery d = new Delivery().where("id", "1");
        d.setAddress("1, Pasar Besar Gombak, Jln Cheras, Batu 3 1/2, 56000, Wilayah Persekutuan");
        d.setWeight(2.00);
        d.setStatus("pending");
        d.setSendOn(LocalDateTime.now());
        System.out.println(d.getStatus());
        d.update();
//delivered
//        Delivery d = new Delivery().where("id", "2");
//        System.out.println(d.getSendBy().getName());
//        DeliveryStaff staff = new DeliveryStaff().where("user_id", "2");
//        d.deliverBy(staff);
//        System.out.println(d.getSendBy().getName());
//create
//        Delivery d = new Delivery();
//        d.setAddress("1, Pasar Besar Cheras, Jln Cheras, Batu 3 1/2, 56000, Wilayah Persekutuan");
//        d.setWeight(Double.valueOf(Math.round(Math.random() * 100)));
//        d.setStatus("pending");
//        d.setOrder(new Order().where("id", "1"));
//        d.setSendBy(new DeliveryStaff().where("id", "1"));
//        d.setSendOn(LocalDateTime.MIN);
//        d.create();
//queryAll

//        System.out.println(new Delivery().where("id", ">", "1"));
//        System.out.println(new Delivery().where("id", ">=", "1"));
//        System.out.println(new Delivery().where("id", "<", "3"));
//        System.out.println(new Delivery().where("id", "<=", "3"));
//        System.out.println(new Delivery().where("weight", ">", "2"));
//        System.out.println(new Delivery().where("weight", ">=", "2"));
//        System.out.println(new Delivery().where("weight", "<", "28.70"));
//        System.out.println(new Delivery().where("weight", "<=", "28.70"));
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime later = LocalDateTime.now().plusDays(1).plusHours(10);
//        System.out.println(new Delivery().where("sendOn", ">=", LocalDateTime.MIN.toString()));
//        System.out.println(new Delivery().where("sendOn", ">", LocalDateTime.MIN.toString()));
//        System.out.println(new Delivery().where("sendOn", "<", LocalDateTime.MIN.toString()));
        //Query one 
//        Delivery d = new Delivery().where("id", "1");
//        System.out.println(d.getOrder());
    }
}
