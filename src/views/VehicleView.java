package views;

import controllers.CheckupController;
import controllers.VehicleController;
import helpers.ACL;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;
import models.VehicleModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * User: Reinier
 * Class: View to show all the Vehicles
 */
public class VehicleView extends IpsenView implements ActionListener
{
    private VehicleController controller;
    private JTable table;

    private JButton btnNewVehicle = new MenuButton("assets/nw_vehicle_btn.png", null, null); //the four lines of code mean to link images to the buttons
    private JButton btnDeleteVehicle = new MenuButton("assets/del_vehicle_btn.png", null, null);
    private JButton btnEditVehicle = new MenuButton("assets/edit_vehicle_btn.png", null, null);
    private JButton btnTakeIn = new MenuButton("assets/innames.png", null, null);

    private JPanel topPanel;

    /**
     * Class Constructor
     * @param controller Vehicle Controller
     */

    public VehicleView(VehicleController controller) {         // links the controller with the vehicle view
        this.controller = controller;

        this.setLayout(new BorderLayout());                   // sets the layout for this menu (how the buttons and content are placed)
        topPanel = new JPanel();                              // border layout splits the program into tiles like PAGE_END, LINE_START, LINE_END, CENTER
                                                                // NORTH, SOUTH etc also possible
        btnNewVehicle.addActionListener(this);
        btnDeleteVehicle.addActionListener(this);            // this adds an actionlistener to the available buttons in the menu which
        btnEditVehicle.addActionListener(this);              // which in turn gives back a response by fulfilling an action the button is linked to.
        btnTakeIn.addActionListener(this);

        if(ACL.getInstance().hasPermissionFor("CAN_CREATE_VEHICLE"))            // these four if statements check if the logged in person has
            this.topPanel.add(btnNewVehicle);                                   // rights to access the following commands like: creating a vehicle in the menu etc.

        if(ACL.getInstance().hasPermissionFor("CAN_EDIT_VEHICLE"))
            this.topPanel.add(btnEditVehicle);

        if(ACL.getInstance().hasPermissionFor("CAN_DELETE_VEHICLE"))
            this.topPanel.add(btnDeleteVehicle);

        if(ACL.getInstance().hasPermissionFor("CAN_ACCESS_CHECKUPS"))
            this.topPanel.add(btnTakeIn);

        this.add(topPanel, BorderLayout.NORTH);             // as listed before this layout means for the buttons that those are shown north.
    }
    /**
     * Method to init all the elements in the view, this method is called later by the controller
     */
    public void init() {
        String[] columnNames =
                {
                        "#",
                        "Merk",
                        "Type",                                   //these are the names for the columns which are shown in the vehicle menu in the java-application
                        "Nummerbord",
                        "Dagtarief",
                        "Categorie"
                };

        List<VehicleModel> vehicles = this.controller.getFactory().getVehicles();    // this commands makes it able for us to call the controller which in turn
                                                                                     // gives us the available information stated in the database
        Object[][] data = new Object[vehicles.size()][6];
        for(int i = 0; i < vehicles.size(); i++) {
            data[i][0] = new Integer(vehicles.get(i).getId());
            data[i][1] = vehicles.get(i).getBrand();
            data[i][2] = vehicles.get(i).getType();
            data[i][3] = vehicles.get(i).getLicenseplate();
            data[i][4] = new String("€ " + (new Double(vehicles.get(i).getHourly_rent())));

            if(vehicles.get(i).getCategory_id() == 0)
            {
                data[i][5] = "";
            }
            else
            {
                data[i][5] = this.controller.getCategoryFactory().getCategory(vehicles.get(i).getCategory_id()).getName();    //makes it able to select a category from another table in the database
            }
        }

        table = new JTable(data, columnNames) {
            public boolean isCellEditable(int row, int col)
            {
                return false;               // this disables the option to change the table while it is shown as a summary in the main menu of the relevant menu.
            }

        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableSP = new JScrollPane(table);

        this.add(tableSP, BorderLayout.CENTER);           // as listed before this layout means that the table information is shown in the center of the window.
    }
    /**
     * Method to actionPerformed changes the view, by using the controller and displaying the next view
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == btnNewVehicle) {
            //new vehicle = if you "bash" the button "Nieuw voertuig aanmaken", this comment underneath will redirect you to the menu of creating a new car.
            VehicleEditView vehicleEditView = new VehicleEditView(this.controller);
            PageScreenSwitcher.getInstance().switchScreen(vehicleEditView);
        }
        if(s == btnDeleteVehicle) {
            //delete vehicle = click this button ("voertuig verwijderen") while selecting a vehicle to delete it
            int confirm = JOptionPane.YES_NO_OPTION;
            confirm = displayMessage(this, 2, "Voertuig verwijderen?", "Warning", confirm);     // you will ofcourse get a popup as shown before this.
            if(confirm == JOptionPane.YES_OPTION) {
                try {
                    VehicleModel model = this.controller.getFactory().getVehicle(Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString()));
                    this.controller.getFactory().delete(model);                          // this command will delete the relevant row in the database.

                    this.displayMessage(this, 1, "Voertuig succesvol verwijderd!", "Geslaagd", 0);

                    //reload view
                    VehicleController vehicleController = new VehicleController();
                    PageScreenSwitcher.getInstance().switchScreen(vehicleController.getView());
                } catch (ArrayIndexOutOfBoundsException ex) {                       // this command will show you an error if something goes wrong while deleting the selected vehicle.
                    System.out.println("Exception: "+ex.getMessage());              //ArrayIndexOutOfBoundsException means that the entered information is too big or not allowed by the specified row.
                }
            }
        }
        if(s == btnEditVehicle) {
            //edit vehicle = click the button "voertuig wijzigen". this command underneath here will make it able to change the selected vehicle, as the editview of the vehicle will popup.
            VehicleEditView vehicleEditView;
            try {
                vehicleEditView = new VehicleEditView(this.controller, table.getValueAt(table.getSelectedRow(),0));
                PageScreenSwitcher.getInstance().switchScreen(vehicleEditView);        // switchting to the vehicle view.
            } catch(ArrayIndexOutOfBoundsException ex) {
                this.displayMessage(this, 0, "Geen voertuig geselecteerd!", "Foutmelding", 0);
            }
        }
        if(s == btnTakeIn)
        {
            CheckupView vehicleTakeInView;         // "innames", when you click it it will show you that menu when you selected a menu.
            try {
                CheckupController checkupController = new CheckupController(table.getValueAt(table.getSelectedRow(),0));
                PageScreenSwitcher.getInstance().switchScreen(checkupController.getView());
            } catch(ArrayIndexOutOfBoundsException ex) {
                this.displayMessage(this, 0, "Geen voertuig geselecteerd!", "Foutmelding", 0);
            }
        }
    }
}
