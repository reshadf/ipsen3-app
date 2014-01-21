package views;

import controllers.CustomerController;
import helpers.ACL;
import models.CustomerModel;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * User: Jasper
 * Class: View, shows what the user sees (and gets its info through the controller)
 */

public class CustomerView extends IpsenView implements ActionListener
{
    private CustomerController controller; //controller
    private JTable table;
    private JButton btnNewCustomer;
    private JButton btnDeleteCustomer;
    private JButton btnEditCustomer;

    private JPanel topPanel;

    /**
     * Constructor with controller
     * @param controller
     */

    public CustomerView(CustomerController controller) {
        this.controller = controller; //set controller

        this.setLayout(new BorderLayout());
        topPanel = new JPanel();

        btnNewCustomer = new MenuButton("assets/nw_customer_btn.png"); //add vehicle knop of zoals Reshad zou zeggen vihicle
        btnEditCustomer = new MenuButton("assets/edit_customer_btn.png"); //edit this bastard
        btnDeleteCustomer = new MenuButton("assets/del_customer_btn.png"); //remove this ugly car

        btnNewCustomer.addActionListener(this);
        btnDeleteCustomer.addActionListener(this);
        btnEditCustomer.addActionListener(this);

        if(ACL.getInstance().hasPermissionFor("CAN_CREATE_CUSTOMER")) //if user has rights to do this
            this.topPanel.add(btnNewCustomer);

        if(ACL.getInstance().hasPermissionFor("CAN_EDIT_CUSTOMER"))
            this.topPanel.add(btnEditCustomer);

        if(ACL.getInstance().hasPermissionFor("CAN_DELETE_CUSTOMER"))
            this.topPanel.add(btnDeleteCustomer);

        this.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Fill table with data
     */

    public void init() {
        String[] columnNames =
        {
                "#",
                "E-mail",
                "Voornaam",
                "Tussenvoegsel",
                "Achternaam",
                "Bedrijf",
                "kvknr",
                "Telefoon",
                "Adres",
                "Postcode",
                "Plaats",
                "Geboortedatum",
                "Paspoortnummer"
        }; //column names

        List<CustomerModel> customers = this.controller.getFactory().getCustomers();

        Object[][] data = new Object[customers.size()][13]; //object that will fill the table
        for(int i = 0; i < customers.size(); i++) {
            data[i][0] = new Integer(customers.get(i).getId());
            data[i][1] = customers.get(i).getEmail();
            data[i][2] = customers.get(i).getFirstname();
            data[i][3] = customers.get(i).getInsertion();
            data[i][4] = customers.get(i).getLastname();
            data[i][5] = customers.get(i).getCompany();
            data[i][6] = customers.get(i).getKvknr();
            data[i][7] = customers.get(i).getPhone();
            data[i][8] = customers.get(i).getAddress();
            data[i][9] = customers.get(i).getZip();
            data[i][10] = customers.get(i).getCity();
            data[i][11] = this.controller.convertToDutchDate(customers.get(i).getBirthdate());
            data[i][12] = customers.get(i).getPassportnumber();
        }

        table = new JTable(data, columnNames) {
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }

        }; //new table
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableSP = new JScrollPane(table);

        this.add(tableSP, BorderLayout.CENTER);
    }

    /**
     * Menu button pressed
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == btnNewCustomer)
        { //new cutomer, show view
            //new vehicle
            CustomerEditView customerEditView = new CustomerEditView(this.controller);
            PageScreenSwitcher.getInstance().switchScreen(customerEditView);
        }
        if(s == btnDeleteCustomer)
        { //delete customer
            int confirm = JOptionPane.YES_NO_OPTION;
            confirm = displayMessage(this, 2, "Klant verwijderen?", "Warning", confirm);
            if(confirm == JOptionPane.YES_OPTION) {
                try {
                    CustomerModel model = this.controller.getFactory().getCustomer(Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString()));
                    this.controller.getFactory().delete(model);

                    this.displayMessage(this, 1, "Klant succesvol verwijderd!", "Geslaagd", 0);

                    //reload view
                    CustomerController customerController = new CustomerController();
                    PageScreenSwitcher.getInstance().switchScreen(customerController.getView());
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Exception: "+ex.getMessage());
                }
            }
        }
        if(s == btnEditCustomer)
        {
            //edit customer
            CustomerEditView customerEditView;
            try {
                customerEditView = new CustomerEditView(this.controller, table.getValueAt(table.getSelectedRow(),0));
                PageScreenSwitcher.getInstance().switchScreen(customerEditView);
            } catch(ArrayIndexOutOfBoundsException ex) {
                this.displayMessage(this, 0, "Geen klant geselecteerd!", "Foutmelding", 0);
            }
        }
    }
}
