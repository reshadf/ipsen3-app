package views;

import javax.swing.*;

/**
 * User: Chris
 * Class: Used as super class for every view, contains global methods used by more than just 1 view
 * Add more methods along the way.
 * */
abstract class IpsenView extends JPanel {

    /**
     *  Constructor for the class
     */
    public IpsenView() {

    }

    /**
     * Method to give an alert to the user
     * type 0 : For error messages
     * type 1 : For success messages
     * type 2 : For warning messages - confirm
     */
    protected int displayMessage(JPanel frame, int type, String message, String title, int confirm) {
        int dialogResult = 0;

        switch(type) {
            case 0 :
                JOptionPane.showMessageDialog(frame,
                        message,
                        title,
                        JOptionPane.ERROR_MESSAGE);
                break;
            case 1 :
                JOptionPane.showMessageDialog(frame,
                        message,
                        title,
                        JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2 :
                dialogResult = JOptionPane.showConfirmDialog (frame,
                        message,
                        title,
                        JOptionPane.WARNING_MESSAGE,
                        confirm);
                break;
        }
        return dialogResult;
    }
}
