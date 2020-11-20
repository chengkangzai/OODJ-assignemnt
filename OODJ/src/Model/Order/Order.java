package Model.Order;

import Model.Interface.Creatable;
import Model.Interface.Updatable;
import Model.Interface.Queryable;
import Model.Interface.Validable;
import Helper.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CCK
 */
public class Order implements Creatable, Updatable, Validable, Queryable {

    private int ID;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime payAt;

    private final Connection reader = new Connection("orders");

    public Order(int ID, Double price, LocalDateTime createdAt, LocalDateTime payAt) {
        this.ID = ID;
        this.price = price;
        this.createdAt = createdAt;
        this.payAt = payAt;
    }

    public Order() {
    }
//No Set ID for the sake of encapsulation !

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

    public Connection getReader() {
        return reader;
    }

    /**
     *
     * @param type
     * @param queryString
     * @return
     */
    @Override
    public Order where(String type, String queryString) {
        int i = 0;
        switch (type.toLowerCase()) {
            case "id":
                i = 0;
                break;
            case "price":
                i = 1;
                break;
            case "createdat":
                i = 2;
                break;
            case "payat":
                i = 3;
                break;
            default:
                System.out.println("Type not specificied");
                break;
        }
        List<String> fromFile = reader.getFromFile();
        for (int j = 1; j < fromFile.size(); j++) {
            String[] split = fromFile.get(j).split(",");
            if (split[i].equals(queryString)) {
                return new Order(
                        Integer.valueOf(split[0]),
                        Double.valueOf(split[1]),
                        LocalDateTime.parse(split[2]),
                        LocalDateTime.parse(split[3]));
            }
        }
        return null;
    }

    /**
     *
     * @param type
     * @param queryOperator
     * @param queryString
     * @return
     */
    @Override
    public ArrayList<Order> where(String type, String queryOperator, String queryString) {
        int i = 0;
        switch (type.toLowerCase()) {
            case "id":
                i = 0;
                break;
            case "price":
                i = 1;
                break;
            case "createdat":
                i = 2;
                break;
            case "payat":
                i = 3;
                break;
            default:
                System.out.println("Type not specificied");
                break;
        }

        ArrayList<Order> temp = new ArrayList();
        List<String> fromFile = reader.getFromFile();

        if ((i == 0) || (i == 1)) {
            int query = Integer.valueOf(queryString);
            for (int j = 1; j < fromFile.size(); j++) {
                String[] split = fromFile.get(j).split(",");
                Double queryInFile = Double.valueOf(split[i]);
                switch (queryOperator.toLowerCase()) {
                    case ">":
                        if (queryInFile > query) {
                            temp.add(new Order(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]),
                                    LocalDateTime.parse(split[2]),
                                    LocalDateTime.parse(split[3])
                            ));
                        }
                        break;
                    case ">=":
                        if (queryInFile >= query) {
                            temp.add(new Order(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]),
                                    LocalDateTime.parse(split[2]),
                                    LocalDateTime.parse(split[3])
                            ));
                        }
                        break;
                    case "<":
                        if (queryInFile < query) {
                            temp.add(new Order(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]),
                                    LocalDateTime.parse(split[2]),
                                    LocalDateTime.parse(split[3])
                            ));
                        }
                        break;
                    case "<=":
                        if (queryInFile <= query) {
                            temp.add(new Order(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]),
                                    LocalDateTime.parse(split[2]),
                                    LocalDateTime.parse(split[3])
                            ));
                        }
                        break;
                    case "=":
                    case "==":
                    case "===":
                        if (queryInFile == query) {
                            temp.add(new Order(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]),
                                    LocalDateTime.parse(split[2]),
                                    LocalDateTime.parse(split[3])
                            ));
                        }
                        break;
                }
            }
        }
        if ((i == 2) || (i == 3)) {
            LocalDateTime queryTime = LocalDateTime.parse(queryString);
            for (int j = 1; j < fromFile.size(); j++) {
                String[] split = fromFile.get(j).split(",");
                LocalDateTime fileTime = LocalDateTime.parse(split[i]);
                switch (queryOperator.toLowerCase()) {
                    case ">":
                        if (fileTime.isAfter(queryTime)) {
                            temp.add(new Order(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]),
                                    LocalDateTime.parse(split[2]),
                                    LocalDateTime.parse(split[3])
                            ));
                        }

                        break;
                    case ">=":
                        if (fileTime.isAfter(queryTime) || fileTime.isEqual(queryTime)) {
                            temp.add(new Order(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]),
                                    LocalDateTime.parse(split[2]),
                                    LocalDateTime.parse(split[3])
                            ));
                        }
                        break;
                    case "<":
                        if (fileTime.isBefore(queryTime)) {
                            temp.add(new Order(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]),
                                    LocalDateTime.parse(split[2]),
                                    LocalDateTime.parse(split[3])
                            ));
                        }
                        break;
                    case "<=":
                        if (fileTime.isBefore(queryTime) || fileTime.isEqual(queryTime)) {
                            temp.add(new Order(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]),
                                    LocalDateTime.parse(split[2]),
                                    LocalDateTime.parse(split[3])
                            ));
                        }
                        break;
                    case "=":
                    case "==":
                    case "===":
                        if (fileTime.isEqual(queryTime)) {
                            temp.add(new Order(
                                    Integer.valueOf(split[0]),
                                    Double.valueOf(split[1]),
                                    LocalDateTime.parse(split[2]),
                                    LocalDateTime.parse(split[3])
                            ));
                        }
                        break;
                }
            }
        }
        return temp;
    }

    @Override
    public ArrayList<Order> all() {
        return this.where("id", ">=", "1");
    }

    /**
     *
     * Create one Order
     *
     * @return
     */
    @Override
    public boolean create() {
        List<String> fromFile = reader.getFromFile();
        this.createdAt = LocalDateTime.now();
        fromFile.add(this.format(true));
        return reader.reWrite(reader.listToString(fromFile));
    }

    /**
     *
     * Update Order
     *
     * @return
     */
    @Override
    public boolean update() {
        List<String> fromFile = reader.getFromFile();
        fromFile.set(this.getID(), this.format(false));
        return reader.reWrite(reader.listToString(fromFile));
    }

    private String format(boolean isCreating) {
        return isCreating
                ? reader.getNewID() + "," + this.price + "," + this.createdAt + "," + this.payAt
                : this.getID() + "," + this.price + "," + this.createdAt + "," + this.payAt;
    }

}
