package views;

import controllers.*;
import helpers.ACL;
import models.MainModel;
import helpers.MainScreenSwitcher;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Jasper
 * Class: View, shows what the user sees (and gets its info through the controller)
 */
public class MainView extends IpsenView implements ActionListener {

    private MainModel model;
    private MainController controller;

    private JPanel pnlMenuBar;
    private MenuButton btnCustomers = new MenuButton("assets/klanten_default.png", "assets/klanten_hover.png", null);
    private MenuButton btnReservations = new MenuButton("assets/rental_default.png", "assets/rental_hover.png", null);
    private MenuButton btnVehicles = new MenuButton("assets/voertuigen_default.png", "assets/voertuigen_hover.png", null);
    private MenuButton btnManagement = new MenuButton("assets/management_default.png", "assets/management_hover.png", null);
    private MenuButton btnCategories = new MenuButton("assets/categorien_default.png", "assets/categorien_hover.png", null);
    private MenuButton btnLogout = new MenuButton("assets/logout_default.png", "assets/logout_hover.png", null);

    public MainView(MainModel model, MainController controller)
    {
        this.setLayout(new BorderLayout()); //set layout to borderlayout
        this.pnlMenuBar = new JPanel(); //create menubar
        this.pnlMenuBar.setPreferredSize(new Dimension(this.getSize().width, 132));
        this.pnlMenuBar.setOpaque(false);

        this.add(pnlMenuBar, BorderLayout.NORTH); //set our menubar to top;
        this.add(PageScreenSwitcher.getInstance());

        if(ACL.getInstance().hasPermissionFor("CAN_ACCESS_CUSTOMERS"))
            pnlMenuBar.add(this.btnCustomers);

        if(ACL.getInstance().hasPermissionFor("CAN_ACCESS_RESERVATIONS"))
            pnlMenuBar.add(this.btnReservations);

        if(ACL.getInstance().hasPermissionFor("CAN_ACCESS_VEHICLES"))
            pnlMenuBar.add(this.btnVehicles);

        if(ACL.getInstance().hasPermissionFor("CAN_ACCESS_MANAGEMENT"))
            pnlMenuBar.add(this.btnManagement);

        if(ACL.getInstance().hasPermissionFor("CAN_ACCESS_CATEGORIES"))
            pnlMenuBar.add(this.btnCategories);

        pnlMenuBar.add(this.btnLogout);

        this.btnCustomers.addActionListener(this);
        this.btnReservations.addActionListener(this);
        this.btnVehicles.addActionListener(this);
        this.btnManagement.addActionListener(this);
        this.btnCategories.addActionListener(this);
        this.btnLogout.addActionListener(this);

        System.out.println(ACL.getInstance().hasPermissionFor("CAN_ACCESS_CUSTOMERS"));
        //======== start page
        if(ACL.getInstance().hasPermissionFor("CAN_ACCESS_CUSTOMERS"))
        {
            this.deactivateAllbuttons();
            this.btnCustomers.activateButton();

            CustomerController customerController = new CustomerController();
            PageScreenSwitcher.getInstance().switchScreen(customerController.getView());
        }
        else
        {
            PageScreenSwitcher.getInstance().switchScreen(new JPanel()); //empty screen otherwise it would remember rights of previous user
        }
        //========

        this.repaint();

    }

    public void deactivateAllbuttons()
    {
        this.btnCustomers.deactivateButton();
        this.btnReservations.deactivateButton();
        this.btnVehicles.deactivateButton();
        this.btnManagement.deactivateButton();
        this.btnCategories.deactivateButton();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.btnCustomers)
        {
            this.deactivateAllbuttons();
            this.btnCustomers.activateButton();

            CustomerController customerController = new CustomerController();
            PageScreenSwitcher.getInstance().switchScreen(customerController.getView());
        }
        else if(e.getSource() == this.btnReservations)
        {
            this.deactivateAllbuttons();
            this.btnReservations.activateButton();

            ReservationController reservationController = new ReservationController();
            PageScreenSwitcher.getInstance().switchScreen(reservationController.getView());
        }
        else if(e.getSource() == this.btnCategories)
        {
            this.deactivateAllbuttons();
            this.btnCategories.activateButton();

            CategoryController categoryController = new CategoryController();

            PageScreenSwitcher.getInstance().switchScreen(categoryController.getView());
        }
        else if(e.getSource() == this.btnVehicles)
        {
            this.deactivateAllbuttons();
            this.btnVehicles.activateButton();

            VehicleController vehicleController = new VehicleController();
            PageScreenSwitcher.getInstance().switchScreen(vehicleController.getView());
        }
        else if(e.getSource() == this.btnManagement)
        {
            this.deactivateAllbuttons();
            this.btnManagement.activateButton();

//            ReservationController reservationController = new ReservationController();
//            PageScreenSwitcher.getInstance().switchScreen(new StatisticsView(reservationController));

            ManagementController managementController = new ManagementController();
            PageScreenSwitcher.getInstance().switchScreen(managementController.getView());
        }
        else if(e.getSource() == this.btnLogout)
        {
            LoginController loginController = new LoginController();
            MainScreenSwitcher.getInstance().switchScreen(loginController.getView());
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(new ImageIcon(ClassLoader.getSystemResource("assets/menubg.png")).getImage(), 0, 0, null);
        g.drawImage(new ImageIcon(ClassLoader.getSystemResource("assets/menubg.png")).getImage(), 1100, 0, null); //repeat image if larger windows
    }
}
