/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author CCK
 */
public class GUIHelper {

    public GUIHelper() {
    }

    public void showMessageBox(String message) {
        final JFrame parent = new JFrame();
        parent.setVisible(true);
        JOptionPane.showMessageDialog(parent, message, "Pop Up ", 1);
        parent.setVisible(false);
    }

    public void showWarningBox(String message) {
        final JFrame parent = new JFrame();
        parent.setVisible(true);
        JOptionPane.showMessageDialog(parent, message, "Pop Up ", 0);
        parent.setVisible(false);
    }

    /**
     * Ask the customer really want to exit ?
     */
    public void exitSoftware() {
        final JFrame parent = new JFrame();
        final int showConfirmDialog = JOptionPane.showConfirmDialog(parent, "Are you sure you want to exit ?",
                "Exit Confirmation", 1);

        switch (showConfirmDialog) {
            case 0:
                System.exit(0);
                break;
            case 1:
            case 2:
                parent.setVisible(false);
                break;
        }
    }
}
