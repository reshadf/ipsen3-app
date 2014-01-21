package views;

import controllers.*;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Floris
 * Class: View, shows what the user sees (and gets its info through the controller)
 */
public class ManagementView extends IpsenView implements ActionListener{

    private JPanel topPanel;
    private MenuButton btnRightsGroups;
    private MenuButton btnAccounts;
    private MenuButton btnStatistics;

    private ManagementController controller;

    public ManagementView(ManagementController controller)
    {
        this.controller = controller;

        this.setLayout(new BorderLayout());
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(1000, 68));

        btnRightsGroups = new MenuButton("assets/rechtengroepen.png",null, null);
        btnRightsGroups.addActionListener(this);

        btnAccounts = new MenuButton("assets/accounts.png",null, null);
        btnAccounts.addActionListener(this);

        btnStatistics = new MenuButton("assets/statistiek_inzien.png",null, null);
        btnStatistics.addActionListener(this);

        this.topPanel.add(btnRightsGroups);
        this.topPanel.add(btnAccounts);
        this.topPanel.add(btnStatistics);

        this.add(topPanel, BorderLayout.NORTH);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnRightsGroups)
        {
            RightGroupController rightGroupController = new RightGroupController();                    //New instance of RightGroupController
            PageScreenSwitcher.getInstance().switchScreen(rightGroupController.getView());   //Switch screen to AccessRightGroupView
        }
        if(e.getSource() == btnAccounts)
        {
            AccountController accountController = new AccountController();                    //New instance of AccountController
            PageScreenSwitcher.getInstance().switchScreen(accountController.getView());   //Switch screen to AccessAccountView
        }
        if(e.getSource() == btnStatistics)
        {
            System.out.println("Statistics");
            ReservationController reservationController = new ReservationController();                    //New instance of RightGroupController
            PageScreenSwitcher.getInstance().switchScreen(new StatisticsView(reservationController));   //Switch screen to AccessRightGroupView

        }


    }
}
