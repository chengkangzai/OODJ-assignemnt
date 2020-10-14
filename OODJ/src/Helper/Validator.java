/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.util.regex.Pattern;

/**
 *
 * @author CCK
 */
public class Validator {

    public Validator() {
    }

    /**
     *
     * Validate the string is valid or not
     *
     * @param input
     * @return true is the string is not empty and not containing coma(,)
     */
    public boolean isValidString(String input) {
        return !(input == null || input.isEmpty() || input.contains(","));
    }

    /**
     *
     * Validate the email is valid or not
     *
     * @param email
     * @return true if the input string is not empty and is a email
     */
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches() && isValidString(email);
    }

    public boolean isValidOperator(String queryOperator) {
        return queryOperator.contains(">") || queryOperator.contains("<") || queryOperator.contains(">=") || queryOperator.contains("<=") || queryOperator.contains("=") || queryOperator.contains("==") || queryOperator.contains("===");
    }
}
