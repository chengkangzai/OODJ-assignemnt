/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Feedback;

import Helper.Connection;
import Helper.Validator;
import java.util.List;

/**
 *
 * @author CCK
 */
public class Feedback {

    private int ID;
    private String feedback;
    
    private final Connection reader = new Connection("db/feedbacks.txt");
    private final Validator valid = new Validator();

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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     *
     * @return
     */
    public boolean create() {
        List<String> fromFile = reader.getFromFile();
        fromFile.add(this.format());
        return reader.reWrite(reader.listToString(fromFile));
    }

    /**
     *
     * Delete initiated Feedback
     *
     * @return
     */
    public boolean delete() {
        List<String> fromFile = reader.getFromFile();
        fromFile.remove(ID);
        return reader.reWrite(reader.listToString(fromFile));
    }

    /**
     *
     * Get Feedback by using where statement
     *
     * @param type
     * @param queryString
     * @return
     */
    public Feedback where(String type, String queryString) {
        int i = 0;
        switch (type.toLowerCase()) {
            case "id":
                i = 0;
                break;
            case "feedback":
                i = 1;
                break;
            default:
                System.out.println("Type not specificied");
                break;
        }

        List<String> fromFile = reader.getFromFile();
        for (String element : fromFile) {
            String[] split = element.split(",");
            if (split[i].equals(queryString)) {
                return new Feedback(Integer.valueOf(split[0]), split[1]);
            }
        }
        return null;
    }

    private String format() {
        if (!valid.isValidString(feedback)) {
            return null;
        }
        return reader.getNewID() + "," + feedback;
    }

}
