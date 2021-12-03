package Model.Feedback;

import Model.Interface.Creatable;
import Model.Interface.Queryable;
import Model.Interface.Deletable;
import Model.Interface.Validable;
import Helper.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CCK
 */
public class Feedback implements Creatable, Deletable, Validable, Queryable {

    private int ID;
    private String feedback;

    private final Connection reader = new Connection("feedbacks");

    public Feedback() {
    }

    public Feedback(String feedback) {
        this.feedback = feedback;
    }

    public Feedback(int ID, String feedback) {
        this.ID = ID;
        this.feedback = feedback;
    }

    public Feedback(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Connection getReader() {
        return reader;
    }

    @Override
    public boolean create() {
        List<String> fromFile = reader.getFromFile();
        fromFile.add(this.format());
        return reader.reWrite(reader.listToString(fromFile));
    }

    /**
     * Delete initiated Feedback
     */
    @Override
    public boolean delete() {
        List<String> fromFile = reader.getFromFile();
        fromFile.remove(ID);
        return reader.reWrite(reader.listToString(fromFile));
    }

    /**
     * Get Feedback by using where statement
     */
    @Override
    public Feedback where(String type, String queryString) {
        int i = 0;
        switch (type.toLowerCase()) {
            case "id" -> i = 0;
            case "feedback" -> i = 1;
            default -> System.out.println("Type not specificied");
        }

        List<String> fromFile = reader.getFromFile();
        for (String element : fromFile) {
            String[] split = element.split(",");
            if (split[i].equals(queryString)) {
                return new Feedback(Integer.parseInt(split[0]), split[1]);
            }
        }
        return null;
    }

    /**
     * Get Feedback by using where statement
     */
    @Override
    public ArrayList<Feedback> where(String type, String queryOperator, String queryString) {
        if (!valid.isValidOperator(queryOperator)) {
            return null;
        }
        if (!type.equals("id")) {
            return null;
        }

        ArrayList<Feedback> temp = new ArrayList<>();
        int queryID = Integer.parseInt(queryString);
        List<String> fromFile = reader.getFromFile();

        for (int i = 1; i < fromFile.size(); i++) {
            String[] split = fromFile.get(i).split(",");
            int idFile = Integer.parseInt(split[0]);
            switch (queryOperator) {
                case ">":
                    if (idFile > queryID) {
                        temp.add(new Feedback(Integer.parseInt(split[0]), split[1]));
                    }
                    break;
                case ">=":
                    if (idFile >= queryID) {
                        temp.add(new Feedback(Integer.parseInt(split[0]), split[1]));
                    }
                    break;
                case "<":
                    if (idFile < queryID) {
                        temp.add(new Feedback(Integer.parseInt(split[0]), split[1]));
                    }
                    break;
                case "<=":
                    if (idFile <= queryID) {
                        temp.add(new Feedback(Integer.parseInt(split[0]), split[1]));
                    }
                    break;
            }
        }
        return temp;
    }

    @Override
    public ArrayList<Feedback> all() {
        return this.where("id", ">=", "1");
    }

    private String format() {
        if (!valid.isValidString(feedback)) {
            return null;
        }
        return reader.getNewID() + "," + feedback;
    }

}
