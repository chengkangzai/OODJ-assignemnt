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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CCK
 */
public class Delivery {

    private int ID;
    private Double weight;
    private String address;
    // "delivered","pending"
    private String status;
    private DeliveryStaff sendBy;
    private LocalDateTime sendOn;

    private final Connection reader = new Connection("db/deliveries.txt");
    private final Validator valid = new Validator();

    public Delivery(int ID) {
        this.ID = ID;
    }

    public Delivery() {
    }

    public Delivery(int ID, Double weight, String address, String status, DeliveryStaff sendBy, LocalDateTime sendOn) {
        this.ID = ID;
        this.weight = weight;
        this.address = address;
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

    /**
     * Get certain Delivery by using where statement like laravel
     * 
     * @param type
     * @param queryString
     * @return
     */
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
                if (split[3].equals("pending")) {
                    return new Delivery(
                            Integer.valueOf(split[0]),
                            Double.valueOf(split[1]), split[2], split[3],
                            new DeliveryStaff(),
                            LocalDateTime.parse(split[5]));

                } else if (split[3].equals("delivered")) {
                    return new Delivery(
                            Integer.valueOf(split[0]),
                            Double.valueOf(split[1]), split[2], split[3],
                            new DeliveryStaff().where("user_id", split[4]),
                            LocalDateTime.parse(split[5]));
                }
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
            case "sendon":
                i = 5;
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
                        if (fileQuery > query && statusInFile.equals("pending")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff(),
                                    LocalDateTime.parse(split[5])));
                        } else if (fileQuery > query && statusInFile.equals("delivered")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5])));
                        }
                        break;
                    case ">=":
                        if (fileQuery >= query && statusInFile.equals("pending")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff(),
                                    LocalDateTime.parse(split[5])));
                        } else if (fileQuery >= query && statusInFile.equals("delivered")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5])));
                        }
                        break;
                    case "<":
                        if (fileQuery < query && statusInFile.equals("pending")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff(),
                                    LocalDateTime.parse(split[5])));
                        } else if (fileQuery < query && statusInFile.equals("delivered")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5])));
                        }
                        break;
                    case "<=":
                        if (fileQuery <= query && statusInFile.equals("pending")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff(),
                                    LocalDateTime.parse(split[5])));
                        } else if (fileQuery <= query && statusInFile.equals("delivered")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5])));
                        }
                        break;
                    case "=":
                    case "==":
                    case "===":
                        if (fileQuery.equals(query) && statusInFile.equals("pending")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff(),
                                    LocalDateTime.parse(split[5])));
                        } else if (fileQuery.equals(query) && statusInFile.equals("delivered")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5])));
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

                        if (fileTime.isAfter(queryTime) && statusInFile.equals("pending")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff(),
                                    LocalDateTime.parse(split[5])));
                        } else if (fileTime.isAfter(queryTime) && statusInFile.equals("delivered")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5])));
                        }
                        break;

                    case ">=":

                        if ((fileTime.isAfter(queryTime) || fileTime.isEqual(queryTime)) && statusInFile.equals("pending")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff(),
                                    LocalDateTime.parse(split[5])));
                        } else if ((fileTime.isAfter(queryTime) || fileTime.isEqual(queryTime)) && statusInFile.equals("delivered")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5])));
                        }
                        break;
                    case "<":

                        if (fileTime.isBefore(queryTime) && statusInFile.equals("pending")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff(),
                                    LocalDateTime.parse(split[5])));
                        } else if (fileTime.isBefore(queryTime) && statusInFile.equals("delivered")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5])));
                        }
                        break;
                    case "<=":
                        if ((fileTime.isBefore(queryTime) || fileTime.isEqual(queryTime)) && statusInFile.equals("pending")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff(),
                                    LocalDateTime.parse(split[5])));
                        } else if ((fileTime.isBefore(queryTime) || fileTime.isEqual(queryTime)) && statusInFile.equals("delivered")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5])));
                        }

                        break;
                    case "=":
                    case "==":
                    case "===":
                        if (fileTime.isEqual(queryTime) && statusInFile.equals("pending")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff(),
                                    LocalDateTime.parse(split[5])));
                        } else if (fileTime.isEqual(queryTime) && statusInFile.equals("delivered")) {
                            temp.add(new Delivery(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]), split[2], split[3],
                                    new DeliveryStaff().where("user_id", split[4]),
                                    LocalDateTime.parse(split[5])));
                        }
                        break;
                }
            }
        }
        return temp;
    }

    /**
     * Create Delivery
     * @return
     */
    public Boolean create() {
        if (!(status.equals("pending")||status.equals("delivered"))) {
            return null;
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
    public Boolean update() {
        if (!(status.equals("pending")||status.equals("delivered"))) {
            return null;
        }
        List<String> fromFile = reader.getFromFile();
        fromFile.set(this.ID, this.format(false));
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
        return isCreating
                ? reader.getNewID() + "," + this.weight + "," + reader.comma2Pipe(this.address) + "," + this.status + "," + this.sendBy.getId() + "," + this.sendOn
                : this.ID + "," + this.weight + "," + reader.comma2Pipe(this.address) + "," + this.status + "," + this.sendBy.getId() + "," + this.sendOn;
    }

    public static void main(String[] args) {
        //Update
//        Delivery d = new Delivery().where("id", "1");
//        d.setAddress("1, Pasar Besar Cheras, Jln Cheras, Batu 3 1/2, 56000, Wilayah Persekutuan");
//        d.setWeight(2.00);
//        d.setSendOn(LocalDateTime.now());
//        d.update();
//delivered
//        Delivery d = new Delivery().where("id", "2");
//        System.out.println(d.getSendBy().getName());
//        DeliveryStaff staff = new DeliveryStaff().where("user_id", "2");
//        d.deliverBy(staff);
//        System.out.println(d.getSendBy().getName());
//create
//        Delivery d = new Delivery();
//        d.setAddress("1, Pasar Besar Cheras, Jln Cheras, Batu 3 1/2, 56000, Wilayah Persekutuan");
//        d.setWeight(Math.random()*100);
//        d.setStatus("pending");
//        d.setSendBy(new DeliveryStaff());
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
        
    }
}
