package views;

import controllers.RightController;
import controllers.RightGroupController;
import helpers.*;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * User: Reinier Koops
 * Class: A view that gives representation of rights
 */

public class AccessRightView extends IpsenView implements ActionListener
{
    private RightController controller;

    private MenuButton btnBack, btnSave;
    private JPanel topPanel;
    private RightPane rightPane;

    private int groupId = 0;

    /**
     * Class Constructor
     * @param controller Right Controller
     */

    public AccessRightView(RightController controller) //controller
    {
        this.controller = controller;

        this.setLayout( new BorderLayout() );
        topPanel = new JPanel();
        topPanel.setPreferredSize( new Dimension(1000, 68) );

        btnBack = new MenuButton("assets/terug_naar_overzicht.png");
        btnSave = new MenuButton("assets/save.png");
        btnBack.addActionListener(this);
        btnSave.addActionListener(this);
        topPanel.add(btnBack);
        topPanel.add(btnSave);

        this.topPanel.add( btnBack );
        this.topPanel.add( btnSave );

        this.add(topPanel, BorderLayout.NORTH);
    }
    /**
     * Method to init all the elements in the view, this method is called later by the controller
     */
    public void init(Object... args) {

        this.groupId = Integer.valueOf(args[0].toString());

        Dimension viewSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, viewSize.width, viewSize.height);

        setVisible(true);

        java.util.List<RightModel> rights = this.controller.getFactory().getRights();

        this.rightPane = new RightPane(rights);
        this.add(rightPane);

        ArrayList<Integer> temp = this.controller.getFactory().getRight2GroupList(this.groupId);
        this.rightPane.fillCheckboxes(temp);
    }
    /**
     * Method to actionPerformed changes the view, by using the controller and displaying the next view
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        if(e.getSource() == this.btnBack) //back
        {
            RightGroupController rightGroupController = new RightGroupController();          //New instance of rightcontroller
            PageScreenSwitcher.getInstance().switchScreen(rightGroupController.getView());   //Switch screen to AccessRightView
        }
        else if(e.getSource() == this.btnSave)
        {
            this.controller.save(this.groupId, this.rightPane.getSelectedCheckboxes(), this.rightPane.getUnSelectedCheckboxes());

            MessageQueue.getInstance().addMessage("Rechten succesvol opgeslagen!", 0);

            String messageSet = "";
            for(Message message : MessageQueue.getInstance().getMessages()) {
                messageSet += message.toString();
            }
            MessageQueue.getInstance().clearQueue();

            displayMessage(this, 1, messageSet, "Geslaagd!", 1);


            RightGroupController rightGroupController = new RightGroupController();          //New instance of rightcontroller
            PageScreenSwitcher.getInstance().switchScreen(rightGroupController.getView());   //Switch screen to AccessRightView
        }

    }

}
