package views;

import controllers.LoginController;
import controllers.MainController;
import helpers.Message;
import helpers.MessageQueue;
import helpers.MainScreenSwitcher;
import helpers.MenuButton;
import helpers.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Jasper
 * Class: Give the visitor the possibility to log in
 * Displays a form
 */
public class LoginView extends IpsenView implements ActionListener {

    private LoginController controller;

    private JLabel lblEmpty = new JLabel("");
    private JLabel lblUsername = new JLabel("Username");
    private JTextField txtUsername = new JTextField(10);
    private  JLabel lblPassword = new JLabel("Password");
    private  JTextField txtPassword = new JTextField(10);
    private  JButton btnLogin = new MenuButton("assets/login.png", null, null);

    /**
     *
     *
     * @param controller
     */
    public LoginView(LoginController controller)
    {
        this.controller = controller;

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(new SpringLayout());
        centerPanel.setPreferredSize(new Dimension(410, 200));

        centerPanel.add(this.lblUsername);
        this.lblUsername.setLabelFor(this.txtUsername);
        centerPanel.add(this.txtUsername);

        centerPanel.add(this.lblPassword);
        this.lblPassword.setLabelFor(this.txtPassword);
        centerPanel.add(this.txtPassword);

        centerPanel.add(this.lblEmpty);
        this.lblEmpty.setLabelFor(this.btnLogin);
        centerPanel.add(this.btnLogin);

        this.btnLogin.addActionListener(this);
        this.txtUsername.addActionListener(this); //on hit enter key
        this.txtPassword.addActionListener(this); //on hit enter key

        SpringUtilities.makeCompactGrid(centerPanel,
                3, 2,    //rows, cols
                50, 10,  //initX, initY
                50, 10); //xPad, yPad

        this.add(centerPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(this.controller.login(this.txtUsername.getText(), this.txtPassword.getText()))
        {
            MainController mainController = new MainController();
            MainScreenSwitcher.getInstance().switchScreen(mainController.getView());
        }
        else
        {
            String messageSet = "";
            for(Message message : MessageQueue.getInstance().getMessages()) {
                messageSet += message.toString();
            }
            MessageQueue.getInstance().clearQueue();

            displayMessage(this, 0, messageSet, "Inloggen mislukt", 0);
        }
    }
}