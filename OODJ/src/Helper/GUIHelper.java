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
                "Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

        switch (showConfirmDialog) {
            case 0 -> System.exit(0);
            case 1, 2 -> parent.setVisible(false);
        }
    }
}
