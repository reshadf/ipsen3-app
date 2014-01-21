package views;

import controllers.CheckupController;
import helpers.DatePicker;
import helpers.MyMouseListener;
import helpers.SpringUtilities;
import models.CheckupModel;
import helpers.MenuButton;
import helpers.PageScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * User: Jasper
 * Class: View, shows what the user sees (and gets its info through the controller)
 */
public class CheckupEditView extends IpsenView implements ActionListener
{
    private CheckupController controller;
    private JButton btnBack;
    private JButton btnSave;

    //form fields and labels
    HashMap<String, Object> formFields = new HashMap<String, Object>();

    JLabel lblId;
    JTextField txtId = null;

    JLabel lblDate;
    JTextField txtDate = null;
    JLabel lblDistance;
    JTextField txtDistance = null;
    JLabel lblCleaned;
    JCheckBox cbCleaned = null;
    JLabel lblDamage;
    JTextArea txtDamage = null;

    private int vehicleID;

    private JPanel buttonBar = new JPanel();

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    private Boolean boolEdit;

    public CheckupEditView(CheckupController controller, Object... args) {
        this.controller = controller;

        CheckupModel model;
        if(args.length > 0) {
            model = this.controller.getFactory().getCheckup(Integer.valueOf(args[0].toString()));
            System.out.println("Take in: " + model.getId());
            this.boolEdit = true;
        } else {
            model = null;
            this.boolEdit = false;
        }

        JPanel frame = new JPanel(new BorderLayout());

        //back button
        btnBack = new MenuButton("assets/terug_naar_overzicht.png", null, null);
        btnSave = new MenuButton("assets/save.png", null, null);
        btnBack.addActionListener(this);
        btnSave.addActionListener(this);

        buttonBar.add(btnBack);
        buttonBar.add(btnSave);

        JPanel form = new JPanel(new SpringLayout());

        //input : id
        lblId = new JLabel("ID");
        txtId = new JTextField(5);
        txtId.setEditable(false);
        txtId.setBorder(null);
        txtId.setOpaque(false);
        if(model != null)
            txtId.setText(String.valueOf(model.getId()));
        else
            txtId.setText("0");

        form.add(lblId);
        lblId.setLabelFor(txtId);
        form.add(txtId);

        //input : brand
        lblDate = new JLabel("Datum *");
        txtDate = new JTextField(15);

        txtDate.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                txtDate.setText(new DatePicker((JFrame) JFrame.getFrames()[0]).setPickedDate());
            }
        });

        if(model != null)
            txtDate.setText(model.getDate_detection().toString("d-M-y"));

        form.add(lblDate);
        lblDate.setLabelFor(txtDate);
        form.add(txtDate);

        //input : brand
        lblDistance = new JLabel("Kilometerstand *");
        txtDistance = new JTextField(15);
        if(model != null)
            txtDistance.setText(model.getDistance_driven() + "");

        form.add(lblDistance);
        lblDistance.setLabelFor(txtDistance);
        form.add(txtDistance);

        //input : airco
        lblCleaned = new JLabel("Schoongemaakt");
        cbCleaned = new JCheckBox();
        if(model != null)
            cbCleaned.setSelected(model.getCleaned());
        cbCleaned.addActionListener(this);

        form.add(lblCleaned);
        lblCleaned.setLabelFor(cbCleaned);
        form.add(cbCleaned);

        //input : description
        lblDamage = new JLabel("Schade");
        txtDamage = new JTextArea(5, 30);
        if(model != null)
            txtDamage.append(model.getDamage());

        form.add(lblDamage);
        lblDamage.setLabelFor(txtDamage);
        form.add(txtDamage);


        SpringUtilities.makeCompactGrid(form,
                5, 2, //rows, cols
                50, 10,        //initX, initY
                50, 10);       //xPad, yPad

        frame.add(buttonBar, BorderLayout.NORTH);
        frame.add(form, BorderLayout.CENTER);

        this.add(frame);
    }

    private String dateToAmericanFor(String date) {
        String[] formatted = date.split("-");
        return formatted[2]+"-"+formatted[1]+"-"+formatted[0];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == btnBack) {
            CheckupController checkupController = new CheckupController(this.getVehicleID(),0);
            PageScreenSwitcher.getInstance().switchScreen(checkupController.getView());
        }
        if(s == btnSave) {

            System.out.println("Voertuig: " + this.vehicleID);

            formFields.put("id", txtId.getText());
            formFields.put("date_detection", dateToAmericanFor(txtDate.getText()));
            formFields.put("distance_driven", txtDistance.getText());
            formFields.put("cleaned", (cbCleaned.isSelected())?1:0);
            formFields.put("damage", txtDamage.getText());
            formFields.put("vehicle_id", this.getVehicleID());

            int code = controller.save(formFields);
            switch(code) {
                case 0 :
                    displayMessage(this, 0, "Alle velden met een asteriks(*) dienen ingevuld te worden.", "Formulier fout!", 0);
                    break;
                case 1 :
                    if(this.boolEdit) {
                        displayMessage(this, 1, "Inname is succesvol opgeslagen!", "Geslaagd!", 0);
                    } else {
                        displayMessage(this, 1, "Inname is succesvol aangemaakt!", "Geslaagd!", 0);
                    }
                    CheckupController checkupController = new CheckupController(this.getVehicleID(),0);
                    PageScreenSwitcher.getInstance().switchScreen(checkupController.getView());
                    break;
                case 2 :
                    displayMessage(this, 0, "Fout opgetreden in formulier!", "Formulier fout!", 0);
                    break;
            }
        }
    }
}
