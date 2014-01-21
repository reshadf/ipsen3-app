package views;

import controllers.CheckupController;
import controllers.VehicleController;
import helpers.ACL;
import models.CheckupModel;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;
import models.VehicleModel;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jasper
 * Class: View, shows what the user sees (and gets its info through the controller)
 */
public class CheckupView extends IpsenView implements ActionListener
{
    private CheckupController controller;
    private JTable table;

    private VehicleModel model;

    private JButton btnBack = new MenuButton("assets/terug_naar_overzicht.png", null, null);
    private JButton btnTakeIn = new MenuButton("assets/nw_inname_btn.png", null, null);
    private JButton btnEditTakeIn = new MenuButton("assets/edit_inname_btn.png", null, null);
    private JButton btnDeleteTakeIn = new MenuButton("assets/del_inname_btn.png", null, null);

    private JPanel topPanel;

    private int passVehicleID = 0;

    public CheckupView(CheckupController controller) {
        this.controller = controller;

        this.setLayout(new BorderLayout());
        topPanel = new JPanel();

        btnBack.addActionListener(this);
        btnEditTakeIn.addActionListener(this);
        btnDeleteTakeIn.addActionListener(this);
        btnTakeIn.addActionListener(this);

        this.topPanel.add(btnBack);

        if(ACL.getInstance().hasPermissionFor("CAN_CREATE_CHECKUP"))
            this.topPanel.add(btnTakeIn);

        if(ACL.getInstance().hasPermissionFor("CAN_EDIT_CHECKUP"))
            this.topPanel.add(btnEditTakeIn);

            if(ACL.getInstance().hasPermissionFor("CAN_DELETE_CHECKUP"))
        this.topPanel.add(btnDeleteTakeIn);

        this.add(topPanel, BorderLayout.NORTH);
    }

    public void init(Object... args) {
        if(args.length > 0) {
            System.out.println("Vehicle ID: "+args[0].toString());
            this.passVehicleID = Integer.valueOf(args[0].toString());

            model = this.controller.getVehicleFactory().getSingleVehicle(Integer.valueOf(args[0].toString()));
        } else {
            model = null;
        }

        String[] columnNames = {
            "#",
            "Datum",
            "Kilometerstand",
            "Schoongemaakt",
            "Schade"
        };

        System.out.println(model);
        List<CheckupModel> checkups = this.controller.getFactory().getCheckups(model.getId());

        if(checkups == null)
            checkups = new ArrayList<CheckupModel>();

        Object[][] data = new Object[checkups.size()][5];
        for(int i = 0; i < checkups.size(); i++) {
            data[i][0] = new Integer(checkups.get(i).getId());

            DateTime tempDateTime = checkups.get(i).getDate_detection();

            data[i][1] = tempDateTime.toString("d-M-Y");
            data[i][2] = checkups.get(i).getDistance_driven();

            if(checkups.get(i).getCleaned()){ data[i][3] = "Ja"; }else{ data[i][3] = "Nee"; }

            data[i][4] = checkups.get(i).getDamage();
        }

        table = new JTable(data, columnNames) {
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }

        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableSP = new JScrollPane(table);

        this.add(tableSP, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();

        if(s == btnBack) {
            VehicleController vehicleController = new VehicleController();
            PageScreenSwitcher.getInstance().switchScreen(vehicleController.getView());
        }
        if(s == btnDeleteTakeIn) {
            //delete vehicle
            int confirm = JOptionPane.YES_NO_OPTION;
            confirm = displayMessage(this, 2, "Inname verwijderen?", "Warning", confirm);
            if(confirm == JOptionPane.YES_OPTION) {
                try {
                    CheckupModel model = this.controller.getFactory().getCheckup(Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString()));
                    this.controller.getFactory().delete(model);

                    this.displayMessage(this, 1, "Inname succesvol verwijderd!", "Geslaagd", 0);

                    CheckupController checkupController = new CheckupController(this.passVehicleID);
                    PageScreenSwitcher.getInstance().switchScreen(checkupController.getView());

                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Exception: "+ex.getMessage());
                }
            }
        }
        if(s == btnEditTakeIn) {
            //edit vehicle
            CheckupEditView checkupEditView;
            try {
                checkupEditView = new CheckupEditView(this.controller, table.getValueAt(table.getSelectedRow(),0));
                checkupEditView.setVehicleID(this.passVehicleID);
                PageScreenSwitcher.getInstance().switchScreen(checkupEditView);
            } catch(ArrayIndexOutOfBoundsException ex) {
                this.displayMessage(this, 0, "Geen inname geselecteerd!", "Foutmelding", 0);
            }
        }
        if(s == btnTakeIn)
        {

            CheckupEditView checkupEditView = new CheckupEditView(this.controller);
            checkupEditView.setVehicleID(this.passVehicleID);
            PageScreenSwitcher.getInstance().switchScreen(checkupEditView);
        }
    }
}
