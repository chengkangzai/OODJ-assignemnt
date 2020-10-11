/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Feedback;

import Helper.Connection;
import Helper.Validator;

/**
 *
 * @author CCK
 */
public class Feedback {

    String feedback;
    Connection reader = new Connection("feedback.txt");
    Validator valid = new Validator();

    public Feedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void create() {
        
    }

}
